package com.nhnacademy.todo.controller;

import com.nhnacademy.todo.advice.CommonRestControllerAdvice;
import com.nhnacademy.todo.exception.StudentNotFoundException;
import com.nhnacademy.todo.student.domain.Student;
import com.nhnacademy.todo.student.service.StudentService;
import org.junit.jupiter.api.*;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@TestMethodOrder(value = MethodOrderer.OrderAnnotation.class)
class StudentRestControllerTest {

    MockMvc mockMvc;
    StudentService studentService;

    @BeforeEach
    void setUp() {
        studentService = mock(StudentService.class);
        mockMvc = MockMvcBuilders.standaloneSetup(new StudentRestController(studentService))
                .setControllerAdvice(CommonRestControllerAdvice.class)
                .build();
    }

    @Order(1)
    @Test
    @DisplayName("id:marco 조회")
    void getStudent() throws Exception {

        Student student = new Student("marco", "마르코", "M", 40);

        when(studentService.getStudent(anyString())).thenReturn(student);

        mockMvc.perform(
                        get("/students/marco")
                                .contentType(MediaType.APPLICATION_JSON)
                                .accept(MediaType.APPLICATION_JSON)
                )
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value("marco"))
                .andExpect(jsonPath("$.name").value("마르코"))
                .andExpect(jsonPath("$.age").value(40))
                .andExpect(jsonPath("$.gender").value("M"))
                .andReturn();
    }

    @Order(2)
    @Test
    @DisplayName("student-not-found")
    void studentNotFound() throws Exception {
        when(studentService.getStudent(anyString())).thenThrow(StudentNotFoundException.class);

        MvcResult mvcResult = mockMvc.perform(
                        get("/students/student100")
                                .contentType(MediaType.APPLICATION_JSON)
                                .accept(MediaType.APPLICATION_JSON)
                )
                .andDo(print())
                .andExpect(result -> assertTrue(result.getResolvedException() instanceof StudentNotFoundException))
                .andExpect(status().isNotFound())
                .andReturn();
    }

}