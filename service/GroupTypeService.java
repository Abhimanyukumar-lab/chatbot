package com.technojade.allybot.service;

import java.util.List;

import com.technojade.allybot.entity.GroupType;

public interface GroupTypeService {

	public List<GroupType> listOfGroupType();
	public GroupType groupTypeById(Long id);
	
}
