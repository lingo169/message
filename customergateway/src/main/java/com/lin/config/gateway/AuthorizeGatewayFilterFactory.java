package com.lin.config.gateway;

import com.google.gson.Gson;
import com.lin.common.constant.CommonConstant;
import com.lin.common.constant.RedisConstant;
import com.lin.common.error.CustomRuntimeException;
import com.lin.common.error.ErrorCode;
import com.lin.common.error.ErrorMessage;
import com.lin.common.error.ReturnMessageUtil;
import com.lin.common.rest.ResMsg;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

@Component
public class AuthorizeGatewayFilterFactory extends AbstractGatewayFilterFactory<AuthorizeGatewayFilterFactory.Config> {
    private static final Logger log = LoggerFactory.getLogger(AuthorizeGatewayFilterFactory.class);
    private Gson gson = new Gson();
    @Value("${" + CommonConstant.SESSION_TIMEOUT + "}")
    private String sessionTimeout;

    @Autowired
    private StringRedisTemplate stringRedisTemplate;
    @Value("${skip.replay.verify}")
    private boolean skipReplayVerify;
    //默认60秒
    @Value("${replay.timeout:60000}")
    private String replayTimeout;

    @Value("${wdp.path.filter:/customerservice/msvc}")
    private String pathFilter;

    /**
     * 不加该构造方法内容，则会出现：java.lang.Object cannot be cast to com.lin.config.gateway.AuthorizeGatewayFilterFactory$Config
     */
    public AuthorizeGatewayFilterFactory() {
        super(Config.class);
    }

    @Override
    public List<String> shortcutFieldOrder() {
        return Arrays.asList("enabled");
    }

    @Override
    public GatewayFilter apply(Config config) {
        log.info("登录认证{}", config.isEnabled());
        return (exchange, chain) -> {
            ServerHttpResponse response = exchange.getResponse();
            HttpHeaders headers = exchange.getRequest().getHeaders();
            ResMsg<Object> res = null;
            //设置开关，生产需要验证
            if(skipReplayVerify){
                res = replayVerify(headers.getFirst("X-RANDOM"), headers.getFirst("X-TIMESTAMP"));
            }
            if (null != res) {
                return generateMono(response, HttpStatus.TOO_EARLY, res);
            }
            log.info("进入 return (exchange, chain){}", config.isEnabled());
            if (!config.isEnabled()) {
                log.info("path enabled:{}", config.isEnabled());
                return chain.filter(exchange);
            }
            String path = exchange.getRequest().getPath().value();
            log.info("path:{}", path);
            boolean containsFlag = path.contains(pathFilter);
            log.info("包含路径规则：{}", containsFlag);
            if (containsFlag) {
                String token = headers.getFirst(CommonConstant.AUTH_TOKEN);
                log.info("header中获取的token位：{}", token);
                if (StringUtils.isEmpty(token)) {
                    res = ReturnMessageUtil.error(ErrorCode.UNAUTHORIZED_ERROR, ErrorMessage.UNAUTHORIZED_ERROR);
                    return generateMono(response, HttpStatus.UNAUTHORIZED, res);

//                    response.setStatusCode(HttpStatus.UNAUTHORIZED);
//                    response.getHeaders().add("Content-Type", MediaType.APPLICATION_JSON_VALUE);
//                    DataBuffer buffer = response.bufferFactory().wrap(gson.toJson(res).getBytes());
//                    return response.writeWith(Flux.just(buffer));

                }
                String data = stringRedisTemplate.opsForValue().get(RedisConstant.WDP_TOKEN + token);
                if (StringUtils.isEmpty(data)) {
                    log.error("获取不到登录信息");
                    res = ReturnMessageUtil.error(ErrorCode.UNAUTHORIZED_ERROR, ErrorMessage.UNAUTHORIZED_ERROR);
                    return generateMono(response, HttpStatus.UNAUTHORIZED, res);
                }
                log.info("登陆信息超时时间：{}，长度：{}", stringRedisTemplate.getExpire(RedisConstant.WDP_TOKEN + token), data.length());
                stringRedisTemplate.expire(RedisConstant.WDP_TOKEN + token, Long.valueOf(sessionTimeout), TimeUnit.SECONDS);
            }
            return chain.filter(exchange);
        };
    }

    private Mono<Void> generateMono(ServerHttpResponse response, HttpStatus status, ResMsg<Object> res) {
        response.setStatusCode(status);
        response.getHeaders().add("Content-Type", MediaType.APPLICATION_JSON_VALUE);
        DataBuffer buffer = response.bufferFactory().wrap(gson.toJson(res).getBytes());
        return response.writeWith(Flux.just(buffer));
    }

    public static class Config {
        // 控制是否开启认证
        private boolean enabled;

        public Config() {
        }

        public boolean isEnabled() {
            return enabled;
        }

        public void setEnabled(boolean enabled) {
            this.enabled = enabled;
        }

        public static void main(String[] args) {
            System.out.println("/wdp-backstage/web/user/123".substring(0, 18));
        }
    }


    /**
     * @param random
     * @return 返回NULL则属于正常
     */
    private ResMsg<Object> replayVerify(String random, String timestamp) {
        Long requestTimestamp = 0l;
        try {
            requestTimestamp = Long.parseLong(timestamp);//请求中的时间戳
        } catch (NumberFormatException e) {
            log.error("NumberFormatException error :{}", e);
            return ReturnMessageUtil.error(ErrorCode.REPLAY_ATTACK_ERROR, ErrorCode.REPLAY_ATTACK_ERROR.getMessage());
        }

        //ranodmVerify
        if (null == requestTimestamp || StringUtils.isEmpty(random)) {
            return ReturnMessageUtil.error(ErrorCode.REPLAY_ATTACK_ERROR, ErrorCode.REPLAY_ATTACK_ERROR.getMessage());
        }
        String rd = stringRedisTemplate.opsForValue().get(RedisConstant.REPLAY_ATTACK_RANDOM + ":" + random);
        if (!StringUtils.isEmpty(rd)) {
            log.error("随机数存在： requestRandom => {}; redisRandom => {}", random, rd);
            return ReturnMessageUtil.error(ErrorCode.REPLAY_ATTACK_ERROR, ErrorCode.REPLAY_ATTACK_ERROR.getMessage());
        }
        stringRedisTemplate.opsForValue().set(RedisConstant.REPLAY_ATTACK_RANDOM + random, random, Integer.parseInt(replayTimeout));

        //timestampVerify
        long currentMilliseconds = System.currentTimeMillis();
        log.info("replayTimeout:{} currentMilliseconds:{},requestTimestamp:{}", replayTimeout, currentMilliseconds, requestTimestamp);
        if (Math.abs(currentMilliseconds - requestTimestamp) > Long.parseLong(replayTimeout)) {
            log.error("时间戳超时");
            return ReturnMessageUtil.error(ErrorCode.REPLAY_ATTACK_ERROR, ErrorCode.REPLAY_ATTACK_ERROR.getMessage());
        }
        return null;
    }


    public static void main(String[] args) {
        System.out.println(new Date().getTime());
    }
}
