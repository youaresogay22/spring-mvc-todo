package com.nhnacademy.todo.service;

import com.nhnacademy.todo.dto.DailyRegisterCountResponseDto;
import com.nhnacademy.todo.dto.EventCreatedResponseDto;
import com.nhnacademy.todo.dto.EventDto;
import com.nhnacademy.todo.exception.InvalidEventOwnerException;
import java.time.LocalDate;
import java.util.List;

public interface EventService {
    //등록
    EventCreatedResponseDto insert(String userId, EventDto eventDto);

    //수정
    long update(String userId, long eventId, EventDto eventDto);

    //삭제
    void deleteOne(String userId, long eventId);

    //조회
    EventDto getEvent(String userId, long eventId);

    //월단위 조회
    List<EventDto> getEventListByMonth(String userId, Integer year, Integer month);

    //일단위 조회
    List<EventDto> getEventListByday(String userId, Integer year, Integer month, Integer day);

    //일 단위 등록 카윤트
    DailyRegisterCountResponseDto getDayliyRegisterCount(String userId, LocalDate targetDate);

    //삭제
    void deleteEventByDaily(String userId, LocalDate eventAt);

    //event 소유자 체크
    default boolean checkOwner(String userId, String dbUserId){
        if(!userId.equals(dbUserId)){
            throw new InvalidEventOwnerException();
        }
        return true;
    }
}
