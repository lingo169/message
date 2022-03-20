package com.lin.controller;

import com.lin.common.error.CustomRuntimeException;
import com.lin.controller.req.AddCustomerReqMsg;
import com.lin.controller.res.ResMsg;
import com.lin.service.customer.CustomerGroupService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@Api(tags = "群组入口")
@RestController
@RequestMapping("/msvc")
public class CustomerGroupController implements BaseController{

    @Autowired
    private CustomerGroupService customerGroupService;

    @ApiOperation("新增群接口")
    @PostMapping("/createGroup")
    public ResMsg<Integer> createGroup(@Valid @RequestBody AddCustomerReqMsg reqMsg, BindingResult bindingResult) throws CustomRuntimeException {
        BaseController.verify(bindingResult);
        ResMsg<Integer> urs = new ResMsg<>();
        Integer i = customerGroupService.addGroup(reqMsg.getCustomerId(), reqMsg.getCustomerRelId());
        return urs.withData(i);
    }
}
