package com.boss.bobi.config.exception;


import com.boss.bobi.common.enums.ResultCode;
import com.boss.bobi.common.model.CommonResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class ExceptionAdviceInterceptor {

    private static final Logger logger = LoggerFactory.getLogger(ExceptionAdviceInterceptor.class);





    @ExceptionHandler(MethodArgumentNotValidException.class)
    public CommonResult handleBValidException(MethodArgumentNotValidException exception){
        BindingResult bindingResult = exception.getBindingResult();
        Map<String, String> map = new HashMap<>();
        bindingResult.getFieldErrors().forEach(fieldError -> {
            map.put(fieldError.getField(),fieldError.getDefaultMessage());
        });
        return CommonResult.build(ResultCode.DEFEATED, map);
    }

    @ExceptionHandler(Exception.class)
    public CommonResult error(Exception ex){
        logger.error("Exception: ", ex);
        return CommonResult.build(ResultCode.DEFEATED);
    }

}
