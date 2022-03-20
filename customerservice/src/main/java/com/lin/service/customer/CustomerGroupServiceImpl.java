package com.lin.service.customer;

import com.lin.common.error.CustomRuntimeException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class CustomerGroupServiceImpl implements CustomerGroupService{
    Logger log = LoggerFactory.getLogger(CustomerGroupServiceImpl.class);
    @Override
    public Integer createGroup(Long customerId, Long customerRelId) throws CustomRuntimeException {
        return null;
    }

    @Override
    public Integer addGroup(Long customerId, Long customerRelId) throws CustomRuntimeException {
        return null;
    }
}
