package com.nhnacademy.todo.controller;

import com.nhnacademy.todo.student.domain.Student;
import com.nhnacademy.todo.student.service.StudentService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/students")
public class StudentRestController {

    private final StudentService studentService;

    public StudentRestController(StudentService studentService) {
        this.studentService = studentService;
    }


    @GetMapping("/{id}")
    public Student getStudent(@PathVariable("id")String id){
        Student student = studentService.getStudent(id);

        return student;
    }
}
