package com.lin.controller;

import com.lin.common.error.CustomRuntimeException;
import com.lin.common.error.ErrorCode;
import com.lin.common.rest.ResMsg;
import com.lin.controller.req.AddCustomerGroupReqMsg;
import com.lin.controller.req.AddCustomerReqMsg;
import com.lin.controller.req.GroupAddMemberReqMsg;
import com.lin.po.CustomerGroup;
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
    @PostMapping("/creategroup")
    public ResMsg<CustomerGroup> createGroup(@Valid @RequestBody AddCustomerGroupReqMsg reqMsg, BindingResult bindingResult) throws CustomRuntimeException {
        BaseController.verify(bindingResult);
        if(reqMsg.getCustomerRelIds()==null||reqMsg.getCustomerRelIds().size()==0){
            throw new CustomRuntimeException(ErrorCode.ERR_CODE_INVALIDATION, ErrorCode.ERR_CODE_INVALIDATION.getMessage());
        }
        ResMsg<CustomerGroup> urs = new ResMsg<>();
        return urs.withData(customerGroupService.createGroup(reqMsg));
    }

    @ApiOperation("群组新增成员接口")
    @PostMapping("/addmember")
    public ResMsg<Integer> addMember(@Valid @RequestBody GroupAddMemberReqMsg reqMsg, BindingResult bindingResult) throws CustomRuntimeException {
        BaseController.verify(bindingResult);
        ResMsg<Integer> urs = new ResMsg<>();
        return urs.withData(customerGroupService.addGroup(reqMsg));
    }
}
