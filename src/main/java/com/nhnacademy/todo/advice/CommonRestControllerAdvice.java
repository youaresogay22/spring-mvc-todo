package com.nhnacademy.todo.advice;

import com.nhnacademy.todo.exception.StudentNotFoundException;
import com.nhnacademy.todo.exception.UserNotFoundException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class CommonRestControllerAdvice {
    @InitBinder
    public void initBinder(WebDataBinder binder){
        binder.initDirectFieldAccess();
    }


    @ExceptionHandler(StudentNotFoundException.class)
    public ResponseEntity studentNotFoundExHandler(){
        return ResponseEntity
                .notFound()
                .build();
    }
}
