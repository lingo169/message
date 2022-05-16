package com.lin.service.customer;

import com.lin.common.error.CustomRuntimeException;
import com.lin.controller.req.AddCustomerGroupReqMsg;
import com.lin.controller.req.GroupAddMemberReqMsg;
import com.lin.po.CustomerGroup;

public interface CustomerGroupService {

    CustomerGroup createGroup(AddCustomerGroupReqMsg reqMsg) throws CustomRuntimeException;

    Integer addGroup(GroupAddMemberReqMsg reqMsg) throws CustomRuntimeException;
}
