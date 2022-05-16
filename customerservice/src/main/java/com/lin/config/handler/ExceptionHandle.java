package com.lin.config.handler;

import com.lin.common.error.CustomRuntimeException;
import com.lin.common.error.ErrorCode;
import com.lin.common.error.ReturnMessageUtil;
import com.lin.common.rest.ResMsg;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ExceptionHandle {
    private final static Logger logger = LoggerFactory.getLogger(ExceptionHandle.class);

    @ExceptionHandler(value = Exception.class)
    public ResMsg<Object> handle(Exception exception) {
        if (exception instanceof CustomRuntimeException) {
            CustomRuntimeException ex = (CustomRuntimeException) exception;
            return ReturnMessageUtil.error(ex.getErrorCode(), ex.getMessage());
        } else {
            logger.error("系统异常 {}", exception);
            return ReturnMessageUtil.error(ErrorCode.UN_KNOW_ERROR, "未知异常" + exception.getMessage());
        }
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Object bindException(MethodArgumentNotValidException e) {
        logger.error("MethodArgumentNotValidException error ",e);
        BindingResult bindingResult = e.getBindingResult();
        final ResMsg<Object> message = null;
        if (bindingResult.hasErrors()) {
            for (int i = 0; i < bindingResult.getAllErrors().size(); i++) {
                return ReturnMessageUtil.error(ErrorCode.ERR_CODE_INVALIDATION, "MethodArgumentNotValidException" + ErrorCode.ERR_CODE_INVALIDATION.getMessage() + bindingResult.getAllErrors().get(i).getDefaultMessage());
            }
        }
        return message;
    }
}