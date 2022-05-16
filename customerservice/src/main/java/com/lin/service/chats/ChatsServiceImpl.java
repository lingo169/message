package com.lin.service.chats;

import com.lin.common.constant.CommonConstant;
import com.lin.common.error.CustomRuntimeException;
import com.lin.common.utils.BeanCopyUtils;
import com.lin.common.utils.CustomFileUtils;
import com.lin.common.utils.DateUtils;
import com.lin.common.utils.RedisSerializableUtils;
import com.lin.controller.req.ChatsDetailReqMsg;
import com.lin.controller.req.ListRelsReqMsg;
import com.lin.controller.res.ChatPageResMsg;
import com.lin.controller.res.CustomerChatPageResMsg;
import com.lin.dao.ChatsMapper;
import com.lin.dao.CustomerGroupMapper;
import com.lin.dao.CustomerRelsMapper;
import com.lin.dto.socketio.ChatObject;
import com.lin.po.Chats;
import com.lin.po.Customer;
import com.lin.po.CustomerGroup;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class ChatsServiceImpl implements ChatsService {
    Logger log = LoggerFactory.getLogger(ChatsServiceImpl.class);
    @Value("${cs.prefix-path}")
    private String prefixPath;
    @Autowired
    private CustomerGroupMapper customerGroupMapper;
    @Autowired
    private CustomerRelsMapper customerRelsMapper;

    @Autowired
    private ChatsMapper chatsMapper;

//    @Autowired
//    private RedissonClient redissonClient;

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
    public Integer save(ChatObject data) throws CustomRuntimeException, IOException {
        //转换file 的base64为文件存储
        String url = CustomFileUtils.buildFile(data.getFile(), data.getSuffix(), prefixPath, RedisSerializableUtils.generateOrderNo(CommonConstant.DEFAULT_LENGTH));
        Chats chats = BeanCopyUtils.beanCopy(data, Chats.class);
        chats.setFileUrl(url);
        chats.setReadFlag(Chats.READ_FLAG_UNREAD);
        insertSelectiveInit(chats);
        return chatsMapper.insertSelective(chats);
    }

    @Override
    public ChatPageResMsg chatdetail(ChatsDetailReqMsg reqMsg) {
        ChatPageResMsg cpr = new ChatPageResMsg();
        Map<String, Object> map = build(reqMsg);
        cpr.setRecords(chatsMapper.listchats(map));
        cpr.setTotal(chatsMapper.listchatscount(map));
        return cpr;
    }

    private Map<String, Object> build(ChatsDetailReqMsg reqMsg) {
        Map<String, Object> map = new HashMap<>();
        map.put("customerGroupId", reqMsg.getCustomerGroupId());
        map.put("pageNo", reqMsg.getPageNo());
        map.put("pageSize", reqMsg.getPageSize());
        return map;
    }

    private Chats insertSelectiveInit(Chats chats) throws CustomRuntimeException {
        chats.setId(Long.parseLong(RedisSerializableUtils.generateForLong(CommonConstant.DEFAULT_LENGTH)));

        chats.setCreateTime(DateUtils.fullDateFormat());
        chats.setUpdateTime(DateUtils.fullDateFormat());
        return chats;
    }

    /**
     * 输出有问题
     *
     * @return
     */
    /*private Long getRelationship(Long senderId) {
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
    }*/
    private Customer init(Long id, int pageSize, int pageNo) {
        Customer c = new Customer();
        c.setId(id);
        c.setPageNo(pageNo);
        c.setPageSize(pageSize);
        return c;
    }
}
