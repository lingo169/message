package com.lin.service.customer;

import com.lin.common.error.CustomRuntimeException;
import com.lin.controller.req.CustomerReqMsg;
import com.lin.controller.res.CustomerPageResMsg;
import com.lin.controller.res.CustomerResMsg;
import com.lin.po.Customer;

import java.util.List;

public interface CustomerService {

    /**
     *
     * @param customerId
     * @param customerRelId
     * @return 1:新增待通过，0：已有关系；其他：添加错误
     * @throws CustomRuntimeException
     */
    Integer addrel(Long customerId,Long customerRelId) throws CustomRuntimeException;

    Integer agree(Long customerRelId,Long customerGroupId,String status) throws CustomRuntimeException;

    CustomerPageResMsg listrels(Customer c);

    CustomerResMsg detail(Long customerId);

    /**
     * 对新增（注册）的用户进行新增或者后续更新
     * @param reqMsg
     * @return
     */
    Integer save(CustomerReqMsg reqMsg);


}
