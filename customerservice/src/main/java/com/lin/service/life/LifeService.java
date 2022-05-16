package com.lin.service.life;

import com.lin.common.error.CustomRuntimeException;
import com.lin.controller.req.AddLifeReqMsg;
import com.lin.controller.req.LifeListReqMsg;
import com.lin.controller.res.LifePageResMsg;
import com.lin.po.Life;

public interface LifeService {
    Integer addrel(AddLifeReqMsg reqMsg) throws CustomRuntimeException;

    LifePageResMsg lifelist(LifeListReqMsg reqMsg);

    Life lifedetail(Long id) throws CustomRuntimeException;

    Integer del(Long id) throws CustomRuntimeException;
}
