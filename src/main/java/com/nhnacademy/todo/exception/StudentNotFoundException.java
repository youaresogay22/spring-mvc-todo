package com.nhnacademy.todo.exception;

public class StudentNotFoundException extends RuntimeException{
    public StudentNotFoundException(String id) {
        super("student not found:" + id);
    }
}
