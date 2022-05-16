package com.lin.controller;

import com.lin.common.error.CustomRuntimeException;
import com.lin.common.rest.ResMsg;
import com.lin.controller.req.AddLifeReqMsg;
import com.lin.controller.req.LifeListReqMsg;
import com.lin.controller.res.LifePageResMsg;
import com.lin.po.Life;
import com.lin.service.life.LifeService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@Api(tags = "生活入口")
@RestController
@RequestMapping("/msvc")
public class LifeController {

    @Autowired
    private LifeService lifeService;

    @ApiOperation("新增生活接口")
    @PostMapping("/addlife")
    public ResMsg<Integer> addrel(@Valid @RequestBody AddLifeReqMsg reqMsg, BindingResult bindingResult) throws CustomRuntimeException {
        BaseController.verify(bindingResult);
        ResMsg<Integer> urs = new ResMsg<>();
        Integer i = lifeService.addrel(reqMsg);
        return urs.withData(i);
    }

    @ApiOperation("查看生活列表接口")
    @PostMapping("/lifelist")
    public ResMsg<LifePageResMsg> lifelist(@Valid @RequestBody LifeListReqMsg reqMsg, BindingResult bindingResult) throws CustomRuntimeException {
        BaseController.verify(bindingResult);
        ResMsg<LifePageResMsg> urs = new ResMsg<>();
        return urs.withData(lifeService.lifelist(reqMsg));
    }

    @ApiOperation("查看生活详情接口")
    @GetMapping("/lifedetail/{id}")
    public ResMsg<Life> lifedetail(@PathVariable Long id) throws CustomRuntimeException {
        ResMsg<Life> urs = new ResMsg<>();
        return urs.withData(lifeService.lifedetail(id));
    }

    @ApiOperation("删除生活详情接口")
    @DeleteMapping("/lifedel/{id}")
    public ResMsg<Integer> lifedel(@PathVariable Long id) throws CustomRuntimeException {
        ResMsg<Integer> urs = new ResMsg<>();
        return urs.withData(lifeService.del(id));
    }
}
