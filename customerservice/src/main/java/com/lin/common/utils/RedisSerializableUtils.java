package com.lin.common.utils;

import com.lin.common.error.CustomRuntimeException;
import com.lin.common.error.ErrorCode;
import com.lin.config.SpringContextIniter;
import org.redisson.Redisson;
import org.redisson.api.RScript;
import org.redisson.api.RedissonClient;
import org.redisson.client.codec.LongCodec;
import org.redisson.client.codec.StringCodec;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;


/**
 * 生成序列号
 *
 * @author linjingqin-yfzx
 */

public class RedisSerializableUtils {
    /**
     * 日志
     */
    protected static final Logger logger = LoggerFactory.getLogger(RedisSerializableUtils.class);
    /**
     * 订单在Redis中的KEY（序列号的KEY）
     */
    //public static final String INIT_SERIALIZABLE_ORDER = "wdp:base:serializable:orderno";
    public static final String INIT_SERIALIZABLE_ORDER = "aaa";
    /**
     * 日期格式
     */
    private static DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyyMMddHHmmss");
    /**
     * 小于Integer.MAX_VALUE 即：- 1 = 2147483648 - 1 = 2147483647
     */
    private static final String max = "999999999";
    private static final Integer MAX_LENGTH = 9;
    private static final Integer ORDER_MAX_LEN = 19;
    private static final Integer TIME_MAX_LEN = 14; //时间长度
    /**
     * 下面方法参数需要，把KEY存入数组
     */
    private static final List<String> keys = Arrays.asList(INIT_SERIALIZABLE_ORDER);


    public static String generateForLong(Integer snLen) throws CustomRuntimeException {
        if (snLen != 19) {
            throw new CustomRuntimeException(ErrorCode.SERIALIZABLE_LENGTH_LESS, ErrorCode.SERIALIZABLE_LENGTH_LESS.getMessage());
        }
        int i = snLen - TIME_MAX_LEN;
        //最大数
        String topNum = max.substring(0, i);
        //减少时间格式化后的长度然后再传入 日期长度17位
        return loadScript(SpringContextIniter.getBean("redissonClient", RedissonClient.class), snLen - TIME_MAX_LEN, topNum);
    }

    /**
     * 获取22位的订单ID
     *
     * @return 返回22位的订单序列号
     */
    public static String generateOrderNo(Integer snLen) throws CustomRuntimeException {
//        if (snLen <= 20) {
//            throw new CustomRuntimeException(ErrorCode.SERIALIZABLE_LENGTH_LESS, ErrorCode.SERIALIZABLE_LENGTH_LESS.getMessage());
//        } else if (snLen > ORDER_MAX_LEN) {
//            throw new CustomRuntimeException(ErrorCode.SERIALIZABLE_LENGTH_THAN, ErrorCode.SERIALIZABLE_LENGTH_THAN.getMessage());
//        }
        int i = snLen - TIME_MAX_LEN;
        //最大数
        String topNum = max.substring(0, i);
        //减少时间格式化后的长度然后再传入 日期长度17位
        return loadScript(SpringContextIniter.getBean("redissonClient", RedissonClient.class), snLen - TIME_MAX_LEN, topNum);
    }

    /**
     * 装载lua脚本
     *
     * @param topNum 位数，例如5位数则填写 99999
     * @return 返回脚本序列号
     * local num=redis.call('incr',KEYS[1]) if tonumber(num) >= ARGV[1] then return redis.call('getset', KEYS[1],0) else return redis.call('get',KEYS[1]) end
     * EVAL "local num=redis.call('incr',KEYS[1]) if tonumber(num) >= tonumber(ARGV[1]) then return redis.call('getset', KEYS[1],0) else  return redis.call('get',KEYS[1]) end" 1 aa 8
     */
    public static String loadScript(RedissonClient srt, Integer len, String topNum) {
        String ser = loadScript(srt, topNum);
        //加前缀
        String date = dtf.format(LocalDateTime.now());
        StringBuffer sb = new StringBuffer();
        sb.append(date);
        sb.append(String.format("%0" + len + "d", Long.parseLong(ser)));//Long.parseLong(ser)
        return sb.toString();
    }

    public static String loadScript(RedissonClient rc, String topNum) {
        //脚本
        String script = "local num=redis.call('incr',KEYS[1]) " +
                "if tonumber(num) >= tonumber(ARGV[1]) then " +
                "	return redis.call('getset', KEYS[1],0) " +
                "else " +
                "	return redis.call('get',KEYS[1]) " +
                "end";
        logger.info(script);
        /**
         * ARGV[1] can't be converted to number since it was serialized by default binary FstCodec. You need to use LongCodec or StringCodec instead:
         *
         * redissonClient.getScript(LongCodec.INSTANCE);
         */
        RScript rs = rc.getScript(StringCodec.INSTANCE);
        String ser = rs.eval(RScript.Mode.READ_WRITE, script, RScript.ReturnType.VALUE, Collections.singletonList(INIT_SERIALIZABLE_ORDER), topNum);
        logger.info("key是：{},值是：{}", INIT_SERIALIZABLE_ORDER, ser);
        return ser;
    }

    public static void main(String[] args) throws CustomRuntimeException {
        int len = 6;
        String ser = "8";
        System.out.println(String.format("%0" + len + "d", Long.parseLong("123")));
        RedissonClient rc = Redisson.create();
        System.out.println(generateOrderNo(22));
//		JedisPoolConfig jpc=new JedisPoolConfig();
//		jpc.setMaxIdle(5);
//		jpc.setMinIdle(3);
//		jpc.setMaxTotal(5);
//		jpc.setMaxWaitMillis(20000);
//		StringRedisSerializer ss=new StringRedisSerializer();
//		JedisConnectionFactory connectionFactory=new JedisConnectionFactory();
//		connectionFactory.setPoolConfig(jpc);
//		connectionFactory.setPort(7379);
//		connectionFactory.setHostName("21.96.4.128");
//		connectionFactory.afterPropertiesSet();
//		StringRedisTemplate sr=new StringRedisTemplate();
//		sr.setConnectionFactory(connectionFactory);
//		sr.setKeySerializer(ss);
//		sr.setValueSerializer(new GenericToStringSerializer<Long>(Long.class));
//		sr.afterPropertiesSet();
//		RedisService ru=new RedisService();
//		System.out.println(ru.loadScript(sr,5));
    }
}
