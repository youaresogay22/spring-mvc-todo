package com.nhnacademy.todo.exception;

/**
 * @Author : marco@nhnacademy.com
 * @Date : 22/03/2023
 */
public class InvalidCartIdException extends RuntimeException {
    public InvalidCartIdException(){
        super("Invalid Cart id");
    }
}
