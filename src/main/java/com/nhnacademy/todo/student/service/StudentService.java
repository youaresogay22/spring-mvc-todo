package com.nhnacademy.todo.student.service;

import com.nhnacademy.todo.exception.StudentNotFoundException;
import com.nhnacademy.todo.student.domain.Student;
import com.nhnacademy.todo.student.mapper.StudentMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;


@Service
@Transactional //setautocommit=false 설정과 같음, AOP 적용됨
public class StudentService {

    private final StudentMapper studentMapper;

    // mybatisconfig에서 mapper 생성하므로 런타임 전까지 알 수 없음
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

    public void updateStudent(Student student){
        studentMapper.update(student);
    }

}
