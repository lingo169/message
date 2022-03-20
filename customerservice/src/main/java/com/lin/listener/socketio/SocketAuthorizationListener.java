package com.lin.listener.socketio;

import com.corundumstudio.socketio.AuthorizationListener;
import com.corundumstudio.socketio.HandshakeData;
import com.lin.common.constant.RedisConstant;
import com.lin.config.SpringContextIniter;
import org.apache.commons.lang3.StringUtils;
import org.redisson.api.RBucket;
import org.redisson.api.RedissonClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SocketAuthorizationListener implements AuthorizationListener {
    protected static Logger LOG = LoggerFactory.getLogger(SocketAuthorizationListener.class);

    public static final String TOKEN="token";

    @Override
    public boolean isAuthorized(HandshakeData handshakeData) {
        String token = handshakeData.getSingleUrlParam(TOKEN);
        RedissonClient redissonClient= SpringContextIniter.getBean(RedissonClient.class);
        RBucket<String> bucket = redissonClient.getBucket(RedisConstant.WDP_TOKEN + token);
        String data = bucket.get();
        if (StringUtils.isEmpty(data)) {
            LOG.info("**********客户端：" + token + "无权限**********");
            return false;
        }
        return true;
    }
}
