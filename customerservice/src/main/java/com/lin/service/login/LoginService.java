package com.lin.service.login;

import com.lin.common.error.CustomRuntimeException;
import com.lin.controller.req.CustomerReqMsg;
import com.lin.controller.res.LoginResMsg;

import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;

public interface LoginService {
    LoginResMsg login(String customerNo, String password) throws Exception;

    Integer logout() throws CustomRuntimeException;

    Integer verifyCustomer(String customerNo);

    Integer register(CustomerReqMsg reqMsg) throws InvalidKeySpecException, NoSuchAlgorithmException, CustomRuntimeException;

    Boolean forgetPassword(String email) throws CustomRuntimeException;

    Integer modifypass(String email,String pass,String token) throws CustomRuntimeException;
}
