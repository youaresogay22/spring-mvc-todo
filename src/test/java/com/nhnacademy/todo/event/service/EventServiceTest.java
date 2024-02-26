package com.nhnacademy.todo.event.service;

import com.nhnacademy.todo.event.domain.Event;
import com.nhnacademy.todo.event.mapper.EventMapper;
import com.nhnacademy.todo.exception.EventNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.*;

@Slf4j
@ExtendWith(MockitoExtension.class)
class EventServiceTest {
    EventMapper eventMapper = Mockito.mock(EventMapper.class);
    EventService eventService = new EventService(eventMapper);
    Event test_event = new Event(
            99,
            "jsj",
            "todo test",
            LocalDate.of(2024, 2, 1),
            LocalDateTime.now()
    );


    @Test
    void saveEvent() {
        Mockito.when(eventMapper.getLastEventId()).thenReturn(98L);

        long result = eventService.saveEvent(test_event);

        Mockito.verify(eventMapper, Mockito.times(1)).getLastEventId();
        Mockito.verify(eventMapper, Mockito.times(1)).save(any(Event.class));
        Assertions.assertEquals(result, 99);
    }

    @Test
    void deleteEvent() {
        Mockito.doNothing().when(eventMapper).deleteTodoItem(anyLong());
        eventService.deleteEvent(anyLong());
        Mockito.verify(eventMapper, Mockito.times(1)).deleteTodoItem(anyLong());
    }

    @Test
    void deleteEventOfWholeDay() {
        eventService.deleteEventOfWholeDay(any());
        Mockito.verify(eventMapper, Mockito.times(1)).deleteTodoItemOfDay(any());
    }

    @Test
    void getEvent() {
        Mockito.when(eventMapper.getTodoItem(anyLong())).thenReturn(Optional.of(test_event));

        Event result = eventService.getEvent(99L);

        Mockito.verify(eventMapper, Mockito.times(1)).getTodoItem(anyLong());
        Assertions.assertEquals(result, test_event);
    }

    @Test
    void getEvent_fails() {
        Mockito.when(eventMapper.getTodoItem(anyLong())).thenReturn(Optional.empty());

        EventNotFoundException exception = Assertions.assertThrows(EventNotFoundException.class
                , () -> eventService.getEvent(99L));

        Mockito.verify(eventMapper, Mockito.times(1)).getTodoItem(anyLong());
        assertThat(exception.getMessage()).isEqualTo("event not found:99");
    }

    @Test
    void getEventOfWholeDay() {
        List<Event> list = List.of(test_event, test_event, test_event);
        Mockito.when(eventMapper.getTodoItemListOfDay(any())).thenReturn(list);

        List<Event> result = eventService.getEventOfWholeDay("anyString()", "anyString()", "anyString()");

        Mockito.verify(eventMapper, Mockito.times(1)).getTodoItemListOfDay(any());
        Assertions.assertEquals(result.size(), 3);
    }

    @Test
    void getEventOfWholeMonth() {
        List<Event> list = List.of(test_event, test_event, test_event);
        Mockito.when(eventMapper.getTodoItemListOfMonth(anyString())).thenReturn(list);

        List<Event> result = eventService.getEventOfWholeMonth("anyString()", "anyString()");

        Mockito.verify(eventMapper, Mockito.times(1)).getTodoItemListOfMonth(anyString());
        Assertions.assertEquals(result.size(), 3);
    }

    @Test
    void countEventsOfDay() {
        Mockito.when(eventMapper.countByTodoDate(anyString())).thenReturn(7);

        int result = eventService.countEventsOfDay("anyString()");

        Mockito.verify(eventMapper, Mockito.times(1)).countByTodoDate(any());
        Assertions.assertEquals(result, 7);
    }
}