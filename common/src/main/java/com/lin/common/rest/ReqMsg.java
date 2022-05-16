package com.lin.common.rest;

import java.io.Serializable;

public class ReqMsg implements Serializable {

    private String random;

    private String timestamp;

    public String getRandom() {
        return random;
    }

    public void setRandom(String random) {
        this.random = random;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }
}
