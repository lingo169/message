package com.lin.consume;

import com.lin.common.constant.CustomerServiceConstant;
import com.lin.common.utils.DateUtils;
import com.lin.dao.CustomerGroupMapper;
import com.lin.dto.socketio.ChatObject;
import com.lin.dto.socketio.MessageBase;
import com.lin.po.CustomerGroup;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component(RocketMqConstant.UPDATE_GROUP_TOPIC+ CustomerServiceConstant.CONSUMER_HANDLER)
public class UpdateContentConsumer implements ConsumerHandler {
    private static Logger log = LoggerFactory.getLogger(UpdateContentConsumer.class);
    @Autowired
    private CustomerGroupMapper customerGroupMapper;

    @Override
    public void onMessage(MessageBase msgExt) {
        ChatObject co = (ChatObject)msgExt;
        log.info("UpdateContentConsumer on message body :{}", co.toString());
        CustomerGroup cg = new CustomerGroup();
        cg.setId(co.getCustomerGroupId());
        cg.setContent(co.getContent());
        cg.setEndTime(DateUtils.timeFormat());
        cg.setUpdateTime(DateUtils.fullDateFormat());
        log.info("UpdateContentConsumer hander SUCCESS：{}", customerGroupMapper.updateByPrimaryKeySelective(cg));
    }

}
