//package com.lin.controller.test;
//
//import com.lin.service.socketio.SocketIOService;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.RequestParam;
//import org.springframework.web.bind.annotation.RestController;
//
//@RestController
//public class SocketIOController {
//    @Autowired
//    SocketIOService socketIOService;
//
//    @GetMapping("/sendMessage")
//    public String sendMessage(@RequestParam String topic, @RequestParam(required = false) String userId, @RequestParam String msgContent) {
//        socketIOService.sendMessage(topic, userId, msgContent);
//        return "消息发送成功";
//    }
//}