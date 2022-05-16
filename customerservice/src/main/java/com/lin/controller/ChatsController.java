package com.lin.controller;

import com.lin.common.error.CustomRuntimeException;
import com.lin.common.error.ErrorCode;
import com.lin.common.rest.ResMsg;
import com.lin.controller.req.ChatListReqMsg;
import com.lin.controller.req.ChatsDetailReqMsg;
import com.lin.controller.res.ChatPageResMsg;
import com.lin.controller.res.CustomerChatPageResMsg;
import com.lin.service.chats.ChatsService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.Date;

@Api(tags = "聊天入口")
@RestController
@RequestMapping("/msvc")
public class ChatsController implements BaseController {

    @Autowired
    private ChatsService chatsService;

    @ApiOperation("获取好友/群聊天列表接口")
    @PostMapping("/chatlist")
    public ResMsg<CustomerChatPageResMsg> chatlist(@Valid @RequestBody ChatListReqMsg reqMsg, BindingResult bindingResult) throws InvalidKeySpecException, NoSuchAlgorithmException, CustomRuntimeException {
        BaseController.verify(bindingResult);
        ResMsg<CustomerChatPageResMsg> urs = new ResMsg<>();
        return urs.withData(chatsService.chatlist(reqMsg.getCustomerId(), reqMsg.getPageSize(), reqMsg.getPageNo()));
    }

    @ApiOperation("获取聊天信息接口")
    @PostMapping("/chatdetail")
    public ResMsg<ChatPageResMsg> chatdetail(@Valid @RequestBody ChatsDetailReqMsg reqMsg/*, BindingResult bindingResult*/) throws CustomRuntimeException {
        //BaseController.verify(bindingResult);
        ResMsg<ChatPageResMsg> urs = new ResMsg<>();
        return urs.withData(chatsService.chatdetail(reqMsg));
    }

    public static void main(String[] args) {
        System.out.println(new Date().getTime());
    }
}
