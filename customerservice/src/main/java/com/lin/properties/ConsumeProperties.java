package com.lin.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
@ConfigurationProperties(ConsumeProperties.PREFIX)
public class ConsumeProperties {
    public static final String PREFIX = "rocketmq.consume.topic";

    private final Map<String, String> socketio = new HashMap<>();

    private final Map<String, String> content = new HashMap<>();


    public Map<String, String> getSocketio() {
        return socketio;
    }

    public Map<String, String> getContent() {
        return content;
    }
}
