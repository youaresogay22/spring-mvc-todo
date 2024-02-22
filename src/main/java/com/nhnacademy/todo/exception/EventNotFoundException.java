package com.nhnacademy.todo.exception;

public class EventNotFoundException extends RuntimeException{
    public EventNotFoundException(long id) {
        super("event not found:" + id);
    }
}
