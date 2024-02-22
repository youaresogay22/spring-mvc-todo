package com.nhnacademy.todo.event.service;

import com.nhnacademy.todo.event.domain.Event;
import com.nhnacademy.todo.event.mapper.EventMapper;
import com.nhnacademy.todo.exception.EventNotFoundException;
import com.nhnacademy.todo.exception.EventNotFoundOnDateException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class EventService {
    private final EventMapper eventMapper;

    public EventService(EventMapper eventMapper) {
        this.eventMapper = eventMapper;
    }

    public long saveEvent(Event event) {
        eventMapper.save(event);
        return event.getId();
    }

    public void deleteEvent(long id) {
        eventMapper.deleteTodoItem(id);
    }

    public void deleteEventOfWholeDay(LocalDate todoDate) {
        eventMapper.deleteTodoItemOfDay(todoDate);
    }

    public Event getEvent(long id) {

        Optional<Event> eventOptional = eventMapper.getTodoItem(id);

        if (eventOptional.isEmpty()) {
            throw new EventNotFoundException(id);
        }
        return eventOptional.get();
    }

    public List<Optional<Event>> getEventOfWholeDay(int year, int month, int day) {

        List<Optional<Event>> eventOptionalList = eventMapper.getTodoItemListOfDay(year, month, day);

        if (eventOptionalList.isEmpty()) {
            throw new EventNotFoundOnDateException(day);
        }
        return eventOptionalList;
    }

    public List<Optional<Event>> getEventOfWholeMonth(int year, int month) {

        List<Optional<Event>> eventOptionalList = eventMapper.getTodoItemListOfMonth(year, month);

        if (eventOptionalList.isEmpty()) {
            throw new EventNotFoundOnDateException(month);
        }
        return eventOptionalList;
    }

    public int countEventsOfDay(String day) {
        String[] parsedDay = day.split("-");
        return eventMapper.countByTodoDate(parsedDay[0], parsedDay[1], parsedDay[2]);
    }


}
