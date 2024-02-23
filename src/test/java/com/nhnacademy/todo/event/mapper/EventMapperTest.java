package com.nhnacademy.todo.event.mapper;

import com.nhnacademy.todo.config.RootConfig;
import com.nhnacademy.todo.config.WebConfig;
import com.nhnacademy.todo.event.domain.Event;
import com.nhnacademy.todo.student.domain.Student;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.ContextHierarchy;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@WebAppConfiguration
@ContextHierarchy({
        @ContextConfiguration(classes = {RootConfig.class}),
        @ContextConfiguration(classes = {WebConfig.class})
})
@Transactional // 테스트 코드에 붙으면 테스트 실행 후 커밋되기전 전부 롤백 처리
class EventMapperTest {

    @Autowired
    EventMapper eventMapper;

    static LocalDateTime now;
    static Event event;

    @BeforeAll
    static void setUP() {
        now = LocalDateTime.now().truncatedTo(ChronoUnit.SECONDS);

        event = new Event(99,
                "jsj",
                "todo test",
                LocalDate.of(2024, 2, 1),
                now);
    }

    @Test
    @DisplayName("todo-저장")
    void save() {
        eventMapper.save(event);

        Event event_Saved = eventMapper.getTodoItem(99).orElse(null);
        Assertions.assertAll(
                () -> Assertions.assertEquals(99, event_Saved.getId()),
                () -> Assertions.assertEquals("jsj", event_Saved.getUserId()),
                () -> Assertions.assertEquals("todo test", event_Saved.getSubject()),
                () -> Assertions.assertEquals(LocalDate.of(2024, 2, 1), event_Saved.getEventAt()),
                () -> Assertions.assertEquals(now, event_Saved.getCreatedAt())
        );
    }

    @Test
    void deleteTodoItem() {
    }

    @Test
    void deleteTodoItemOfDay() {
    }

    @Test
    @DisplayName("todo-단일_조회-by id")
    void getTodoItem() {
        Event event_Saved = eventMapper.getTodoItem(99).orElse(null);

        Assertions.assertAll(
                () -> Assertions.assertEquals(99, event_Saved.getId()),
                () -> Assertions.assertEquals("jsj", event_Saved.getUserId()),
                () -> Assertions.assertEquals("todo test", event_Saved.getSubject()),
                () -> Assertions.assertEquals(LocalDate.of(2024, 2, 1), event_Saved.getEventAt()),
                () -> Assertions.assertEquals(now, event_Saved.getCreatedAt())
        );
    }

    @Test
    @DisplayName("todo-일전체_조회")
    void getTodoItemListOfDay() {
    }

    @Test
    @DisplayName("todo-월전체_조회")
    void getTodoItemListOfMonth() {
    }

    @Test
    @DisplayName("todo-일전체_갯수조회")
    void countByTodoDate() {
    }

    @Test
    @DisplayName("todo-최고_todoID_조회")
    void getLastEventId() {
    }
}