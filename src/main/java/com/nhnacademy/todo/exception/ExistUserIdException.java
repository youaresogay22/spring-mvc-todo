package com.nhnacademy.todo.exception;

/**
 * @Author : marco@nhnacademy.com
 * @Date : 30/03/2023
 */
public class ExistUserIdException extends RuntimeException{
    public ExistUserIdException(){
        super("exist user id");
    }
}
