package com.lin.consume;

import com.lin.config.SpringContextIniter;
import com.lin.dto.socketio.MessageBase;

public interface ConsumerHandler {
    void onMessage(MessageBase msg);

    static ConsumerHandler getBean(String beanName){
        return SpringContextIniter.getBean(beanName,ConsumerHandler.class);
    }
}
