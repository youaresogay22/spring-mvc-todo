package com.nhnacademy.todo.event.mapper;

import com.nhnacademy.todo.config.RootConfig;
import com.nhnacademy.todo.config.WebConfig;
import com.nhnacademy.todo.event.domain.Event;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.ContextHierarchy;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;

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

    LocalDateTime now;
    Event test_event;
    String dateString_0201, dateString_0202;

    @BeforeEach
    void setUp() {
        now = LocalDateTime.now().truncatedTo(ChronoUnit.HOURS);
        dateString_0201 = LocalDate.of(2024, 2, 1).format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        dateString_0202 = LocalDate.of(2024, 2, 2).format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));

        //2월 1일=1개
        test_event = new Event(
                "jsj",
                "todo test",
                LocalDate.of(2024, 2, 1)
        );
        test_event.setId(99);

        //2월2일=2개
        Event test_event2 = new Event(
                "jsj",
                "todo test2",
                LocalDate.of(2024, 2, 2)
        );
        test_event2.setId(100);

        Event test_event3 = new Event(
                "jsj",
                "todo test3",
                LocalDate.of(2024, 2, 2)
        );
        test_event3.setId(101);

        eventMapper.save(test_event);
        eventMapper.save(test_event2);
        eventMapper.save(test_event3);
    }

    @Test
    @DisplayName("todo-단일_조회-by id")
    @Order(1)
    void getTodoItem() {
        Event event_Saved = eventMapper.getTodoItem(99).orElse(null);

        Assertions.assertAll(
                () -> Assertions.assertEquals(99, event_Saved.getId()),
                () -> Assertions.assertEquals("jsj", event_Saved.getUserId()),
                () -> Assertions.assertEquals("todo test", event_Saved.getSubject()),
                () -> Assertions.assertEquals(LocalDate.of(2024, 2, 1), event_Saved.getEventAt()),
                () -> Assertions.assertEquals(now, event_Saved.getCreatedAt().truncatedTo(ChronoUnit.HOURS))
        );
    }

    @Test
    @DisplayName("todo-일전체_갯수조회")
    @Order(2)
    void countByTodoDate() {
        Assertions.assertAll(
                () -> Assertions.assertEquals(eventMapper.countByTodoDate(dateString_0201), 1),
                () -> Assertions.assertEquals(eventMapper.countByTodoDate(dateString_0202), 2)
        );
    }

    @Test
    @DisplayName("todo-일전체_조회")
    @Order(3)
    void getTodoItemListOfDay() {
        Assertions.assertAll(
                () -> Assertions.assertEquals(eventMapper.getTodoItemListOfDay(dateString_0201).size(), 1),
                () -> Assertions.assertEquals(eventMapper.getTodoItemListOfDay(dateString_0202).size(), 2));
    }

    @Test
    @DisplayName("todo-월전체_조회")
    @Order(4)
    void getTodoItemListOfMonth() {
        String dateString = LocalDate.of(2024, 2, 1).format(DateTimeFormatter.ofPattern("yyyy-MM"));
        Assertions.assertAll(
                () -> Assertions.assertEquals(eventMapper.getTodoItemListOfMonth(dateString).size(), 3));

    }

    @Test
    @DisplayName("todo-저장")
    @Order(5)
    void save() {
        LocalDateTime now2 = LocalDateTime.now().truncatedTo(ChronoUnit.HOURS);

        Event event = new Event(
                "jsj",
                "todo save test",
                LocalDate.of(2024, 2, 3)
        );
        event.setId(777);
        eventMapper.save(event);

        Event event_Saved = eventMapper.getTodoItem(777).orElse(null);
        Assertions.assertAll(
                () -> Assertions.assertEquals(777, event_Saved.getId()),
                () -> Assertions.assertEquals("jsj", event_Saved.getUserId()),
                () -> Assertions.assertEquals("todo save test", event_Saved.getSubject()),
                () -> Assertions.assertEquals(LocalDate.of(2024, 2, 3), event_Saved.getEventAt()),
                () -> Assertions.assertEquals(now2, event_Saved.getCreatedAt().truncatedTo(ChronoUnit.HOURS))
        );
    }

    @Test
    @DisplayName("todo-단일_삭제-by id")
    @Order(6)
    void deleteTodoItem() {
        eventMapper.deleteTodoItem(99);
        Assertions.assertAll(
                () -> Assertions.assertFalse(eventMapper.getTodoItem(99).isPresent()));
    }

    @Test
    @DisplayName("todo-일전체_삭제")
    @Order(7)
    void deleteTodoItemOfDay() {
        eventMapper.deleteTodoItemOfDay(LocalDate.of(2024, 2, 2));

        String dateString = LocalDate.of(2024, 2, 2).format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        Assertions.assertAll(
                () -> Assertions.assertEquals(eventMapper.countByTodoDate(dateString), 0));
    }

    @Test
    @DisplayName("todo-최고_todoID_조회")
    void getLastEventId() {
        Assertions.assertAll(
                () -> Assertions.assertEquals(eventMapper.getLastEventId(), 101));
    }
}