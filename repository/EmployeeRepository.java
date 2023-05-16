package com.technojade.allybot.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.technojade.allybot.entity.Employee;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Long> {

	public Page<Employee> findAllByClientIdAndHotelIdOrderByEmpIdDesc(Long clientId, Long hotelId, Pageable page);

	public List<Employee> findAllByClientIdAndHotelIdOrderByEmpIdDesc(Long clientId, Long hotelId);
	
	public Employee findByEmailAndPassword(String emailId, String password);

	public Employee findByEmailOrUsernameOrContactNo(String emailId, String username, String contactNo);

	public Employee findByContactNoAndIsActive(String mobileNumber, boolean isActive);

	public Employee findByContactNoOrEmailAndIsActive(String mobileNumber, String emailId, boolean isActive);

}
