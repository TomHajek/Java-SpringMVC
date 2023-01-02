package com.springmvc.service;

import com.springmvc.persistence.entity.Group;

import java.util.List;

import com.springmvc.web.dto.GroupDto;


public interface GroupService {

    List<GroupDto> findAllGroups();
    Group saveGroup(GroupDto groupDto);
    GroupDto findGroupById(Long groupId);
    void updateGroup(GroupDto groupDto);
    void delete(Long groupId);
    List<GroupDto> searchGroups(String query);

}
