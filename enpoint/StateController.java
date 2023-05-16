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
import com.technojade.allybot.entity.City;
import com.technojade.allybot.entity.State;
import com.technojade.allybot.service.StateService;

@RestController
@RequestMapping("/state")
public class StateController {

	@Autowired
	private StateService stateService;

	@GetMapping
	public ResponseEntity<?> listOfState() {
		List<State> listOfState = stateService.listOfState();
		AllyBotResponseDto<List<State>> stateRes = new AllyBotResponseDto<>();
		stateRes.setData(listOfState);
		stateRes.setServiceStatus(ServiceStatus.SUCCESS);
		stateRes.setTimestamp(new Timestamp(System.currentTimeMillis()));
		stateRes.setTraceId(Long.MAX_VALUE);
		return ResponseEntity.status(HttpStatus.OK).body(stateRes);
	}

}
