package com.lin.service.customer;

import com.lin.common.error.CustomRuntimeException;
import com.lin.controller.req.AgreeCustomerReqMsg;
import com.lin.controller.req.CustomerReqMsg;
import com.lin.controller.res.CustomerPageResMsg;
import com.lin.controller.res.CustomerResMsg;
import com.lin.po.Customer;

import java.util.List;
import java.util.Map;

public interface CustomerService {

    /**
     * 通过客户ID获取，如果REDIS中获取不到结果，则到数据库中获取；
     * @param id
     * @return
     */
//    Customer relationshipByCustomerId(Long id);
    /**
     *
     * @param customerId
     * @param customerRelId
     * @return 1:新增待通过，0：已有关系；其他：添加错误
     * @throws CustomRuntimeException
     */
    Integer addrel(Long customerId,Long customerRelId) throws CustomRuntimeException;

    Integer agree(AgreeCustomerReqMsg reqMsg) throws CustomRuntimeException;

    CustomerPageResMsg listrels(Map<String,Object> map);

    CustomerResMsg detail(Long customerId);

    /**
     * 对新增（注册）的用户进行新增或者后续更新
     * @param reqMsg
     * @return
     */
    Integer save(CustomerReqMsg reqMsg);


}
