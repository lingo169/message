package com.lin.service.chats;

import com.lin.common.error.CustomRuntimeException;
import com.lin.controller.req.ChatsDetailReqMsg;
import com.lin.controller.res.ChatPageResMsg;
import com.lin.controller.res.CustomerChatPageResMsg;
import com.lin.dto.socketio.ChatObject;
import com.lin.po.Chats;

import java.io.IOException;

public interface ChatsService {

    CustomerChatPageResMsg chatlist(Long id, int pageSize, int pageNo);

    ChatPageResMsg byCustomerId(Long id, int pageSize, int pageNo);

    Integer save(ChatObject data) throws CustomRuntimeException, IOException;

    ChatPageResMsg chatdetail(ChatsDetailReqMsg reqMsg);
}
