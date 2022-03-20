package com.lin.controller;

import com.lin.common.error.CustomRuntimeException;
import com.lin.common.error.ErrorCode;
import com.lin.common.utils.BeanCopyUtils;
import com.lin.controller.req.AddCustomerReqMsg;
import com.lin.controller.req.AgreeCustomerReqMsg;
import com.lin.controller.req.ListRelsReqMsg;
import com.lin.controller.req.LoginReqMsg;
import com.lin.controller.res.CustomerPageResMsg;
import com.lin.controller.res.LoginResMsg;
import com.lin.controller.res.ResMsg;
import com.lin.po.Customer;
import com.lin.service.customer.CustomerService;
import com.lin.service.login.LoginService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;

@Api(tags = "用户入口")
@RestController
@RequestMapping("/msvc")
public class CustomerController implements BaseController {

    @Autowired
    private CustomerService customerService;

    @Autowired
    private LoginService loginService;

    @ApiOperation("新增好友接口")
    @PostMapping("/addrel")
    public ResMsg<Integer> addrel(@Valid @RequestBody AddCustomerReqMsg reqMsg, BindingResult bindingResult) throws CustomRuntimeException {
        BaseController.verify(bindingResult);
        ResMsg<Integer> urs = new ResMsg<>();
        Integer i = customerService.addrel(reqMsg.getCustomerId(), reqMsg.getCustomerRelId());
        return urs.withData(i);
    }

    @ApiOperation("接收好友接口")
    @PostMapping("/agree")
    public ResMsg<Integer> agree(@Valid @RequestBody AgreeCustomerReqMsg reqMsg, BindingResult bindingResult) throws CustomRuntimeException {
        BaseController.verify(bindingResult);
        ResMsg<Integer> urs = new ResMsg<>();
        Integer i = customerService.agree(reqMsg.getCustomerRelId(), reqMsg.getCustomerGroupId(), reqMsg.getStatus());
        return urs.withData(i);
    }

    @ApiOperation("查询好友列表接口")
    @PostMapping("/listrels")
    public ResMsg<CustomerPageResMsg> listrels(@Valid @RequestBody ListRelsReqMsg reqMsg, BindingResult bindingResult) throws CustomRuntimeException {
        BaseController.verify(bindingResult);
        ResMsg<CustomerPageResMsg> urs = new ResMsg<>();
        Customer c= BeanCopyUtils.beanCopy(reqMsg, Customer.class);
        CustomerPageResMsg res = customerService.listrels(c);
        return urs.withData(res);
    }

    @ApiOperation("登出接口")
    @GetMapping("/logout")
    public ResMsg<Integer> logout() throws CustomRuntimeException {
        return ResMsg.ok(loginService.logout());
    }
}
