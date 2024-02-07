package com.nhnacademy.todo.repository.impl;

import com.nhnacademy.todo.domain.Event;
import com.nhnacademy.todo.repository.EventRepository;
import lombok.extern.slf4j.Slf4j;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Collectors;


@Slf4j
public class MemoryEventRepository implements EventRepository {



    private final ConcurrentMap<String, List<Event>> eventMap = new ConcurrentHashMap<>();
    private final AtomicLong autoIdx = new AtomicLong();

    @Override
    public Event save(Event event) {
        return null;
    }

    @Override
    public void update(Event event) {

    }

    @Override
    public void deleteById(String userId, long eventId) {

    }

    @Override
    public Event getEvent(String userId, long eventId) {
        return null;
    }

    @Override
    public List<Event> findAllByDay(String userId, int year, int month, int day) {
        return null;
    }

    @Override
    public List<Event> findAllByMonth(String userId, int year, int month) {
        return null;
    }

    @Override
    public long countByUserIdAndEventAt(String userId, LocalDate targetDate) {
        return 0;
    }

    @Override
    public void deletebyDaily(String userId, LocalDate targetDate) {

    }

    @Override
    public void init() {

    }
}
