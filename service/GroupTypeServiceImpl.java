package com.technojade.allybot.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.technojade.allybot.entity.GroupType;
import com.technojade.allybot.repository.GroupTypeRepository;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class GroupTypeServiceImpl implements GroupTypeService{
	
	@Autowired
	private GroupTypeRepository groupTypeRepository;
	
	@Override
	public List<GroupType> listOfGroupType() {
		return groupTypeRepository.findAll();
	}

	@Override
	public GroupType groupTypeById(Long id) {
		return groupTypeRepository.findById(id).get();
	}

}
