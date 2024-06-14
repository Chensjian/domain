package com.chen.domain.common;

import com.chen.domain.common.exception.ServiceException;
import com.chen.domain.common.result.Result;
import com.chen.domain.common.result.Results;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Component
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(value = RuntimeException.class)
    public Result defaultExceptionHandler(RuntimeException e){
        return Results.failure("B00001","系统执行异常");
    }

    @ExceptionHandler(value = ServiceException.class)
    public Result defaultExceptionHandler(ServiceException e) {
        return Results.failure("B00001", e.getMessage());
    }
}
