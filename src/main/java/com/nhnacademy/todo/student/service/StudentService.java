package com.nhnacademy.todo.student.service;

import com.nhnacademy.todo.exception.StudentNotFoundException;
import com.nhnacademy.todo.student.domain.Student;
import com.nhnacademy.todo.student.mapper.StudentMapper;
import org.springframework.stereotype.Service;

import java.util.Optional;


@Service
public class StudentService {

    private final StudentMapper studentMapper;

    public StudentService(StudentMapper studentMapper) {
        this.studentMapper = studentMapper;
    }

    public Student getStudent(String id){
        Optional<Student> studentOptional = studentMapper.findById(id);
        if(studentOptional.isEmpty()){
            throw new StudentNotFoundException(id);
        }
        return studentOptional.get();
    }
}
