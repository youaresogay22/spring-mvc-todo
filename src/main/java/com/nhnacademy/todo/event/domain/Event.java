package com.nhnacademy.todo.event.domain;

import lombok.Getter;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.concurrent.atomic.AtomicLong;

@Getter
public class Event {
    private static AtomicLong ID_GENERATOR;
    //아이디
    private long id;

    //userId
    private String userId;

    //제목
    private String subject;

    //event 일자
    private LocalDate eventAt;

    //event 등록 datetime
    private LocalDateTime createdAt;

    public Event(String userId, String subject, LocalDate eventAt) {
        this.userId = userId;
        this.subject = subject;
        this.eventAt = eventAt;
        this.createdAt = LocalDateTime.now();
    }

    public void setId() {
        this.id = ID_GENERATOR.incrementAndGet();
    }

    public void update(String subject) {
        this.subject = subject;
    }
}
