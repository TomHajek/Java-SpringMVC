package com.springmvc.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;
import com.springmvc.mapper.GroupMapper;
import com.springmvc.persistence.entity.Group;
import com.springmvc.persistence.entity.User;
import com.springmvc.persistence.repository.GroupRepository;
import com.springmvc.persistence.repository.UserRepository;
import com.springmvc.security.SecurityUtil;
import com.springmvc.service.GroupService;
import com.springmvc.web.dto.GroupDto;

import static com.springmvc.mapper.GroupMapper.*;


@Service
@Transactional
@RequiredArgsConstructor
public class GroupServiceImpl implements GroupService {

    private final GroupRepository groupRepository;
    private final UserRepository userRepository;


    @Override
    public List<GroupDto> findAllGroups() {
        List<Group> groups = groupRepository.findAll();
        return groups
                .stream()
                .map((group) -> mapToGroupDto(group))
                .collect(Collectors.toList());
    }

    @Override
    public Group saveGroup(GroupDto groupDto) {
        //using the sessions to tie group to particular user
        String username = SecurityUtil.getSessionUser();
        User user = userRepository.findByUsername(username);
        Group group = mapToGroup(groupDto);
        group.setCreatedBy(user);
        return groupRepository.save(group);
    }

    @Override
    public GroupDto findGroupById(Long groupId) {
        Group group = groupRepository.findById(groupId).get();
        return mapToGroupDto(group);
    }

    @Override
    public void updateGroup(GroupDto groupDto) {
        String username = SecurityUtil.getSessionUser();
        User user = userRepository.findByUsername(username);
        Group group = mapToGroup(groupDto);
        group.setCreatedBy(user);
        groupRepository.save(group);
    }

    @Override
    public void delete(Long groupId) {
        groupRepository.deleteById(groupId);
    }

    @Override
    public List<GroupDto> searchGroups(String query) {
        List<Group> groups = groupRepository.searchGroups(query);
        return groups
                .stream()
                .map(GroupMapper::mapToGroupDto)
                .collect(Collectors.toList());
    }

}
