package com.springmvc.service;

import com.springmvc.web.dto.EventDto;

import java.util.List;


public interface EventService {

    void createEvent(Long groupId, EventDto eventDto);
    List<EventDto> findAllEvents();
    EventDto findByEventId(Long eventId);
    void updateEvent(EventDto eventDto);
    void deleteEvent(long eventId);

}
