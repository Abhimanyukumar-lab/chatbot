package com.technojade.allybot.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.technojade.allybot.dtos.GroupUserDto;
import com.technojade.allybot.dtos.UserGroupDto;
import com.technojade.allybot.entity.GroupMst;
import com.technojade.allybot.entity.UserGroup;
import com.technojade.allybot.repository.UserGroupRepository;

@Service
public class UserGroupServiceImpl implements UserGroupService {

	@Autowired
	private UserGroupRepository userGroupRepository;

	@Autowired
	private GroupService grpService;

	@Override
	public UserGroup saveUserGroup(UserGroup userGroup) {
		userGroupRepository.save(userGroup);
		return userGroup;
	}

	@Override
	public UserGroup updateUserGroup(UserGroup userGroup) {
		userGroupRepository.save(userGroup);
		return userGroup;
	}

	@Override
	public List<GroupUserDto> getUserGroupByGroupId(Long id) {

		GroupMst groupDetailById = grpService.getGroupDetailById(id);
		Optional<GroupMst> ofNullable = Optional.ofNullable(groupDetailById);

		List<UserGroup> groupData = userGroupRepository.findAllByGroupId(id);
		ArrayList<GroupUserDto> groupDataList = new ArrayList<>();

		groupData.stream().forEach(e -> {
			GroupUserDto groupUserDto = new GroupUserDto();
			BeanUtils.copyProperties(e, groupUserDto);
			if (ofNullable.isPresent()) {
				groupUserDto.setGrpName(ofNullable.get().getName());
			}
			groupDataList.add(groupUserDto);
		});
		return groupDataList;
	}

	@Override
	public UserGroupDto getAllUserGroupByGroupId(Long id) {

		GroupMst groupDetailById = grpService.getGroupDetailById(id);
		Optional<GroupMst> ofNullable = Optional.ofNullable(groupDetailById);

		if (!ofNullable.isPresent()) {
			return null;
		}
		List<UserGroup> groupData = userGroupRepository.findAllByGroupId(id);
		List<Long> groupDataList = new ArrayList<>();

		UserGroupDto userGroupDto = new UserGroupDto();

		groupData.stream().forEach(e -> {
			BeanUtils.copyProperties(e, userGroupDto);
			groupDataList.add(e.getEmployeeId());
		});
		userGroupDto.setEmpIds(groupDataList);

		return userGroupDto;
	}

	@Override
	public List<GroupUserDto> getUserGroupByHotelIdAndClientId(Long hId, Long cId) {

		List<GroupMst> listOfGroupDetails = grpService.listOfGroupDetails();

		List<UserGroup> hotelIdAndClientId = userGroupRepository.findAllByHotelIdAndClientId(hId, cId);

		ArrayList<GroupUserDto> grpData = new ArrayList<>();

		hotelIdAndClientId.stream().forEach(e -> {
			GroupUserDto groupUserDto = new GroupUserDto();
			BeanUtils.copyProperties(e, groupUserDto);
			Optional<GroupMst> groupOption = listOfGroupDetails.stream().filter(d -> e.getUserGroupId() == d.getId())
					.findAny();

			if (groupOption.isPresent()) {
				groupUserDto.setGrpName(groupOption.get().getName());
			}
			grpData.add(groupUserDto);
		});

		return grpData;
	}

	@Transactional
	@Override
	public void deleteUserGroupByGroupId(Long gId) {
		userGroupRepository.deleteAllByGroupId(gId);
	}

	@Override
	public UserGroup getUserGroupById(Long id) {
		Optional<UserGroup> findById = userGroupRepository.findById(id);
		if (findById.isPresent()) {
			return findById.get();
		}
		return null;
	}

	@Override
	public UserGroup findByGroupIdAndEmployeeId(Long gId, Long eId) {
		Optional<UserGroup> findByGroupIdAndEmployeeId = userGroupRepository.findByGroupIdAndEmployeeId(gId, eId);
		if (findByGroupIdAndEmployeeId.isPresent()) {
			return findByGroupIdAndEmployeeId.get();
		}
		return null;
	}

	@Transactional
	@Override
	public UserGroup deleteUserGroupByGrpIdAndEMpId(Long gId, Long eId) {
		UserGroup findByGroupIdAndEmployeeId = findByGroupIdAndEmployeeId(gId, eId);
		if (findByGroupIdAndEmployeeId != null) {
			userGroupRepository.deleteByGroupIdAndEmployeeId(gId, eId);
			return findByGroupIdAndEmployeeId;
		}
		return null;
	}

}
