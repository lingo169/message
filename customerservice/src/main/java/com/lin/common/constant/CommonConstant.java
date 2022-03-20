package com.lin.common.constant;

import org.apache.commons.codec.digest.DigestUtils;

public class CommonConstant {
    private CommonConstant() {
    }

    /**
     * token
     */
    public static final String AUTH_TOKEN = "T-AUTH-TOKEN";
    public static final String SESSION_TIMEOUT = "wdp.session.timeout";
    public static final String IMG_TYPE = "1";
    public static final String VIDEO_TYPE = "2";
    public static final String SPLIT_BASE64 = ";base64,";
    public static final String IMG_BASE64_PREFIX = "data:image/%s;base64,";
    public static final String VIDEO_BASE64_PREFIX = "data:video/%s;base64,";
    /**
     * 默认后缀
     */
    public static final String DEFAULT_PREFIX = "png";

    public static final String UTF_8 = "UTF-8";

    public static final String INIT_PASSWORD = "123qwe";

    public static final Integer DEFAULT_LENGTH = 19;



    public static void main(String[] args) {
        System.out.println(DigestUtils.sha1Hex(INIT_PASSWORD));
    }
}
