package com.technojade.allybot.service;

import java.util.List;

import com.technojade.allybot.dtos.GroupUserDto;
import com.technojade.allybot.dtos.UserGroupDto;
import com.technojade.allybot.entity.UserGroup;

public interface UserGroupService {

	public UserGroup saveUserGroup(UserGroup userGroup);
	public UserGroup updateUserGroup(UserGroup userGroup);
	public UserGroup getUserGroupById(Long id);
	public List<GroupUserDto> getUserGroupByGroupId(Long id);
	public UserGroupDto getAllUserGroupByGroupId(Long id);
	public List<GroupUserDto> getUserGroupByHotelIdAndClientId(Long hId, Long cId);
	public void deleteUserGroupByGroupId(Long gId);
	public UserGroup deleteUserGroupByGrpIdAndEMpId(Long gId, Long eId);
	public UserGroup findByGroupIdAndEmployeeId(Long gId, Long eId);
}
