package com.nhnacademy.todo.student.mapper;

import com.nhnacademy.todo.student.domain.Student;

import java.util.Optional;

public interface StudentMapper {
    Optional<Student> findById(String id);

    void save(Student student);

    void update(Student student);
}
