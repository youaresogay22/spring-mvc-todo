package com.nhnacademy.todo.event.mapper;

import com.nhnacademy.todo.event.domain.Event;
import org.apache.ibatis.annotations.Mapper;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Mapper
public interface EventMapper {
    void save(Event event);

    void deleteTodoItem(long eventId);

    void deleteTodoItemOfDay(LocalDate todoDate);

    Optional<Event> getTodoItem(long eventId);

    List<Optional<Event>> getTodoItemListOfDay(int year, int month, int day);

    List<Optional<Event>> getTodoItemListOfMonth(int year, int month);

    int countByTodoDate(String year, String month, String day);

}
