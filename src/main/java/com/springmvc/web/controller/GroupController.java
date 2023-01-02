package com.springmvc.web.controller;

import com.springmvc.persistence.entity.Group;
import com.springmvc.persistence.entity.User;
import com.springmvc.security.SecurityUtil;
import com.springmvc.service.GroupService;
import com.springmvc.service.UserService;
import com.springmvc.web.dto.GroupDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;


@Controller
@RequiredArgsConstructor
public class GroupController {

    private final GroupService groupService;
    private final UserService userService;


    /**
     * LIST ACTIVITY GROUPS
     *
     * @param model - allows us to put data/objects on webpage
     *              - model representation of user and groups
     * @return      - page with list of groups
     */
    @GetMapping("/groups")
    public String listGroups(Model model) {
        //using sessions to get current username (to allow user to edit/delete only his own groups)
        User user = new User();
        List<GroupDto> groups = groupService.findAllGroups();
        String username = SecurityUtil.getSessionUser();

        if (username != null) {
            user = userService.findByUsername(username);
            model.addAttribute("user", user);
        }

        //injecting user and groups data into our view
        model.addAttribute("user", user);
        model.addAttribute("groups", groups);
        return "groups-list";
    }

    /**
     * SEARCH GROUP
     *
     * @param query  - search parameters
     * @param model  - model of groups
     * @return       - page with list of groups (only with searched data in it)
     *
     * @RequestParam - enables spring to extract input data that may be passed as a query, form data, or any arbitrary custom data
     *               - parameter is optional
     *               = takes the query parameter in actual url
     */
    @GetMapping("/groups/search")
    public String searchGroup(@RequestParam(value = "query") String query, Model model) {
        List<GroupDto> groups = groupService.searchGroups(query);
        model.addAttribute("groups", groups);
        return "groups-list";
    }

    /**
     * GET DETAILS OF PARTICULAR GROUP
     *
     * @param groupId - id of the group
     * @param model   - model representation of the group object (details) and user
     * @return        - page with group details
     */
    @GetMapping("/groups/{groupId}")
    public String groupDetail(@PathVariable("groupId") Long groupId, Model model) {
        User user = new User();
        GroupDto groupDto = groupService.findGroupById(groupId);
        String username = SecurityUtil.getSessionUser();

        if(username != null) {
            user = userService.findByUsername(username);
            model.addAttribute("user", user);
        }

        model.addAttribute("user", user);
        model.addAttribute("group", groupDto);
        return "groups-detail";
    }

    /**
     * CREATE NEW GROUP (FORM)
     *
     * @param model - model of group object (empty)
     * @return      - page with group creation form
     */
    @GetMapping("/groups/new")
    public String createGroupForm(Model model) {
        Group group = new Group();
        model.addAttribute("group", group);
        return "groups-create";
    }

    /**
     * SUBMIT CREATE GROUP FORM
     *
     * @param groupDto - data transfer object of group
     * @param result   - result of validation for passed values
     * @param model    - model of group object (filed with data)
     * @return         - redirects to page with groups
     *
     * @Valid          - tell spring to go and validate the data passed into the controller
     *                 = triggers validation annotations to start working
     *                 - BindingResult actually do the value validation and in case of error
     *                   it returns "result.hasErrors()" and we can redirect user back to html page with notification
     *                   that there is an error (in particular form field)
     * @ModelAttribute - binds a method parameter or method return value to a named model attribute and then exposes
     *                   it to a web view
     *                 = it is going to check the model and associates with what we are passing up with dto
     */
    @PostMapping("/groups/new")
    public String saveGroup(@Valid @ModelAttribute("group") GroupDto groupDto, BindingResult result, Model model) {
        if (result.hasErrors()) {
            model.addAttribute("group", groupDto);
            return "groups-create";
        }

        groupService.saveGroup(groupDto);
        return "redirect:/groups";
    }

    /**
     * EDIT GROUP (FORM)
     *
     * @param groupId - group id to be modified
     * @param model   - model of group (existing)
     * @return        - page with group form for object edit
     */
    @GetMapping("/groups/{groupId}/edit")
    public String editGroupForm(@PathVariable("groupId") Long groupId, Model model) {
        GroupDto group = groupService.findGroupById(groupId);
        model.addAttribute("group", group);
        return "groups-edit";
    }

    /**
     * SUBMIT EDIT GROUP FORM
     *
     * @param groupId  - group id to be modified
     * @param groupDto - data transfer object of group
     * @param result   - result of validation for passed values
     * @param model    - model of group
     * @return         - page with groups / edit page in case of error
     */
    @PostMapping("/groups/{groupId}/edit")
    public String updateGroup(@PathVariable("groupId") Long groupId,
                             @Valid @ModelAttribute("group") GroupDto groupDto,
                             BindingResult result, Model model) {

        if (result.hasErrors()) {
            model.addAttribute("group", groupDto);
            return "groups-edit";
        }

        groupDto.setId(groupId);
        groupService.updateGroup(groupDto);
        return "redirect:/groups";
    }

    /**
     * DELETE GROUP
     *
     * @param groupId - id of group to be deleted
     * @return        - redirects to page with listed groups
     *
     * @PathVariable  - indicates that a method parameter should be bound to a URI template variable
     *                - parameter is mandatory
     */
    @GetMapping("/groups/{groupId}/delete")
    public String deleteGroup(@PathVariable("groupId") Long groupId) {
        groupService.delete(groupId);
        return "redirect:/groups";
    }

}
