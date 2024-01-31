package com.nhnacademy.todo.exception;

/**
 * @Author : marco@nhnacademy.com
 * @Date : 22/03/2023
 */
public class InvalidIdOrPassword extends RuntimeException {
    public InvalidIdOrPassword(){
        super("invalid id or  password");
    }
}
