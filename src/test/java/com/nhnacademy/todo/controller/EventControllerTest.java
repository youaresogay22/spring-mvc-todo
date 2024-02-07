package com.nhnacademy.todo.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jayway.jsonpath.DocumentContext;
import com.jayway.jsonpath.JsonPath;
import com.jayway.jsonpath.internal.JsonContext;
import com.nhnacademy.todo.config.RootConfig;
import com.nhnacademy.todo.config.WebConfig;
import com.nhnacademy.todo.domain.Event;
import com.nhnacademy.todo.dto.EventCreatedResponseDto;
import com.nhnacademy.todo.dto.EventDto;
import com.nhnacademy.todo.service.EventService;
import lombok.extern.slf4j.Slf4j;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.ContextHierarchy;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.filter.CharacterEncodingFilter;

import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;


import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * @Author : marco@nhnacademy.com
 * @Date : 19/04/2023
 */
@Slf4j
@ExtendWith(SpringExtension.class)
@WebAppConfiguration
@ContextHierarchy(value = {
        @ContextConfiguration(classes = {RootConfig.class}),
        @ContextConfiguration(classes = {WebConfig.class})
})
class EventControllerTest {
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private WebApplicationContext webApplicationContext;

    @Autowired
    private EventService eventService;

    @BeforeEach
    void setup(){
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext)
                .addFilter(new CharacterEncodingFilter("UTF-8"))
                .build();
    }

    @Test
    @DisplayName("이벤트등록_성공")
    void createEvent() throws Exception {
        EventDto eventDto = EventDto.builder()
                .subject("Spring framework 학습")
                .eventAt(LocalDate.now())
                .build();

        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .post("/api/calendar/events")
                .content(objectMapper.writeValueAsString(eventDto))
                .header("X-USER-ID","marco")
                .contentType(MediaType.APPLICATION_JSON);

        mockMvc.perform(requestBuilder).andDo(print())
                .andExpect(status().isCreated());
    }

    @Test
    @DisplayName("삭제")
    void deleteEvent() throws Exception {
        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .delete("/api/calendar/events/{id}", 1 )
                .header("X-USER-ID","marco");

        mockMvc.perform(requestBuilder)
                .andDo(print())
                .andExpect(status().isNoContent());

    }

    @Test
    @DisplayName("일단위-삭제")
    void deleteEventByDaily() throws Exception {
        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .delete("/api/calendar/events/daily/{targetDate}", "2022-02-15")
                .header("X-USER-ID","marco");

        mockMvc.perform(requestBuilder)
                .andDo(print())
                .andExpect(status().isNoContent());
    }

    @Test
    void getEvent() throws Exception {

        EventDto eventDto = EventDto.builder()
                .subject("Spring framework 학습")
                .eventAt(LocalDate.now())
                .build();

        EventCreatedResponseDto eventCreatedResponseDto = eventService.insert("marco",eventDto);

        RequestBuilder requestBuilder =  MockMvcRequestBuilders
                .get("/api/calendar/events/{eventId}",eventCreatedResponseDto.getId())
                .header("X-USER-ID","marco")
                .contentType(MediaType.APPLICATION_JSON);

        MvcResult mvcResult = mockMvc.perform(requestBuilder)
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn();
        log.info("json:{}", mvcResult.getResponse().getContentAsString(StandardCharsets.UTF_8));
        DocumentContext context = JsonPath.parse(mvcResult.getResponse().getContentAsString(StandardCharsets.UTF_8));
        Assertions.assertThat(context.read("$.id").toString()).isEqualTo(String.valueOf(eventCreatedResponseDto.getId()));
        Assertions.assertThat(context.read("$.subject").toString()).isEqualTo(eventDto.getSubject());
        Assertions.assertThat(context.read("$.eventAt").toString()).isEqualTo(eventDto.getEventAt().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")));

    }

    @Test
    void getEventList() throws Exception {
        RequestBuilder requestBuilder = MockMvcRequestBuilders
                        .get("/api/calendar/events/?year={0}&month={1}",LocalDate.now().getYear(),LocalDate.now().getMonthValue())
                        .header("X-USER-ID","marco");
        MvcResult mvcResult = mockMvc.perform(requestBuilder)
                        .andDo(print())
                        .andReturn();
        DocumentContext context = JsonPath.parse(mvcResult.getResponse().getContentAsString(StandardCharsets.UTF_8));

        Assertions.assertThat(context.read("$.length()").toString()).isEqualTo(String.valueOf(8));


    }
}