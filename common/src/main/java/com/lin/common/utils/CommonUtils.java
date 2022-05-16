package com.lin.common.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CommonUtils {
    private CommonUtils() {
    }

    public static boolean isPhone(String mobile) {
        //String regular1 = "/^1[3-9]\\d{9}$/";
        String regular = "^1[3-9]\\d{9}$";
        Pattern p = Pattern.compile(regular);
        Matcher m = p.matcher(mobile);
        return m.matches();
    }
}
