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
import com.technojade.allybot.entity.RoleTypeMst;
import com.technojade.allybot.service.RoleTypeService;

@RestController
@RequestMapping("/roletype")
public class RoleTypeController {

	@Autowired
	private RoleTypeService roleTypeService;

	@GetMapping
	public ResponseEntity<?> listOfRoleType() {

		List<RoleTypeMst> roleType = roleTypeService.listOfRoleType();
		AllyBotResponseDto<List<RoleTypeMst>> roleTypeRes = new AllyBotResponseDto<>();
		roleTypeRes.setData(roleType);
		roleTypeRes.setServiceStatus(ServiceStatus.SUCCESS);
		roleTypeRes.setTimestamp(new Timestamp(System.currentTimeMillis()));
		roleTypeRes.setTraceId(Long.MAX_VALUE);
		return ResponseEntity.status(HttpStatus.OK).body(roleTypeRes);
	}

}
