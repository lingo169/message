package com.lin.service.customer;

import com.lin.common.constant.CommonConstant;
import com.lin.common.error.CustomRuntimeException;
import com.lin.common.error.ErrorCode;
import com.lin.common.utils.DateUtils;
import com.lin.common.utils.RedisSerializableUtils;
import com.lin.controller.req.CustomerReqMsg;
import com.lin.controller.res.CustomerPageResMsg;
import com.lin.controller.res.CustomerResMsg;
import com.lin.dao.CustomerMapper;
import com.lin.dao.CustomerRelsMapper;
import com.lin.po.Customer;
import com.lin.po.CustomerRels;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.List;

@Component
public class CustomerServiceImpl implements CustomerService {

    @Autowired
    private CustomerRelsMapper customerRelsMapper;
    @Autowired
    private CustomerMapper customerMapper;

    /**
     * 批次新增
     *
     * @param customerId
     * @param customerRelId
     * @return 0：申请中 1：已经确认
     * @throws CustomRuntimeException
     */
    @Override
    @Transactional
    public Integer addrel(Long customerId, Long customerRelId) throws CustomRuntimeException {

        // TODO 后续讨论是否删除该判断
        if (null == customerMapper.selectByPrimaryKey(customerRelId)) {
            throw new CustomRuntimeException(ErrorCode.CUSTOMER_NOT_EXISTS, ErrorCode.CUSTOMER_NOT_EXISTS.getMessage());
        }
        CustomerRels customerRels = customerRelsMapper.selectExistsByCustomerId(customerId);
        if (null != customerRels) {
            return 0;
        }
        /*TODO 批次新增*/
        CustomerRels cr = init(customerId, CustomerRels.OPT_FLAG_APPLICANT, CustomerRels.LEADER_FRIEND, CustomerRels.STATUS_CONFIRM);
        CustomerRels cr2 = init(customerRelId, CustomerRels.OPT_FLAG_RECIPIENT, CustomerRels.LEADER_FRIEND, CustomerRels.STATUS_INIT);
        List list = Arrays.asList(cr, cr2);
        int i = customerRelsMapper.insertBatch(list);
        return i;
    }

    @Override
    public Integer agree(Long customerRelId, Long customerGroupId, String status) throws CustomRuntimeException {
        CustomerRels customerRels = customerRelsMapper.selecByCIdAndGId(customerRelId, customerGroupId);
        if (null == customerRels) {
            throw new CustomRuntimeException(ErrorCode.CUSTOMER_NOT_EXISTS, ErrorCode.CUSTOMER_NOT_EXISTS.getMessage());
        }
        customerRels.setStatus(status);
        return customerRelsMapper.updateByPrimaryKeySelective(customerRels);
    }

    @Override
    public CustomerPageResMsg listrels(Customer c) {
        CustomerPageResMsg res = new CustomerPageResMsg();
        res.setRecords(customerMapper.listrels(c));
        res.setTotal(customerMapper.listrelscount(c.getId()));
        return res;
    }

    @Override
    public CustomerResMsg detail(Long customerId) {
        return null;
    }

    @Override
    public Integer save(CustomerReqMsg reqMsg) {

        return null;
    }

    CustomerRels init(Long customerId, String optFlag, String leader, String status) throws CustomRuntimeException {
        CustomerRels cr = new CustomerRels();
        cr.setId(Long.parseLong(RedisSerializableUtils.generateForLong(CommonConstant.DEFAULT_LENGTH)));
        cr.setCustomerGroupId(Long.parseLong(RedisSerializableUtils.generateForLong(CommonConstant.DEFAULT_LENGTH)));
        cr.setCustomerId(customerId);
        cr.setOptFlag(optFlag);
        cr.setRelFlag(CustomerRels.REL_FLAG_CUSTOMER);
        cr.setLeader(leader);
        cr.setStatus(status);
        cr.setCreateTime(DateUtils.fullDateFormat());
        cr.setUpdateTime(DateUtils.fullDateFormat());
        return cr;
    }

}
