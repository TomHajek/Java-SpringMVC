package com.springmvc.web.controller;

import com.springmvc.persistence.entity.Event;
import com.springmvc.persistence.entity.User;
import com.springmvc.security.SecurityUtil;
import com.springmvc.service.EventService;
import com.springmvc.service.GroupService;
import com.springmvc.service.UserService;
import com.springmvc.web.dto.EventDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;


@Controller
@RequiredArgsConstructor
public class EventController {

    private final EventService eventService;
    private final UserService userService;
    private final GroupService groupService;


    /**
     * LIST OF EVENTS
     *
     * @param model - model representation of user and events
     * @return      - page with list of events
     */
    @GetMapping("/events")
    public String eventList(Model model) {
        //using sessions to get current username (to allow user to edit/delete only his own events)
        User user = new User();
        List<EventDto> events = eventService.findAllEvents();
        String username = SecurityUtil.getSessionUser();

        if(username != null) {
            user = userService.findByUsername(username);
            model.addAttribute("user", user);
        }

        model.addAttribute("user", user);
        model.addAttribute("events", events);
        return "events-list";
    }

    /**
     * VIEW EVENT DETAILS
     *
     * @param eventId - event id to be viewed
     * @param model   - model representation of user, group and event
     * @return        - page with event details
     */
    @GetMapping("/events/{eventId}")
    public String viewEvent(@PathVariable("eventId") Long eventId, Model model) {
        User user = new User();
        EventDto eventDto = eventService.findByEventId(eventId);
        String username = SecurityUtil.getSessionUser();

        if(username != null) {
            user = userService.findByUsername(username);
            model.addAttribute("user", user);
        }

        model.addAttribute("group", eventDto.getGroup());
        model.addAttribute("user", user);
        model.addAttribute("event", eventDto);
        return "events-detail";
    }

    /**
     * CREATE NEW GROUP EVENT
     *
     * @param groupId - id of a group in which event will be created
     * @param model   - model of event with group id
     * @return        - page with create event form
     */
    @GetMapping("/events/{groupId}/new")
    public String createEventForm(@PathVariable("groupId") Long groupId, Model model) {
        Event event = new Event();
        model.addAttribute("groupId", groupId);
        model.addAttribute("event", event);
        return "events-create";
    }

    /**
     * SUBMIT CREATE EVENT FORM
     *
     * @param groupId  - id of a group in which event will be created
     * @param eventDto - data transfer object of event
     * @param result   -
     * @param model    - model of event
     * @return         - page of groups
     */
    @PostMapping("/events/{groupId}")
    public String createEvent(@PathVariable("groupId") Long groupId, @ModelAttribute("event") EventDto eventDto,
                              BindingResult result,
                              Model model) {

        if(result.hasErrors()) {
            model.addAttribute("event", eventDto);
            return "groups-create";
        }

        eventService.createEvent(groupId, eventDto);
        return "redirect:/groups/" + groupId;
    }

    /**
     * EDIT EVENT
     *
     * @param eventId - id of event to be edited
     * @param model   -
     * @return        -
     */
    @GetMapping("/events/{eventId}/edit")
    public String editEventForm(@PathVariable("eventId") Long eventId, Model model) {
        EventDto event = eventService.findByEventId(eventId);
        model.addAttribute("event", event);
        return "events-edit";
    }

    /**
     * SUBMIT EDIT EVENT
     *
     * @param eventId - id of event to be edited
     * @param event   - data tranfer object of event
     * @param result  -
     * @param model   - model of event
     * @return        - redirect to page with events
     */
    @PostMapping("/events/{eventId}/edit")
    public String updateEvent(@PathVariable("eventId") Long eventId,
                              @Valid @ModelAttribute("event") EventDto event,
                              BindingResult result, Model model) {

        if(result.hasErrors()) {
            model.addAttribute("event", event);
            return "events-edit";
        }

        // current event from db
        EventDto eventDto = eventService.findByEventId(eventId);
        // updating event
        event.setId(eventId);
        event.setGroup(eventDto.getGroup());
        eventService.updateEvent(event);
        return "redirect:/events";
    }

    /**
     * DELETE EVENT
     *
     * @param eventId - id of event to be deleted
     * @return        - redirect to page with events
     */
    @GetMapping("/events/{eventId}/delete")
    public String deleteEvent(@PathVariable("eventId") long eventId) {
        eventService.deleteEvent(eventId);
        return "redirect:/events";
    }

}
