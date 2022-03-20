package com.lin.service.login;

import com.google.gson.Gson;
import com.lin.common.error.CustomRuntimeException;
import com.lin.common.error.ErrorCode;
import com.lin.common.utils.BeanCopyUtils;
import com.lin.common.utils.DateUtils;
import com.lin.common.constant.CommonConstant;
import com.lin.common.constant.RedisConstant;
import com.lin.common.utils.RedisSerializableUtils;
import com.lin.common.utils.security.RSAUtils;
import com.lin.common.utils.security.SecuritySHA1Utils;
import com.lin.controller.req.CustomerReqMsg;
import com.lin.controller.res.LoginResMsg;
import com.lin.dao.CustomerMapper;
import com.lin.po.Customer;
import com.lin.service.email.EmailService;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang3.StringUtils;
import org.redisson.api.RBucket;
import org.redisson.api.RedissonClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.security.NoSuchAlgorithmException;
import java.security.interfaces.RSAPrivateKey;
import java.security.spec.InvalidKeySpecException;
import java.util.concurrent.TimeUnit;

@Component
public class LoginServiceImpl implements LoginService {
    private static final Logger log = LoggerFactory.getLogger(LoginServiceImpl.class);
    @Value("${wdp.pri.key}")
    private String privateKey;

    @Value("${" + CommonConstant.SESSION_TIMEOUT + "}")
    private String sessionTimeout;

    @Value("${cs.email.fromemail}")
    private String fromemail;

    @Autowired
    private EmailService emailService;

    @Autowired
    private CustomerMapper customerMapper;

    @Autowired
    private RedissonClient redissonClient;

    @Override
    public LoginResMsg login(String customerNo, String password) throws Exception {
        // 解密密码
        RSAPrivateKey pk = RSAUtils.getPrivateKey(privateKey);
        String dpass = RSAUtils.privateDecrypt(password, pk);
        log.info("priv");
        log.info(dpass);
        Customer c = customerMapper.byCustomerNo(customerNo);
        if (c == null) {
            throw new CustomRuntimeException(ErrorCode.LOGIN_NOT_EXISTS, ErrorCode.LOGIN_NOT_EXISTS.getMessage());
        }
        String sha1pass = SecuritySHA1Utils.shaEncode(dpass);
        log.info("password sha1 value is {}", sha1pass);
        if (!StringUtils.equals(c.getPassword(), sha1pass)) {
            throw new CustomRuntimeException(ErrorCode.LOGIN_ERROR, ErrorCode.LOGIN_ERROR.getMessage());
        }
        LoginResMsg urm = BeanCopyUtils.beanCopy(c, LoginResMsg.class);
        urm = setSession(c);
        return urm;
    }

    @Override
    public Integer logout() throws CustomRuntimeException {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        String token = request.getHeader(CommonConstant.AUTH_TOKEN);
        if (StringUtils.isBlank(token)) {
            throw new CustomRuntimeException(ErrorCode.HEADER_TOKEN, "HEADER中获取不到token");
        } else {
            redissonClient.getBucket(RedisConstant.WDP_TOKEN + token).delete();
            return 1;
        }

    }

    @Override
    public Integer verifyCustomer(String customerNo) {
        Customer c = customerMapper.byCustomerNo(customerNo);
        if (null == c) {
            return 0;
        } else {
            return 1;
        }
    }

    @Override
    public Integer register(CustomerReqMsg reqMsg) throws InvalidKeySpecException, NoSuchAlgorithmException, CustomRuntimeException {
        // 解密密码
        RSAPrivateKey pk = RSAUtils.getPrivateKey(privateKey);
        String dpass = RSAUtils.privateDecrypt(reqMsg.getPassword(), pk);
        reqMsg.setPassword(DigestUtils.sha1Hex(dpass));
        Customer c = saveinit(reqMsg);
        return customerMapper.insertSelective(c);
    }

    @Override
    public Boolean forgetPassword(String email) throws CustomRuntimeException {
        Customer c = customerMapper.selectByEmail(email);
        if (c == null) {
            throw new CustomRuntimeException(ErrorCode.CUSTOMER_NOT_EXISTS, ErrorCode.CUSTOMER_NOT_EXISTS.getMessage());
        }
        String v = BeanCopyUtils.getUUID32();
        RBucket<String> rb = redissonClient.getBucket(RedisConstant.FORGET_PASSWORD + email);
        rb.set(v);
        rb.expire(1, TimeUnit.DAYS);
        String body = "<h1>点击如下链接进行激活</h1></br><a href=\"http://139.198.176.37/chat/modify-pass.html?email=" + email + "=&token=" + v + "\">修改密码</a>";
        log.info("email body :{}", body);
        return emailService.sendEmail(fromemail, email, body);
    }

    @Override
    public Integer modifypass(String email, String pass, String token) throws CustomRuntimeException {
        Customer c = customerMapper.selectByEmail(email);
        if (c == null) {
            throw new CustomRuntimeException(ErrorCode.CUSTOMER_NOT_EXISTS, ErrorCode.CUSTOMER_NOT_EXISTS.getMessage());
        }
        RBucket<String> rb = redissonClient.getBucket(RedisConstant.FORGET_PASSWORD + email);
        if (StringUtils.isBlank(rb.get())) {
            throw new CustomRuntimeException(ErrorCode.RESEND_MODIFYPASS_EAMIL, ErrorCode.RESEND_MODIFYPASS_EAMIL.getMessage());
        }
        if (StringUtils.equals(rb.get(), token)) {
            throw new CustomRuntimeException(ErrorCode.ILLEGAL_MODIFYPASS_TOKEN, ErrorCode.ILLEGAL_MODIFYPASS_TOKEN.getMessage());
        }
        c.setPassword(pass);
        c.setUpdateTime(DateUtils.fullDateFormat());
        return customerMapper.updateByPrimaryKeySelective(c);
    }

    private Customer saveinit(CustomerReqMsg reqMsg) throws CustomRuntimeException {
        Customer c = BeanCopyUtils.beanCopy(reqMsg, Customer.class);
        c.setId(Long.parseLong(RedisSerializableUtils.generateForLong(CommonConstant.DEFAULT_LENGTH)));
        c.setCreateTime(DateUtils.fullDateFormat());
        c.setUpdateTime(DateUtils.fullDateFormat());
        return c;
    }

    private LoginResMsg setSession(Customer user) {
        Gson gs = new Gson();
//		HttpServletRequest request = ((ServletRequestAttributes)RequestContextHolder.getRequestAttributes()).getRequest();
        String token = BeanCopyUtils.getUUID32();
        String userJson = gs.toJson(user);
        RBucket rb = redissonClient.getBucket(RedisConstant.WDP_TOKEN + token);
        rb.expire(Long.valueOf(sessionTimeout), TimeUnit.SECONDS);
        rb.set(userJson);
//        redissonClient.getBucket(RedisConstant.WDP_TOKEN + token, userJson, Long.valueOf(sessionTimeout), TimeUnit.SECONDS).set();
        log.info("存入redis的登陆信息key为：{}，内容大小为：{}", RedisConstant.WDP_TOKEN + token, userJson.length());
        LoginResMsg urm = gs.fromJson(userJson, LoginResMsg.class);
        urm.setSessionid(token);
        return urm;
    }

}
