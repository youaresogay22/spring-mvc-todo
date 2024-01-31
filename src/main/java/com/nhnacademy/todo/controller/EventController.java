package com.nhnacademy.todo.controller;

import com.nhnacademy.todo.dto.EventCreatedResponseDto;
import com.nhnacademy.todo.dto.EventDto;
import com.nhnacademy.todo.service.EventService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Objects;

@AllArgsConstructor
@RestController
@RequestMapping("/api/calendar/events")
public class EventController {

    private final EventService eventService;

    @ResponseStatus(value = HttpStatus.CREATED)
    @PostMapping(value = {"/" ,""})
    public EventCreatedResponseDto createEvent(@RequestHeader(name = "X-USER-ID", required = true)String userId, @RequestBody EventDto eventDto ){
        return eventService.insert(userId,eventDto);
    }

    @ResponseStatus(value = HttpStatus.OK)
    @PutMapping("/{event-id}")
    public long updateEvent(@RequestHeader(name = "X-USER-ID", required = true)String userId, @PathVariable("event-id") long eventId, @RequestBody EventDto eventDto ){
        return eventService.update(userId,eventId,eventDto);
    }

    @ResponseStatus(value=HttpStatus.NO_CONTENT)
    @DeleteMapping("/{event-id}")
    public void deleteEvent(@RequestHeader(name = "X-USER-ID", required = true)String userId, @PathVariable("event-id") long eventId ){
        eventService.deleteOne(userId,eventId);
    }

    @ResponseStatus(value=HttpStatus.NO_CONTENT)
    @DeleteMapping("/daily/{eventAt}")
    public void deleteEventByDaily(@RequestHeader(name = "X-USER-ID", required = true)String userId, @PathVariable("eventAt")String eventAt){
        eventService.deleteEventByDaily(userId, LocalDate.parse(eventAt, DateTimeFormatter.ISO_DATE));
    }

    //envet 조회
    @ResponseStatus(value = HttpStatus.OK)
    @GetMapping("/{event-id}")
    public EventDto getEvent(@RequestHeader(name = "X-USER-ID", required = true)String userId, @PathVariable("event-id") long eventId ){
        return eventService.getEvent(userId,eventId);
    }

    @ResponseStatus(value = HttpStatus.OK)
    @GetMapping(value = {"/",""})
    public List<EventDto> getEventList(@RequestHeader(name = "X-USER-ID", required = true)String userId,
                                       @RequestParam(name = "year", required = true) Integer year,
                                       @RequestParam(name = "month", required = true) Integer month,
                                       @RequestParam(name="day", required = false) Integer day
    ){

        List<EventDto> eventDtos = null;
        if(Objects.isNull(day)){
            eventDtos = eventService.getEventListByMonth(userId,year,month);
        }else{
            eventDtos = eventService.getEventListByday(userId, year, month, day);
        }
        return eventDtos;

    }



}
