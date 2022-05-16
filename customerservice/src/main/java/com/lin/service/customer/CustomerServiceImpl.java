package com.lin.service.customer;

import com.lin.common.constant.CommonConstant;
import com.lin.common.constant.RedisConstant;
import com.lin.common.error.CustomRuntimeException;
import com.lin.common.error.ErrorCode;
import com.lin.common.utils.BeanCopyUtils;
import com.lin.common.utils.DateUtils;
import com.lin.common.utils.RedisSerializableUtils;
import com.lin.controller.req.AgreeCustomerReqMsg;
import com.lin.controller.req.CustomerReqMsg;
import com.lin.controller.res.CustomerPageResMsg;
import com.lin.controller.res.CustomerResMsg;
import com.lin.dao.CustomerMapper;
import com.lin.dao.CustomerRelsMapper;
import com.lin.po.Customer;
import com.lin.po.CustomerRels;
import org.redisson.api.RBucket;
import org.redisson.api.RedissonClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

@Component
public class CustomerServiceImpl implements CustomerService {
    Logger log = LoggerFactory.getLogger(CustomerServiceImpl.class);
    @Autowired
    private CustomerRelsMapper customerRelsMapper;
    @Autowired
    private CustomerMapper customerMapper;
    @Autowired
    private RedissonClient redissonClient;

//    @Override
//    public Customer relationshipByCustomerId(Long id) {
//        RBucket<Customer> rb = redissonClient.getBucket(RedisConstant.CUSTOMER_RELATIONSHIP + id);
//        Customer c = rb.get();
//        if (c == null) {
//            c = customerMapper.selectRelationshipByCustomerId(id);
//            if (c != null) {
//                rb.set(c);
//            }
//        }
//        return c;
//    }

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
        CustomerRels customerRels = customerRelsMapper.selectExistsByCustomerId(customerId,customerRelId);
        if (null != customerRels) {
            switch (customerRels.getStatus()) {
                case CustomerRels.STATUS_INIT: {
                    customerRels.setUpdateTime(DateUtils.fullDateFormat());
                    return customerRelsMapper.updateByPrimaryKeySelective(customerRels);
                }
                case CustomerRels.STATUS_CONFIRM:
                    throw new CustomRuntimeException(ErrorCode.RELATIONSHIP_EXISTS, ErrorCode.RELATIONSHIP_EXISTS.getMessage());
                case CustomerRels.STATUS_REFUSE:
                    throw new CustomRuntimeException(ErrorCode.RELATIONSHIP_REFUSE, ErrorCode.RELATIONSHIP_REFUSE.getMessage());
                default:
            }
        }
        Long groupId = Long.parseLong(RedisSerializableUtils.generateForLong(CommonConstant.DEFAULT_LENGTH));
        /*TODO 批次新增*/
        CustomerRels cr = init(customerId, groupId, customerRelId, CustomerRels.OPT_FLAG_APPLICANT, CustomerRels.LEADER_FRIEND, CustomerRels.STATUS_CONFIRM);
        CustomerRels cr2 = init(customerRelId, groupId, customerId, CustomerRels.OPT_FLAG_RECIPIENT, CustomerRels.LEADER_FRIEND, CustomerRels.STATUS_INIT);
        List list = Arrays.asList(cr, cr2);
        int i = customerRelsMapper.insertBatch(list);
        return i;
    }

    @Override
    public Integer agree(AgreeCustomerReqMsg reqMsg) throws CustomRuntimeException {
        CustomerRels cr = BeanCopyUtils.beanCopy(reqMsg,CustomerRels.class);
        CustomerRels customerRels = customerRelsMapper.selecByCIdAndGId(cr);
        if (null == customerRels) {
            throw new CustomRuntimeException(ErrorCode.RELATIONSHIP_NOT_EXISTS, ErrorCode.RELATIONSHIP_NOT_EXISTS.getMessage());
        }else if(customerRels.getStatus()==CustomerRels.STATUS_CONFIRM){
            return 0;
        }
        customerRels.setStatus(reqMsg.getStatus());
        customerRels.setUpdateTime(DateUtils.fullDateFormat());
        return customerRelsMapper.updateByPrimaryKeySelective(customerRels);
    }

    @Override
    public CustomerPageResMsg listrels(Map<String,Object> map) {
        CustomerPageResMsg res = new CustomerPageResMsg();
        res.setRecords(customerMapper.listrels(map));
        res.setTotal(customerMapper.listrelscount(map));
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

    CustomerRels init(Long customerId, Long groupId, Long customerRelId, String optFlag, String leader, String status) throws CustomRuntimeException {
        CustomerRels cr = new CustomerRels();
        cr.setId(Long.parseLong(RedisSerializableUtils.generateForLong(CommonConstant.DEFAULT_LENGTH)));
        cr.setCustomerGroupId(groupId);
        cr.setCustomerId(customerId);
        cr.setCustomerRelId(customerRelId);
        cr.setOptFlag(optFlag);
        cr.setRelFlag(CustomerRels.REL_FLAG_CUSTOMER);
        cr.setLeader(leader);
        cr.setStatus(status);
        cr.setCreateTime(DateUtils.fullDateFormat());
        cr.setUpdateTime(DateUtils.fullDateFormat());
        return cr;
    }

}
