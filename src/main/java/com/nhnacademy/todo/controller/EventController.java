package com.nhnacademy.todo.controller;

import com.nhnacademy.todo.event.domain.Event;
import com.nhnacademy.todo.event.service.EventService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
    public Map<String, Long> saveEvent(@RequestBody HashMap<String, String> request,
                                       @RequestHeader("X-USER-ID") String userID) {
        Map<String, Long> result = new HashMap<>();
// response DTO객체를 되는 만큼 생성할 것, 나쁜 것 아님
        LocalDate eventAt = LocalDate.parse(request.get("eventAt"));
        Event event = new Event(userID, request.get("subject"), eventAt);

        result.put("id", eventService.saveEvent(event));

        return result;
    }

    @GetMapping("/events/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Event getEventByEventId(@PathVariable("id") long eventId) {

        return eventService.getEvent(eventId);
    }

    //parameter "day" 존재하는 경우에만
    @GetMapping(value = "/events/", params = {"day"})
    @ResponseStatus(HttpStatus.OK)
    public List<Event> getEventByYearMonthDay(@RequestParam("year") String year,
                                              @RequestParam("month") String month,
                                              @RequestParam("day") String day) {

        return eventService.getEventOfWholeDay(year, month, day);
    }

    @GetMapping("/events/")
    @ResponseStatus(HttpStatus.OK)
    public List<Event> getEventByYearMonth(@RequestParam("year") String year,
                                           @RequestParam("month") String month) {

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

    @GetMapping("/daily-register-count")
    @ResponseStatus(HttpStatus.OK)
    public Map<String, Integer> countEventOfWholeDay(@RequestParam("date") String date) {
        Map<String, Integer> result = new HashMap<>();
        result.put("count", eventService.countEventsOfDay(date));
        return result;
    }
}
