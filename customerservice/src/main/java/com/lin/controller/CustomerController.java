package com.lin.controller;

import com.lin.common.error.CustomRuntimeException;
import com.lin.common.rest.ResMsg;
import com.lin.controller.req.AddCustomerReqMsg;
import com.lin.controller.req.AgreeCustomerReqMsg;
import com.lin.controller.req.ListRelsReqMsg;
import com.lin.controller.res.CustomerPageResMsg;
import com.lin.service.customer.CustomerService;
import com.lin.service.login.LoginService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.Map;

@Api(tags = "用户入口")
@RestController
@RequestMapping("/msvc")
public class CustomerController implements BaseController {

    @Autowired
    private CustomerService customerService;

    @Autowired
    private LoginService loginService;

    /**
     *
     * @param reqMsg
     * @param bindingResult
     * @return
     * @throws CustomRuntimeException
     */
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
        Integer i = customerService.agree(reqMsg);
        return urs.withData(i);
    }

    @ApiOperation("查询好友列表接口")
    @PostMapping("/listrels")
    public ResMsg<CustomerPageResMsg> listrels(@Valid @RequestBody ListRelsReqMsg reqMsg, BindingResult bindingResult) throws CustomRuntimeException {
        BaseController.verify(bindingResult);
        ResMsg<CustomerPageResMsg> urs = new ResMsg<>();
//        Customer c= new Customer();
//        c.setId(reqMsg.getCustomerId());
//        c.setPageSize(reqMsg.getPageSize());
//        c.setPageNo(reqMsg.getPageNo());
        Map map = build(reqMsg);
        CustomerPageResMsg res = customerService.listrels(map);
        return urs.withData(res);
    }

    private Map<String, Object> build(ListRelsReqMsg reqMsg) {
        Map<String, Object> map = new HashMap<>();
        map.put("customerId", reqMsg.getCustomerId());
        map.put("incloudGroupFlag", reqMsg.getIncloudGroupFlag());
        map.put("pageNo", reqMsg.getPageNo());
        map.put("pageSize", reqMsg.getPageSize());
        return map;
    }

    @ApiOperation("登出接口")
    @GetMapping("/logout")
    public ResMsg<Integer> logout() throws CustomRuntimeException {
        return ResMsg.ok(loginService.logout());
    }
}
