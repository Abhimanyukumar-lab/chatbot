package com.technojade.allybot.service;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.technojade.allybot.dtos.EmployeeDto;
import com.technojade.allybot.dtos.PaginationDto;
import com.technojade.allybot.entity.Employee;
import com.technojade.allybot.entity.User;

public interface EmployeeService {

	public Employee saveEmployee(Employee employee);

	public List<Employee> findAll();

	public PaginationDto<?> findAllByClientIdAndHotelId(Long clientId, Long hotelId, Pageable page);
	
	public List<EmployeeDto> findAllByClientIdAndHotelId(Long clientId, Long hotelId);

	public Optional<Employee> findEmployeeById(Long id);

	public Employee updateEmployee(Employee employee);

	public Employee login(Employee employee);

	public Employee isEmailOrUsernameExist(Employee employee);

	public Employee loginByMobileNumber(String mobileNumber);

	public Employee findByMobileNumberOrEmailId(Employee employee);

	public Employee deleteEmployeeById(long id);

}
