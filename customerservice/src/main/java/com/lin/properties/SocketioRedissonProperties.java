package com.lin.properties;

import org.redisson.config.Config;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;


@Component
@ConfigurationProperties(SocketioRedissonProperties.PREFIX)
public class SocketioRedissonProperties extends Config {

    public static final String PREFIX = "spring.socketio.redis.redisson";

    public enum RedisServerMode {

        /**
         * Cluster Mode.
         */
        CLUSTER,
        /**
         * Master Slave  Mode
         */
        MASTERSLAVE,
        /**
         * Replicated  Mode
         */
        REPLICATED,
        /**
         * Sentinel  Mode
         */
        SENTINEL,
        /**
         * Single  Mode
         */
        SINGLE,

    }
    private RedisServerMode server = RedisServerMode.SINGLE;

}
