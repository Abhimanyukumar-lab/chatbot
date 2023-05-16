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
import com.technojade.allybot.enpoint.response.ApiError;
import com.technojade.allybot.enpoint.response.ServiceStatus;
import com.technojade.allybot.entity.UserBookingInfo;
import com.technojade.allybot.service.UserBookingInfoService;

import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/userBookingInfo")
@Slf4j
public class UserBookingInfoController {


	@Autowired
	private UserBookingInfoService userBookingInfoService;

	@PostMapping("/saveUserBookingInfo")
	public ResponseEntity<?> saveUserBookingInfo(@RequestBody UserBookingInfo userBookingInfo){

		UserBookingInfo userInfo = userBookingInfoService.saveUserBookingInfo(userBookingInfo);

		if (null == userBookingInfo) {
			AllyBotResponseDto<UserBookingInfo> userBookingInfoRes = new AllyBotResponseDto<>();
			String errorMessage = "Something wen't wrong exist";
			String msg = "";
			badRequest(userBookingInfoRes, errorMessage, msg);
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(userBookingInfoRes);
		}

		AllyBotResponseDto<UserBookingInfo> userBookingInfoRes = new AllyBotResponseDto<>();
		userBookingInfoRes.setData(userInfo);
		userBookingInfoRes.setServiceStatus(ServiceStatus.SUCCESS);
		userBookingInfoRes.setTimestamp(new Timestamp(System.currentTimeMillis()));
		userBookingInfoRes.setTraceId(Long.MAX_VALUE);
		userBookingInfoRes.setMsg("User booking info has been saved successfully");
		return ResponseEntity.status(HttpStatus.CREATED).body(userBookingInfoRes);
	}


	// error handling method
	private void badRequest(AllyBotResponseDto<UserBookingInfo> userBookingInfoRes, String errorMessage, String msg) {
		ApiError error = new ApiError();
		error.setErrorCode("TJ10101");
		error.setErrorMessage(errorMessage);
		userBookingInfoRes.setErrors(error);
		userBookingInfoRes.setMsg(msg);
		userBookingInfoRes.setServiceStatus(ServiceStatus.FAILURE);
		userBookingInfoRes.setTimestamp(new Timestamp(System.currentTimeMillis()));
		userBookingInfoRes.setTraceId(Long.MAX_VALUE);
	}


}