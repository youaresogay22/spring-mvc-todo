package com.nhnacademy.todo.event.service;

import com.nhnacademy.todo.event.domain.Event;
import com.nhnacademy.todo.event.mapper.EventMapper;
import com.nhnacademy.todo.exception.EventNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
@Slf4j
public class EventService {
    private final EventMapper eventMapper;

    public EventService(EventMapper eventMapper) {
        this.eventMapper = eventMapper;
    }

    public long saveEvent(Event event) {
        event.setId(getEventId());
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

    public List<Event> getEventOfWholeDay(String year, String month, String day) {
        String date = year + "-" + month + "-" + day;
        List<Event> list = eventMapper.getTodoItemListOfDay(date);
        log.debug("!!!{}!!!", list);
        return list;
    }

    public List<Event> getEventOfWholeMonth(String year, String month) {
        String date = year + "-" + month;
        return eventMapper.getTodoItemListOfMonth(date);
    }

    public int countEventsOfDay(String date) {
        return eventMapper.countByTodoDate(date);
    }

    private long getEventId() {
        Optional<Long> tempId = Optional.ofNullable(eventMapper.getLastEventId());
        long id = tempId.orElse(0L);
        return id + 1L;
    }

}
