package com.technojade.allybot.service;

import java.util.List;

import com.technojade.allybot.entity.Designation;

public interface DesignationService {

	public List<Designation> listOfDesignation();
	
	public Designation findById(Long designationId);
	
}
