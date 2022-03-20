package com.lin.controller;

import com.lin.common.error.CustomRuntimeException;
import com.lin.common.error.ErrorCode;
import com.lin.common.utils.CommonUtils;
import com.lin.controller.req.CustomerReqMsg;
import com.lin.controller.req.LoginReqMsg;
import com.lin.controller.req.ModifyPassReqMsg;
import com.lin.controller.res.LoginResMsg;
import com.lin.controller.res.ResMsg;
import com.lin.service.login.LoginService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;

@Api(tags = "登录注册入口")
@RestController
@RequestMapping("/unverify")
public class LoginController implements BaseController {

    @Autowired
    private LoginService loginService;

    @ApiOperation("登录接口")
    @ApiResponses({
            @ApiResponse(code = 200,message = "OK",response = LoginResMsg.class)
    })
    @PostMapping("/login")
    public ResMsg<LoginResMsg> login(@Valid @RequestBody LoginReqMsg user, BindingResult bindingResult) throws Exception {
        BaseController.verify(bindingResult);
        ResMsg<LoginResMsg> urs = new ResMsg<>();
        LoginResMsg ur = loginService.login(user.getCustomerNo(), user.getPassword());
        return urs.withData(ur);
    }

    @ApiOperation("注册接口")
    @PostMapping("/register")
    public ResMsg<Integer> register(@Valid @RequestBody CustomerReqMsg reqMsg, BindingResult bindingResult) throws InvalidKeySpecException, NoSuchAlgorithmException, CustomRuntimeException {
        BaseController.verify(bindingResult);
        if (StringUtils.isBlank(reqMsg.getMobile()) || StringUtils.isBlank(reqMsg.getEmail())) {
            throw new CustomRuntimeException(ErrorCode.EMAIL_OR_MOBILE_NOT_NULL, ErrorCode.EMAIL_OR_MOBILE_NOT_NULL.getMessage());
        }
        if (!CommonUtils.isPhone(reqMsg.getMobile())) {
            throw new CustomRuntimeException(ErrorCode.MOBILE_FORMAT_ERROR, ErrorCode.MOBILE_FORMAT_ERROR.getMessage());
        }
        if (loginService.verifyCustomer(reqMsg.getCustomerNo()) == 1) {
            throw new CustomRuntimeException(ErrorCode.CUSTOMER_EXISTS, ErrorCode.CUSTOMER_EXISTS.getMessage());
        }
        ResMsg<Integer> urs = new ResMsg<>();
        Integer i = loginService.register(reqMsg);
        return urs.withData(i);
    }

    @ApiOperation("查询账户是否存在")
    @ApiImplicitParam(paramType = "path", name = "email", value = "邮件地址", required = true)
    @ApiResponses({
            @ApiResponse(code = 200,message = "data返回true则发送邮箱成功，否则发送邮箱失败")
    })
    @GetMapping("/forget/{email}")
    public ResMsg<Boolean> forgetPassword(@PathVariable String email) throws InvalidKeySpecException, NoSuchAlgorithmException, CustomRuntimeException {
        ResMsg<Boolean> urs = new ResMsg<>();
        return urs.withData(loginService.forgetPassword(email));
    }
    @PostMapping("/modifypass")
    public ResMsg<Integer> modifypass(@Valid @RequestBody ModifyPassReqMsg reqMsg, BindingResult bindingResult) throws CustomRuntimeException {
        BaseController.verify(bindingResult);
        if(!StringUtils.equals(reqMsg.getConfirmpass(),reqMsg.getPass())){
            throw new CustomRuntimeException(ErrorCode.PASSWORD_NOT_EQUALLY, ErrorCode.PASSWORD_NOT_EQUALLY.getMessage());
        }
        ResMsg<Integer> urs = new ResMsg<>();
        return urs.withData(loginService.modifypass(reqMsg.getEmail(),reqMsg.getPass(),reqMsg.getToken()));
    }


    @ApiOperation("查询账户是否存在")
    @ApiImplicitParam(paramType = "path", name = "email", value = "账号", required = true)
    @ApiResponses({
            @ApiResponse(code = 200,message = "data返回0则找不到账号，否则账户存在")
    })
    @GetMapping("/verifyNo/{customerNo}")
    public ResMsg<Integer> verifyCustomer(@PathVariable String customerNo) throws InvalidKeySpecException, NoSuchAlgorithmException, CustomRuntimeException {
        ResMsg<Integer> urs = new ResMsg<>();
        return urs.withData(loginService.verifyCustomer(customerNo));
    }

}
