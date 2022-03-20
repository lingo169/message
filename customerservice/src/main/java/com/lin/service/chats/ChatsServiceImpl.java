package com.lin.service.chats;

import com.lin.common.constant.RedisConstant;
import com.lin.common.error.CustomRuntimeException;
import com.lin.common.error.ErrorCode;
import com.lin.common.utils.BeanCopyUtils;
import com.lin.controller.res.ChatPageResMsg;
import com.lin.controller.res.CustomerChatPageResMsg;
import com.lin.dao.CustomerGroupMapper;
import com.lin.dao.CustomerRelsMapper;
import com.lin.dto.socketio.ChatObject;
import com.lin.po.Chats;
import com.lin.po.Customer;
import com.lin.po.CustomerGroup;
import com.lin.po.CustomerRels;
import org.redisson.api.RBucket;
import org.redisson.api.RList;
import org.redisson.api.RedissonClient;
import org.redisson.client.RedisClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.concurrent.TimeUnit;

@Component
public class ChatsServiceImpl implements ChatsService {
    Logger log = LoggerFactory.getLogger(ChatsServiceImpl.class);
    @Autowired
    private CustomerGroupMapper customerGroupMapper;
    @Autowired
    private CustomerRelsMapper customerRelsMapper;
    @Autowired
    private RedissonClient redissonClient;

    @Override
    public CustomerChatPageResMsg chatlist(Long id, int pageSize, int pageNo) {
        CustomerChatPageResMsg cpr = new CustomerChatPageResMsg();
        Customer c = init(id, pageSize, pageNo);
        List<CustomerGroup> list = customerGroupMapper.listchats(c);
        cpr.setRecords(list);
        cpr.setTotal(customerGroupMapper.listchatscount(c));
        return cpr;
    }

    @Override
    public ChatPageResMsg byCustomerId(Long id, int pageSize, int pageNo) {
        Customer c = init(id, pageSize, pageNo);
        return null;
    }

    @Override
    public Integer save(ChatObject data) throws CustomRuntimeException {
        Long recipient = getRelationship(data.getSender());
        if (recipient == null) {
            throw new CustomRuntimeException(ErrorCode.RELATIONSHIP_NOT_EXISTS, ErrorCode.RELATIONSHIP_NOT_EXISTS.getMessage());
        }
        Chats chats = BeanCopyUtils.beanCopy(data, Chats.class);

        return null;
    }

    /**
     * 输出有问题
     *
     * @param senderId
     * @return
     */
    private Long getRelationship(Long senderId) {
        RBucket<Long> rb = redissonClient.getBucket(RedisConstant.CUSTOMER_RELATIONSHIP + senderId);
        Long recipient = rb.get();
        if (recipient == null) {
            CustomerRels customer = customerRelsMapper.selectExistsByCustomerId(senderId);
            if(customer==null){
                return null;
            }
            rb.set(customer.getCustomerId());
            rb.expire(1, TimeUnit.DAYS);
            recipient = customer.getCustomerId();
        }
        return recipient;
    }

    private Customer init(Long id, int pageSize, int pageNo) {
        Customer c = new Customer();
        c.setId(id);
        c.setPageNo(pageNo);
        c.setPageSize(pageSize);
        return c;
    }
}
