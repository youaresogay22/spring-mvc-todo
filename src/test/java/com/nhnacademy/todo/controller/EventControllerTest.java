package com.nhnacademy.todo.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.nhnacademy.todo.advice.CommonRestControllerAdvice;
import com.nhnacademy.todo.event.domain.Event;
import com.nhnacademy.todo.event.service.EventService;
import com.nhnacademy.todo.exception.StudentNotFoundException;
import com.nhnacademy.todo.student.service.StudentService;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Slf4j
@ExtendWith(MockitoExtension.class)
class EventControllerTest {
    MockMvc mockMvc;
    EventService eventService;
    ObjectMapper mapper;

    @BeforeEach
    void setUp() {
        eventService = mock(EventService.class);
        mockMvc = MockMvcBuilders.standaloneSetup(new EventController(eventService))
                .setControllerAdvice(CommonRestControllerAdvice.class)
                .build();
        mapper = new ObjectMapper();
    }

    @Test
    void saveEvent() throws Exception {
        when(eventService.saveEvent(any(Event.class))).thenReturn(7L);

        Map<String, String> map = new HashMap<>();
        map.put("subject", "test subject");
        map.put("eventAt", "2023-02-01");

        MvcResult mvcResult = mockMvc.perform(
                        post("/api/calendar/events")
                                .contentType(MediaType.APPLICATION_JSON)
                                .characterEncoding("utf-8")
                                .accept(MediaType.APPLICATION_JSON)
                                .header("X-USER-ID", "123")
                                .content(mapper.writeValueAsString(map)))
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(7))
                .andReturn();

        String req_id = mvcResult.getRequest().getHeader("X-USER-ID");
        //String req_subject = mvcResult.getRequest().get("X-USER-ID");
        String req_eventAt = mvcResult.getRequest().getContentAsString();
    }

    @Test
    void getEventByEventId() {
    }

    @Test
    void getEventByYearMonthDay() {
    }

    @Test
    void getEventByYearMonth() {
    }

    @Test
    void deleteEvent() {
    }

    @Test
    void deleteEventOfWholeDay() {
    }

    @Test
    void countEventOfWholeDay() throws Exception {
        when(eventService.countEventsOfDay(anyString())).thenReturn(7);

        MvcResult mvcResult = mockMvc.perform(
                        get("/api/calendar/daily-register-count?date=2023-03-01")
                                .contentType(MediaType.APPLICATION_JSON)
                                .accept(MediaType.APPLICATION_JSON)
                )
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.count").value(7))
                .andReturn();
    }
}