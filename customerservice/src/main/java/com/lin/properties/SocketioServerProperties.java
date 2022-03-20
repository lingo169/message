package com.lin.properties;


import com.corundumstudio.socketio.Configuration;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;


@Component
@ConfigurationProperties(SocketioServerProperties.PREFIX)
public class SocketioServerProperties extends Configuration {

    public static final String PREFIX = "spring.socketio.server";

    private boolean failIfNativeEpollLibNotPresent = false;

    /**
     * Enable Socketio Server.
     */
    private boolean enabled = false;

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public boolean isFailIfNativeEpollLibNotPresent() {
        return failIfNativeEpollLibNotPresent;
    }

    public void setFailIfNativeEpollLibNotPresent(boolean failIfNativeEpollLibNotPresent) {
        this.failIfNativeEpollLibNotPresent = failIfNativeEpollLibNotPresent;
    }
}
