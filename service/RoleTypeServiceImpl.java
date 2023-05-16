package com.technojade.allybot.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.technojade.allybot.entity.RoleTypeMst;
import com.technojade.allybot.repository.RoleTypeRepository;

@Service
public class RoleTypeServiceImpl implements RoleTypeService {

	@Autowired
	private RoleTypeRepository roleTypeRepo;
	
	@Override
	public List<RoleTypeMst> listOfRoleType() {
		// TODO Auto-generated method stub
		return roleTypeRepo.findAll();
	}

	@Override
	public RoleTypeMst findByIdRoleType(Long id) {
		// TODO Auto-generated method stub
		return roleTypeRepo.findById(id).get();
	}

}
