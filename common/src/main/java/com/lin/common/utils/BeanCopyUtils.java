package com.lin.common.utils;


import com.google.gson.Gson;

import java.util.UUID;

public class BeanCopyUtils {

    public static String getUUID32() {
        return UUID.randomUUID().toString().replace("-", "").toLowerCase();
    }

    /**
     *
     * @param t 需要被转换的对象
     * @param cls 转换后的类型
     * @param <T>
     * @return 转换后类型具体对象
     */
    public static <T> T beanCopy(Object t, Class<T> cls) {
        if(null==t){
            return null;
        }
        Gson g = new Gson();
        return g.fromJson(g.toJson(t), cls);
    }

    public static <T> T toObj(String t, Class<T> cls) {
        if(null==t){
            return null;
        }
        Gson g = new Gson();
        return g.fromJson(t,cls);
    }
}
