/**
 * 
 */
package com.technojade.allybot.enpoint;

import java.sql.Timestamp;
import java.time.Instant;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.technojade.allybot.enpoint.response.AllyBotResponseDto;
import com.technojade.allybot.enpoint.response.ServiceStatus;
import com.technojade.allybot.entity.User;
import com.technojade.allybot.service.OtpService;
import com.technojade.allybot.service.UserService;

/**
 * @author BC70873
 *
 */
@RestController
@RequestMapping(value = "/otp")
public class OtpControler {
	
	@Autowired
	private OtpService otpService;
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private ObjectMapper objectMapper;
	
	@PostMapping(value = "/send")
	public ResponseEntity<?> send(@RequestBody User user){
		String otp = otpService.sendOtpOnly(user.getMobileNumber());
		AllyBotResponseDto<JsonNode> otpRes = new AllyBotResponseDto<>();
		ObjectNode objectNode = objectMapper.createObjectNode();
		objectNode.put("otp", otp);
		otpRes.setData(objectNode);
		if(otp != null) {
			otpRes.setServiceStatus(ServiceStatus.SUCCESS);
			otpRes.setMsg("Otp Sent");
		} else {
			otpRes.setServiceStatus(ServiceStatus.FAILURE);
			otpRes.setMsg("Otp sending failed");
		}
		otpRes.setTraceId(Long.MAX_VALUE);
		otpRes.setTimestamp(Timestamp.from(Instant.now()));
		return ResponseEntity.status(HttpStatus.OK).body(otpRes);
	}

	@PostMapping(value = "/send/login-credential")
	public ResponseEntity<?> sendWithUserCredential(@RequestBody User user){
		String otp = otpService.sendOtpWithCredential(user.getMobileNumber());
		AllyBotResponseDto<JsonNode> otpRes = new AllyBotResponseDto<>();
		ObjectNode objectNode = objectMapper.createObjectNode();
		objectNode.put("otp", otp);
		otpRes.setData(objectNode);
		if(otp != null) {
			otpRes.setServiceStatus(ServiceStatus.SUCCESS);
			otpRes.setMsg("Otp Sent");
		} else {
			otpRes.setServiceStatus(ServiceStatus.FAILURE);
			otpRes.setMsg("Otp sending failed");
		}
		otpRes.setTraceId(Long.MAX_VALUE);
		otpRes.setTimestamp(Timestamp.from(Instant.now()));
		return ResponseEntity.status(HttpStatus.OK).body(otpRes);
	}

	@PostMapping(value = "/validate")
	public ResponseEntity<?> validate(@RequestBody User otpReqDto){
		boolean isValidated = otpService.validate(otpReqDto.getMobileNumber(), otpReqDto.getOtp());
		
		if(!isValidated) {
			AllyBotResponseDto<User> otpRes = new AllyBotResponseDto<>();
			otpRes.setData(null);
			otpRes.setServiceStatus(ServiceStatus.SUCCESS);
			otpRes.setMsg("OTP is invalid");
			otpRes.setTraceId(Long.MAX_VALUE);
			otpRes.setTimestamp(Timestamp.from(Instant.now()));
			return ResponseEntity.status(HttpStatus.OK).body(otpRes);	
		}
		
		User user = userService.loginByMobileNumber(otpReqDto.getMobileNumber());
		AllyBotResponseDto<User> otpRes = new AllyBotResponseDto<>();
		otpRes.setData(user);
		otpRes.setServiceStatus(ServiceStatus.SUCCESS);
		otpRes.setMsg("Otp validated, User will be logging now.");
		otpRes.setTraceId(Long.MAX_VALUE);
		otpRes.setTimestamp(Timestamp.from(Instant.now()));
		return ResponseEntity.status(HttpStatus.OK).body(otpRes);
	}

}
