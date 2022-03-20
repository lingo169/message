package com.lin.service.customer;

import com.lin.common.error.CustomRuntimeException;

public interface CustomerGroupService {

    Integer createGroup(Long customerId,Long customerRelId) throws CustomRuntimeException;

    Integer addGroup(Long customerId,Long customerRelId) throws CustomRuntimeException;
}
