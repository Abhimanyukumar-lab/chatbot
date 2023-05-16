package com.technojade.allybot.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.technojade.allybot.dtos.PaginationDto;
import com.technojade.allybot.entity.GroupMst;
import com.technojade.allybot.repository.GroupReository;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class GroupServiceImpl implements GroupService {

	@Autowired
	private GroupReository groupReository;

	@Autowired
	private UserGroupService userGroupService;

	@Override
	public GroupMst saveGroupDetail(GroupMst groupMst) {
		groupReository.save(groupMst);
		return groupMst;
	}

	@Override
	public List<GroupMst> listOfGroupDetails() {
		return groupReository.findAll(Sort.by(Sort.Direction.DESC, "id"));
	}

	@Override
	public List<GroupMst> listOfGroupDetails(Long hId) {
		return groupReository.findAllByHotelId(hId);
	}

	@Override
	public GroupMst getGroupDetailById(Long id) {
		Optional<GroupMst> findById = groupReository.findById(id);
		if (findById.isPresent()) {
			return findById.get();
		}
		return null;
	}

	@Override
	public GroupMst updateGroupDetail(GroupMst groupMst) {
		groupReository.save(groupMst);
		return groupMst;
	}

	@Transactional
	@Override
	public GroupMst deleteGroupDetailById(Long id) {
		GroupMst detailById = getGroupDetailById(id);
		if (detailById != null) {
			groupReository.deleteById(id);
			userGroupService.deleteUserGroupByGroupId(id);
			return detailById;
		}
		return null;
	}

	@Override
	public Page<GroupMst> listOfGroupDetails(Long hId, Pageable pageable) {
		Page<GroupMst> groupMst = groupReository.findAllByHotelId(hId, pageable);
		return groupMst;
	}

}
