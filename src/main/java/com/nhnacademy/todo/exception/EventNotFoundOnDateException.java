package com.nhnacademy.todo.exception;

public class EventNotFoundOnDateException extends RuntimeException {
    public EventNotFoundOnDateException(String date) {
        super("event not found:" + date);
    }
}
