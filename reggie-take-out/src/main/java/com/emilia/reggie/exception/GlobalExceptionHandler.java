package com.emilia.reggie.exception;

import com.emilia.reggie.common.R;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice//REST风格的异常处理
public class GlobalExceptionHandler {

    @ExceptionHandler(NameExistsException.class)
    public R nameExistsException(NameExistsException e) {
        return R.error(e.getMessage());
    }

    @ExceptionHandler(CustomerRelationException.class)
    public R customerRelationException(CustomerRelationException e) {
        return R.error(e.getMessage());
    }

    @ExceptionHandler(Exception.class)
    public R exceptionHandler(Exception e) {
        e.printStackTrace();
        return R.error("正在拼命加载中......");
    }
}