package com.lin.controller.res;

import com.lin.po.Customer;
import com.lin.po.CustomerGroup;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class CustomerPageResMsg {
    private Integer total;
    private List<Customer> records;
}
