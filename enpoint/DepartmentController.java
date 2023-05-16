package com.technojade.allybot.enpoint;

import java.sql.Timestamp;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.technojade.allybot.enpoint.response.AllyBotResponseDto;
import com.technojade.allybot.enpoint.response.ServiceStatus;
import com.technojade.allybot.entity.Department;
import com.technojade.allybot.service.DepartmentService;

import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/department")
@Slf4j
public class DepartmentController {

	
	@Autowired
	private DepartmentService departmentService;
	
	@GetMapping
	public ResponseEntity<?> listOfDepartment(){
		
		List<Department> departmentListResponse = departmentService.departmentList();
		AllyBotResponseDto<List<Department>> response = new AllyBotResponseDto<>();
		response.setData(departmentListResponse);
		response.setServiceStatus(ServiceStatus.SUCCESS);
		response.setTimestamp(new Timestamp(System.currentTimeMillis()));
		response.setTraceId(Long.MAX_VALUE);
		response.setMsg("Department information retireved");
		return ResponseEntity.status(HttpStatus.OK).body(response);
		
	}
	
	
	
	
}
