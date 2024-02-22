package com.nhnacademy.todo.controller;

import com.nhnacademy.todo.event.domain.Event;
import com.nhnacademy.todo.event.service.EventService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/calendar")
@Slf4j
public class EventController {
    private final EventService eventService;

    public EventController(EventService eventService) {
        this.eventService = eventService;
    }

    @PostMapping("/events")
    @ResponseStatus(HttpStatus.CREATED)
    public long saveEvent(@RequestHeader("X-USER-ID") String userID,
                          @RequestParam("subject") String subject,
                          @RequestParam("eventAt") LocalDate eventAt) {
        Event event = new Event(userID, subject, eventAt);
        event.setId();

        return eventService.saveEvent(event);
    }

    @GetMapping("/events/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Event getEventByEventId(@PathVariable("id") long eventId) {

        return eventService.getEvent(eventId);
    }

    @GetMapping("/events/?year={year}&month={month}&day={day}")
    @ResponseStatus(HttpStatus.OK)
    public List<Optional<Event>> getEventByYearMonthDay(@PathVariable("year") int year,
                                                        @PathVariable("month") int month,
                                                        @PathVariable("day") int day) {
        log.debug("{}", year);
        return eventService.getEventOfWholeDay(year, month, day);
    }

    @GetMapping("/events/?year={year}&month={month}")
    @ResponseStatus(HttpStatus.OK)
    public List<Optional<Event>> getEventByYearMonth(@PathVariable("year") int year,
                                                     @PathVariable("month") int month) {

        return eventService.getEventOfWholeMonth(year, month);
    }

    @DeleteMapping("/events/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteEvent(@PathVariable("id") long eventId) {
        eventService.deleteEvent(eventId);
    }

    @DeleteMapping("/events/daily/{date}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteEventOfWholeDay(@PathVariable("date") String date) {
        LocalDate localDate = LocalDate.parse(date, DateTimeFormatter.ISO_DATE);
        eventService.deleteEventOfWholeDay(localDate);
    }

    @GetMapping("/daily-register-count?date={date}")
    @ResponseStatus(HttpStatus.OK)
    public int countEventOfWholeDay(@PathVariable("date") String date) {
        return eventService.countEventsOfDay(date);
    }
}
