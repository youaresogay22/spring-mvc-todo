package com.nhnacademy.todo.event.mapper;

import com.nhnacademy.todo.event.domain.Event;
import org.apache.ibatis.annotations.Mapper;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Mapper
public interface EventMapper {
    void save(Event event);

    void deleteTodoItem(long eventId);

    void deleteTodoItemOfDay(LocalDate todoDate);

    Optional<Event> getTodoItem(long eventId);

    List<Event> getTodoItemListOfDay(String date_YMD);

    List<Event> getTodoItemListOfMonth(String date_YM);

    int countByTodoDate(String date);

    Long getLastEventId();

}
