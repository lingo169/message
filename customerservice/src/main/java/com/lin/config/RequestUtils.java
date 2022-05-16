package com.lin.config;

import com.lin.common.constant.CommonConstant;
import com.lin.common.constant.RedisConstant;
import com.lin.common.utils.BeanCopyUtils;
import com.lin.po.Customer;
import org.redisson.api.RBucket;
import org.redisson.api.RedissonClient;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;

public class RequestUtils {
    public static HttpServletRequest getRequest() {
        ServletRequestAttributes servletRequestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = servletRequestAttributes.getRequest();
        return request;
    }

    public static String getSession(String tokenKey) {
        return getRequest().getHeader(tokenKey);
    }

    public static Customer getCustomer(String sessionid) {
        RedissonClient redissonClient = SpringContextIniter.getBean(RedissonClient.class);
        RBucket<String> rb = redissonClient.getBucket(RedisConstant.WDP_TOKEN + sessionid);
        return BeanCopyUtils.toObj(rb.get(), Customer.class);
    }

    public static Customer getCustomer() {
        String sessionid = RequestUtils.getSession(CommonConstant.AUTH_TOKEN);
        RedissonClient redissonClient = SpringContextIniter.getBean(RedissonClient.class);
        RBucket<String> rb = redissonClient.getBucket(RedisConstant.WDP_TOKEN + sessionid);
        return BeanCopyUtils.toObj(rb.get(), Customer.class);
    }
}
