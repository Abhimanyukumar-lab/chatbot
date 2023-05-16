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
import com.technojade.allybot.entity.UserBookingDetail;
import com.technojade.allybot.entity.UserBookingInfo;
import com.technojade.allybot.service.UserBookingDetailService;

import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/userBookingDetail")
@Slf4j
public class UserBookingDetailController {

	@Autowired
	private UserBookingDetailService userBookingDetailService;

	@PostMapping("/saveUserBookingDetail")
	public ResponseEntity<?> saveUserBookingDetailData(@RequestBody UserBookingDetail userBookingDetail) {

		UserBookingDetail bookingDetailResponse = userBookingDetailService.saveUserBookingDetail(userBookingDetail);

		if (null == bookingDetailResponse) {
			AllyBotResponseDto<UserBookingDetail> bookingDetailRes = new AllyBotResponseDto<>();
			String errorMessage = "Something wen't wrong exist";
			String msg = "";
			badRequest(bookingDetailRes, errorMessage, msg);
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(bookingDetailRes);
		}		

		AllyBotResponseDto<UserBookingDetail> bookingDetailRes = new AllyBotResponseDto<>();
		bookingDetailRes.setData(bookingDetailResponse);
		bookingDetailRes.setServiceStatus(ServiceStatus.SUCCESS);
		bookingDetailRes.setTimestamp(new Timestamp(System.currentTimeMillis()));
		bookingDetailRes.setTraceId(Long.MAX_VALUE);
		bookingDetailRes.setMsg("Address has been saved successfully");
		return ResponseEntity.status(HttpStatus.CREATED).body(bookingDetailRes);

	}

	private void badRequest(AllyBotResponseDto<UserBookingDetail> userBookingDetailRes, String errorMessage, String msg) {
		ApiError error = new ApiError();
		error.setErrorCode("TJ10101");
		error.setErrorMessage(errorMessage);
		userBookingDetailRes.setErrors(error);
		userBookingDetailRes.setMsg(msg);
		userBookingDetailRes.setServiceStatus(ServiceStatus.FAILURE);
		userBookingDetailRes.setTimestamp(new Timestamp(System.currentTimeMillis()));
		userBookingDetailRes.setTraceId(Long.MAX_VALUE);
	}

}