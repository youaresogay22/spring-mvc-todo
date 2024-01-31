package com.nhnacademy.todo.advice;

import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class CommonRestControllerAdvice {
    @InitBinder
    public void initBinder(WebDataBinder binder){
        binder.initDirectFieldAccess();
    }

}
