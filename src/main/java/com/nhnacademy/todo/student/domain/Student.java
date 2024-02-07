package com.nhnacademy.todo.student.domain;

import java.time.LocalDateTime;

public class Student {
    private String id;
    private String name;
    private String gender;
    private int age;
    private LocalDateTime createdAt;

    public Student(String id, String name, String gender, int age) {
        this.id = id;
        this.name = name;
        this.gender = gender;
        this.age = age;
        this.createdAt = LocalDateTime.now();
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getGender() {
        return gender;
    }

    public int getAge() {
        return age;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }
}
