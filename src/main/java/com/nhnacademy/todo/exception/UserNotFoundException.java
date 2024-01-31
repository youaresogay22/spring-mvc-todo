package com.nhnacademy.todo.exception;

/**
 * @Author : marco@nhnacademy.com
 * @Date : 21/03/2023
 */
public class UserNotFoundException extends RuntimeException {
    public UserNotFoundException(){
        super("User Not Found Exception");
    }
}
