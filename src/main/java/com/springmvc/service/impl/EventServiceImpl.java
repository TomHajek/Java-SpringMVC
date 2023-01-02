package com.springmvc.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;
import com.springmvc.mapper.EventMapper;
import com.springmvc.persistence.entity.Event;
import com.springmvc.persistence.entity.Group;
import com.springmvc.persistence.repository.EventRepository;
import com.springmvc.persistence.repository.GroupRepository;
import com.springmvc.service.EventService;
import com.springmvc.web.dto.EventDto;

import static com.springmvc.mapper.EventMapper.mapToEvent;
import static com.springmvc.mapper.EventMapper.mapToEventDto;


@Service
@Transactional
@RequiredArgsConstructor
public class EventServiceImpl implements EventService {

    private final EventRepository eventRepository;
    private final GroupRepository groupRepository;


    @Override
    public void createEvent(Long groupId, EventDto eventDto) {
        Group group = groupRepository.findById(groupId).get();
        Event event = mapToEvent(eventDto);
        event.setGroup(group);
        eventRepository.save(event);
    }

    @Override
    public List<EventDto> findAllEvents() {
        List<Event> events = eventRepository.findAll();
        return events
                .stream()
                .map(EventMapper::mapToEventDto)
                .collect(Collectors.toList());
    }

    @Override
    public EventDto findByEventId(Long eventId) {
        Event event = eventRepository.findById(eventId).get();
        return mapToEventDto(event);
    }

    @Override
    public void updateEvent(EventDto eventDto) {
        Event event = mapToEvent(eventDto);
        eventRepository.save(event);
    }

    @Override
    public void deleteEvent(long eventId) {
        eventRepository.deleteById(eventId);
    }
}
