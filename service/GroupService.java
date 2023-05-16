package com.technojade.allybot.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.technojade.allybot.entity.GroupMst;

public interface GroupService {

	public GroupMst saveGroupDetail(GroupMst groupMst);

	public List<GroupMst> listOfGroupDetails(Long hId);

	public List<GroupMst> listOfGroupDetails();

	public Page<GroupMst> listOfGroupDetails(Long hId, Pageable pageable);

	public GroupMst getGroupDetailById(Long id);

	public GroupMst updateGroupDetail(GroupMst groupMst);

	public GroupMst deleteGroupDetailById(Long id);
}
