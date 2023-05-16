package com.technojade.allybot.service;

import java.util.List;

import com.technojade.allybot.entity.Department;

public interface DepartmentService {

	
	public List<Department> departmentList();
	
	public Department findById(Long departmentId);
	
	
}
