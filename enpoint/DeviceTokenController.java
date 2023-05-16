package com.technojade.allybot.enpoint;

import java.sql.Timestamp;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.technojade.allybot.enpoint.response.AllyBotResponseDto;
import com.technojade.allybot.enpoint.response.ServiceStatus;
import com.technojade.allybot.entity.DeviceTokenMst;
import com.technojade.allybot.service.DeviceTokenService;

@RestController
@RequestMapping("/devicetoken")
public class DeviceTokenController {

	
	@Autowired
	private DeviceTokenService deviceTokenService;
	
	@PostMapping
	public ResponseEntity<?> saveDeviceToken(@RequestBody DeviceTokenMst deviceTokenMst){
		
		DeviceTokenMst deviceToken = deviceTokenService.saveDeviceToken(deviceTokenMst);
		AllyBotResponseDto<DeviceTokenMst> deviceTokenRes = new AllyBotResponseDto<>();
		deviceTokenRes.setData(deviceToken);
		deviceTokenRes.setServiceStatus(ServiceStatus.SUCCESS);
		deviceTokenRes.setTimestamp(new Timestamp(System.currentTimeMillis()));
		deviceTokenRes.setTraceId(Long.MAX_VALUE);
		deviceTokenRes.setMsg("Device token has been saved successfully");
		return ResponseEntity.status(HttpStatus.CREATED).body(deviceTokenRes);
	}
	
	
}
