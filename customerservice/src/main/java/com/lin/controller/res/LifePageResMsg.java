package com.lin.controller.res;

import com.lin.po.Life;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class LifePageResMsg {
    private Integer total;
    private List<Life> records;
}
