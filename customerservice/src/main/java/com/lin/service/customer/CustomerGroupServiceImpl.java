package com.lin.service.customer;

import com.lin.common.constant.CommonConstant;
import com.lin.common.constant.RedisConstant;
import com.lin.common.error.CustomRuntimeException;
import com.lin.common.error.ErrorCode;
import com.lin.common.utils.BeanCopyUtils;
import com.lin.common.utils.DateUtils;
import com.lin.common.utils.RedisSerializableUtils;
import com.lin.config.RequestUtils;
import com.lin.controller.req.AddCustomerGroupReqMsg;
import com.lin.controller.req.GroupAddMemberReqMsg;
import com.lin.dao.CustomerGroupMapper;
import com.lin.dao.CustomerMapper;
import com.lin.dao.CustomerRelsMapper;
import com.lin.po.Customer;
import com.lin.po.CustomerGroup;
import com.lin.po.CustomerRels;
import org.redisson.api.RBucket;
import org.redisson.api.RedissonClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

@Component
public class CustomerGroupServiceImpl implements CustomerGroupService {
    Logger log = LoggerFactory.getLogger(CustomerGroupServiceImpl.class);
    @Autowired
    private CustomerMapper customerMapper;
    @Autowired
    private RedissonClient redissonClient;
    @Autowired
    private CustomerGroupMapper customerGroupMapper;
    @Autowired
    private CustomerRelsMapper customerRelsMapper;

    @Transactional
    @Override
    public CustomerGroup createGroup(AddCustomerGroupReqMsg reqMsg) throws CustomRuntimeException {
        //查看成员是否存在
        Customer c = RequestUtils.getCustomer();
        Integer i = customerMapper.countByIds(reqMsg.getCustomerRelIds());
        if (c == null || !c.getId().equals(reqMsg.getCustomerId()) || !i.equals(reqMsg.getCustomerRelIds().size())) {
            throw new CustomRuntimeException(ErrorCode.ERR_CODE_INVALIDATION, ErrorCode.ERR_CODE_INVALIDATION.getMessage());
        }
        Long groupId = Long.parseLong(RedisSerializableUtils.generateForLong(CommonConstant.DEFAULT_LENGTH));

        CustomerGroup cg = bulidGroup(groupId, reqMsg);
        //insert group
        customerGroupMapper.insertSelective(cg);
        //batch insert CustomerRels
        List<CustomerRels> list = new ArrayList<>();
        reqMsg.getCustomerRelIds().forEach(item -> {
            try {
                list.add(bulidCustomerRels(item, groupId, CustomerRels.OPT_FLAG_RECIPIENT, CustomerRels.LEADER_UNIVERSAL, CustomerRels.REL_FLAG_GROUP, CustomerRels.STATUS_CONFIRM));
            } catch (CustomRuntimeException e) {
                log.error("bulidCustomerRels error", e);
            }
        });
        //build group creater
        list.add(bulidCustomerRels(reqMsg.getCustomerId(), groupId, CustomerRels.OPT_FLAG_APPLICANT, CustomerRels.LEADER_SUPER, CustomerRels.REL_FLAG_GROUP, CustomerRels.STATUS_CONFIRM));
        customerRelsMapper.insertBatch(list);
        return cg;
    }

    @Override
    public Integer addGroup(GroupAddMemberReqMsg reqMsg) throws CustomRuntimeException {
        CustomerGroup cg = customerGroupMapper.selectByPrimaryKey(reqMsg.getCustomerGroupId());
        if(cg == null){
            throw new CustomRuntimeException(ErrorCode.GROUP_NOT_EXISTS, ErrorCode.GROUP_NOT_EXISTS.getMessage());
        }
        //这里认为reqMsg.getCustomerRelIds()都是未添加到该组的，暂不做判断 要做就做双层循环，性能下降；
        List<CustomerRels> list = new ArrayList<>();
        reqMsg.getCustomerRelIds().forEach(item -> {
            try {
                list.add(bulidCustomerRels(item, reqMsg.getCustomerGroupId(), CustomerRels.OPT_FLAG_RECIPIENT, CustomerRels.LEADER_UNIVERSAL, CustomerRels.REL_FLAG_GROUP, CustomerRels.STATUS_CONFIRM));
            } catch (CustomRuntimeException e) {
                log.error("bulidCustomerRels error", e);
            }
        });
        return customerRelsMapper.insertBatch(list);
    }

    private CustomerRels bulidCustomerRels(Long customerId, Long groupId, String optFlag, String leader, String relFlag, String status) throws CustomRuntimeException {
        CustomerRels cr = new CustomerRels();
        cr.setId(Long.parseLong(RedisSerializableUtils.generateForLong(CommonConstant.DEFAULT_LENGTH)));
        cr.setCustomerId(customerId);
        cr.setCustomerGroupId(groupId);
        cr.setOptFlag(optFlag);
        cr.setLeader(leader);
        cr.setRelFlag(relFlag);
        cr.setStatus(status);
        cr.setCreateTime(DateUtils.fullDateFormat());
        cr.setUpdateTime(DateUtils.fullDateFormat());
        return cr;
    }

    private CustomerGroup bulidGroup(Long groupId, AddCustomerGroupReqMsg reqMsg) throws CustomRuntimeException {
        CustomerGroup cg = new CustomerGroup();
        cg.setId(groupId);
        cg.setCustomerGroupNo(RedisSerializableUtils.generateForLong(CommonConstant.DEFAULT_LENGTH));
        cg.setGroupFlag(CustomerGroup.GROUP_FLAG_MANY);
        cg.setCustomerGroupName(reqMsg.getGroupName());
        cg.setPurpose(reqMsg.getPurpose());
        cg.setPrivateFlag(CustomerGroup.PUBLIC_FLAG);
        cg.setSender(reqMsg.getCustomerId());
        cg.setStatus(CustomerGroup.STATUS_AVAILABLE);
        cg.setCreateTime(DateUtils.fullDateFormat());
        cg.setUpdateTime(DateUtils.fullDateFormat());
        return cg;
    }

}
