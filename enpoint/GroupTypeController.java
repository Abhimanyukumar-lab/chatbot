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
import com.technojade.allybot.entity.GroupType;
import com.technojade.allybot.service.GroupTypeService;

@RestController
@RequestMapping("/groupType")
public class GroupTypeController {

	@Autowired
	private GroupTypeService groupTypeService;

	@GetMapping
	public ResponseEntity<?> listOfGroupType() {
		List<GroupType> listOfGroupType = groupTypeService.listOfGroupType();
		AllyBotResponseDto<List<GroupType>> response = new AllyBotResponseDto<>();
		response.setData(listOfGroupType);
		response.setServiceStatus(ServiceStatus.SUCCESS);
		response.setTimestamp(new Timestamp(System.currentTimeMillis()));
		response.setTraceId(Long.MAX_VALUE);
		response.setMsg("Department information retireved");
		return ResponseEntity.status(HttpStatus.OK).body(response);
	}

}
