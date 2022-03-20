package com.lin.controller.res;

import com.lin.po.Chats;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class ChatPageResMsg {
    private Integer total;
    private List<Chats> records;
}
