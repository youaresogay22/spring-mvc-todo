package com.nhnacademy.todo.student.mapper;

import com.nhnacademy.todo.config.RootConfig;
import com.nhnacademy.todo.config.WebConfig;
import com.nhnacademy.todo.student.domain.Student;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.ContextHierarchy;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.transaction.annotation.Transactional;


@ExtendWith(SpringExtension.class)
@WebAppConfiguration
@ContextHierarchy({
        @ContextConfiguration(classes = {RootConfig.class}),
        @ContextConfiguration(classes = {WebConfig.class})
})
@Transactional
//@Import(value = {MybatisConfig.class, DatabaseConfig.class, DatabaseProperties.class})
class StudentMapperTest {

    @Autowired
    StudentMapper studentMapper;

    @Test
    @DisplayName("학생-조회_id_marco")
    void getStudent(){
        Student student = studentMapper.findById("marco").orElse(null);
        Assertions.assertAll(
                ()->Assertions.assertEquals("marco",student.getId()),
                ()->Assertions.assertEquals("마르코",student.getName()),
                ()->Assertions.assertEquals(40,student.getAge()),
                ()->Assertions.assertEquals("M",student.getGender())
        );
    }

    @Test
    @DisplayName("학생-등록")
    void saveStudent(){
        Student student = new Student("stduent01","학생01","F",30);
        studentMapper.save(student);

        Student student01 = studentMapper.findById("student01").orElse(null);
        Assertions.assertAll(
                ()->Assertions.assertEquals("stduent01",student.getId()),
                ()->Assertions.assertEquals("학생01",student.getName()),
                ()->Assertions.assertEquals(30,student.getAge()),
                ()->Assertions.assertEquals("F",student.getGender())
        );
    }
}