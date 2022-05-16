package com.lin.controller;

import com.lin.common.error.CustomRuntimeException;
import com.lin.common.error.ErrorCode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.validation.BindingResult;

public interface BaseController {
    Logger log = LoggerFactory.getLogger(BaseController.class);

    static void verify(BindingResult bindingResult) throws CustomRuntimeException {
        if (bindingResult.hasErrors()) {
            bindingResult.getAllErrors().forEach(o -> {
                log.error(" error message is: {}", o.getDefaultMessage());
            });
            throw new CustomRuntimeException(ErrorCode.ERR_CODE_INVALIDATION, bindingResult.getAllErrors().get(0).getDefaultMessage());
        }
    }
}
