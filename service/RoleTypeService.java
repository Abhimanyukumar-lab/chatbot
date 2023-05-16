package com.technojade.allybot.service;

import java.util.List;

import com.technojade.allybot.entity.RoleTypeMst;

public interface RoleTypeService {

	public List<RoleTypeMst> listOfRoleType();
	public RoleTypeMst findByIdRoleType(Long id);
	
}
