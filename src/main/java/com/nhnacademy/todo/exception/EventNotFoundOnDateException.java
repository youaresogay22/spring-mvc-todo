package com.nhnacademy.todo.exception;

public class EventNotFoundOnDateException extends RuntimeException {
    public EventNotFoundOnDateException(int date) {
        super("event not found:" + date);
    }
}
