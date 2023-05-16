package com.technojade.allybot.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.technojade.allybot.dtos.EmployeeDto;
import com.technojade.allybot.dtos.PaginationDto;
import com.technojade.allybot.entity.Department;
import com.technojade.allybot.entity.Designation;
import com.technojade.allybot.entity.Employee;
import com.technojade.allybot.entity.GroupMst;
import com.technojade.allybot.entity.UserGroup;
import com.technojade.allybot.entity.UserRole;
import com.technojade.allybot.repository.EmployeeRepository;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class EmployeeServiceImpl implements EmployeeService {

	@Autowired
	private EmployeeRepository employeeRepository;

	@Autowired
	private DepartmentService departmentService;

	@Autowired
	private DesignationService designationService;

	@Autowired
	private UserRoleService userRoleService;
	
	@Autowired
	private GroupService groupService;

	@Override
	public Employee saveEmployee(Employee employee) {
		employeeRepository.save(employee);
		return employee;
	}

	@Override
	public List<Employee> findAll() {
		return employeeRepository.findAll(Sort.by(Sort.Direction.DESC, "empId"));
	}

	@Override
	public PaginationDto<?> findAllByClientIdAndHotelId(Long clientId, Long hotelId, Pageable paging) {

		List<Designation> designations = designationService.listOfDesignation();
//		List<Department> departments = departmentService.departmentList();

		Page<Employee> employee = employeeRepository.findAllByClientIdAndHotelIdOrderByEmpIdDesc(clientId, hotelId,
				paging);

		List<EmployeeDto> employees = new ArrayList<>();

		employee.stream().forEach(e -> {
			EmployeeDto employeedDto = new EmployeeDto();
			Optional<Designation> designationOption = designations.stream()
					.filter(d -> d.getDesignationId() == e.getDesignationId()).findAny();
//			Optional<Department> departmentOption = departments.stream()
//					.filter(d -> d.getDepId() == e.getDepartmentId()).findAny();

			GroupMst groupMst = groupService.getGroupDetailById(e.getDepartmentId());
			
			BeanUtils.copyProperties(e, employeedDto);
			if (designationOption.isPresent())
				employeedDto.setDesignationName(designationOption.get().getName());

			if (groupMst != null)
				employeedDto.setDepartmentName(groupMst.getName());

			if (employeedDto.getRoleId() != 0 && employeedDto.getRoleId() != null) {
				UserRole roleById = userRoleService.userRoleById(employeedDto.getRoleId());
				if (roleById != null) {
					employeedDto.setRole(roleById.getName());
				}
			}

			employees.add(employeedDto);
		});

		PaginationDto<Object> paginationDto = new PaginationDto<>();
		paginationDto.setData(employees);
		paginationDto.setTotalElement(employee.getTotalElements());
		paginationDto.setTotalPage((long) employee.getTotalPages());

		return paginationDto;
	}
	
	@Override
	public List<EmployeeDto> findAllByClientIdAndHotelId(Long clientId, Long hotelId) {

		List<Designation> designations = designationService.listOfDesignation();
//		List<Department> departments = departmentService.departmentList();

		List<Employee> employee = employeeRepository.findAllByClientIdAndHotelIdOrderByEmpIdDesc(clientId, hotelId);
		List<EmployeeDto> employees = new ArrayList<>();
		employee.stream().forEach(e -> {
			EmployeeDto employeedDto = new EmployeeDto();
			Optional<Designation> designationOption = designations.stream()
					.filter(d -> d.getDesignationId() == e.getDesignationId()).findAny();
//			Optional<Department> departmentOption = departments.stream()
//					.filter(d -> d.getDepId() == e.getDepartmentId()).findAny();
			
			GroupMst groupMst = groupService.getGroupDetailById(e.getDepartmentId());
			
			BeanUtils.copyProperties(e, employeedDto);
			if (designationOption.isPresent())
				employeedDto.setDesignationName(designationOption.get().getName());

			if (groupMst != null)
				employeedDto.setDepartmentName(groupMst.getName());
			
			if (employeedDto.getRoleId() != 0 && employeedDto.getRoleId() != null) {
				UserRole roleById = userRoleService.userRoleById(employeedDto.getRoleId());
				if (roleById != null) {
					employeedDto.setRole(roleById.getName());
				}
			}
			
			employees.add(employeedDto);
		});
		return employees;
	}

	@Override
	public Optional<Employee> findEmployeeById(Long id) {
		return employeeRepository.findById(id);
	}

	@Override
	public Employee updateEmployee(Employee employee) {
		return employeeRepository.save(employee);
	}

	@Override
	public Employee login(Employee employee) {
		Employee empEntity = employeeRepository.findByEmailAndPassword(employee.getEmail(), employee.getPassword());

		if (null == empEntity) {
			return null;
		}
		return empEntity;
	}

	@Override
	public Employee isEmailOrUsernameExist(Employee employee) {
		String usernameParam = (null != employee.getUsername() ? employee.getUsername() : "");
		String emailId = (null != employee.getEmail() ? employee.getEmail() : "");
		String mobileNumber = null != employee.getContactNo() ? employee.getContactNo() : "";
		return employeeRepository.findByEmailOrUsernameOrContactNo(emailId, usernameParam, mobileNumber);
	}

	@Override
	public Employee loginByMobileNumber(String mobileNumber) {
		return employeeRepository.findByContactNoAndIsActive(mobileNumber, true);
	}

	@Override
	public Employee findByMobileNumberOrEmailId(Employee employee) {
		String emailId = (null != employee.getEmail() ? employee.getEmail() : "");
		String mobileNumber = null != employee.getContactNo() ? employee.getContactNo() : "";
		return employeeRepository.findByContactNoOrEmailAndIsActive(mobileNumber, emailId, true);
	}

	@Transactional
	@Override
	public Employee deleteEmployeeById(long id) {
		Optional<Employee> employeeById = findEmployeeById(id);
		if (employeeById.isPresent()) {
			employeeRepository.deleteById(id);
			return employeeById.get();
		}
		return null;
	}

}
