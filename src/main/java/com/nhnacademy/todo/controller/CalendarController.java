package com.nhnacademy.todo.controller;

import com.nhnacademy.todo.dto.DailyRegisterCountRequestDto;
import com.nhnacademy.todo.dto.DailyRegisterCountResponseDto;
import com.nhnacademy.todo.service.EventService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

/**
 * @Author : marco@nhnacademy.com
 * @Date : 16/03/2023
 */
@RestController
@RequestMapping("/api/calendar")
@RequiredArgsConstructor
public class CalendarController {
    private final EventService eventService;

    @GetMapping("/daily-register-count")
    public DailyRegisterCountResponseDto dailyRegisterCount(@RequestHeader(name = "X-USER-ID", required = true)String userId, @Valid DailyRegisterCountRequestDto dailyRegisterCountRequestDto){
        return eventService.getDayliyRegisterCount(userId, dailyRegisterCountRequestDto.getDate());
    }

}
