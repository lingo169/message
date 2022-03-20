package com.lin.controller.res;

import com.lin.po.CustomerGroup;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class CustomerChatPageResMsg {
    private Integer total;
    private List<CustomerGroup> records;
}
