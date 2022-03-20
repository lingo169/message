package com.lin.service.chats;

import com.lin.common.error.CustomRuntimeException;
import com.lin.controller.res.ChatPageResMsg;
import com.lin.controller.res.CustomerChatPageResMsg;
import com.lin.dto.socketio.ChatObject;

public interface ChatsService {

    CustomerChatPageResMsg chatlist(Long id, int pageSize, int pageNo);

    ChatPageResMsg byCustomerId(Long id, int pageSize, int pageNo);

    Integer save(ChatObject data) throws CustomRuntimeException;
}
