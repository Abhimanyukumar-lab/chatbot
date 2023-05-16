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
import com.technojade.allybot.entity.Designation;
import com.technojade.allybot.service.DesignationService;

@RestController
@RequestMapping("/designation")
public class DesignationController {

	
	@Autowired
	private DesignationService desinDesignationService;
	
	@GetMapping
	public ResponseEntity<?> listOfDesignation(){
		List<Designation> listOfDesignationResponse = desinDesignationService.listOfDesignation();
		
		AllyBotResponseDto<List<Designation>> response = new AllyBotResponseDto<>();
		response.setData(listOfDesignationResponse);
		response.setServiceStatus(ServiceStatus.SUCCESS);
		response.setTimestamp(new Timestamp(System.currentTimeMillis()));
		response.setTraceId(Long.MAX_VALUE);
		response.setMsg("Designation information retireved");
		return ResponseEntity.status(HttpStatus.OK).body(response);
		
	}
	
	
}
