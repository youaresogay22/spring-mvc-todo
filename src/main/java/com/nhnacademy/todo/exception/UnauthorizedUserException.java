package com.nhnacademy.todo.exception;

/**
 * @Author : marco@nhnacademy.com
 * @Date : 16/03/2023
 */
public class UnauthorizedUserException extends RuntimeException {
    private static final String MESSAGE = "X-USER-ID를 찾을 수 없습니다.";
    public UnauthorizedUserException(){
        super(MESSAGE);
    }
}
