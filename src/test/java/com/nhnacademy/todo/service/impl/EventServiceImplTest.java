package com.nhnacademy.todo.service.impl;

import com.nhnacademy.todo.domain.Event;
import com.nhnacademy.todo.dto.EventCreatedResponseDto;
import com.nhnacademy.todo.dto.EventDto;
import com.nhnacademy.todo.repository.EventRepository;
import com.nhnacademy.todo.service.EventService;
import com.sun.jdi.request.EventRequest;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * @Author : marco@nhnacademy.com
 * @Date : 20/04/2023
 */
class EventServiceImplTest {

    private EventService eventService;
    private EventRepository eventRepository;

    @BeforeEach
    void setUp() {
        eventRepository = mock(EventRepository.class);
        eventService = new EventServiceImpl(eventRepository);
    }

    @Test
    void insert() {
        //given
        Event event = new Event("marco","Spring mvc study", LocalDate.now());
        event.setId(1l);

        EventDto eventdto = new EventDto(0l,"Student mvc study",LocalDate.now());

        //when
        Mockito.doReturn(event).when(eventRepository).save(any());
        EventCreatedResponseDto eventCreatedResponseDto = eventService.insert("marco", eventdto );

        //then
        Assertions.assertThat(eventCreatedResponseDto.getId()).isEqualTo(1l);

    }
}