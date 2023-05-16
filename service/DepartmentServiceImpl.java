package com.technojade.allybot.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.technojade.allybot.entity.Department;
import com.technojade.allybot.repository.DepartmentRepository;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class DepartmentServiceImpl implements DepartmentService {

	@Autowired
	private DepartmentRepository departmentRepository;

	@Override
	public List<Department> departmentList() {
		return departmentRepository.findAll();
	}

	@Override
	public Department findById(Long departmentId) {
		Optional<Department> departmentOptional = departmentRepository.findById(departmentId);
		if(departmentOptional.isPresent())
			return departmentOptional.get();
		
		return null;
	}

}
