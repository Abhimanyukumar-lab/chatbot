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
import com.technojade.allybot.entity.BookedRoomInfo;
import com.technojade.allybot.service.RoomInfoService;

import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/roomInfo")
@Slf4j
public class RoomInfoController {

	@Autowired
	private RoomInfoService roomInfoService;

	@PostMapping("/saveRoomInfo")
	public ResponseEntity<?> saveRoomInfo(@RequestBody BookedRoomInfo roomInfo) {

		BookedRoomInfo saveRoomInfo = roomInfoService.saveRoomInfo(roomInfo);

		if (null == saveRoomInfo) {
			AllyBotResponseDto<BookedRoomInfo> roomInfoRes = new AllyBotResponseDto<>();
			String errorMessage = "Something wen't wrong exist";
			String msg = "";
			badRequest(roomInfoRes, errorMessage, msg);
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(roomInfoRes);
		}

		AllyBotResponseDto<BookedRoomInfo> roomInfoRes = new AllyBotResponseDto<>();
		roomInfoRes.setData(saveRoomInfo);
		roomInfoRes.setServiceStatus(ServiceStatus.SUCCESS);
		roomInfoRes.setTimestamp(new Timestamp(System.currentTimeMillis()));
		roomInfoRes.setTraceId(Long.MAX_VALUE);
		roomInfoRes.setMsg("User booking info has been saved successfully");
		return ResponseEntity.status(HttpStatus.CREATED).body(roomInfoRes);

	}

	// error handling method
	private void badRequest(AllyBotResponseDto<BookedRoomInfo> roomInfoRes, String errorMessage, String msg) {
		ApiError error = new ApiError();
		error.setErrorCode("TJ10101");
		error.setErrorMessage(errorMessage);
		roomInfoRes.setErrors(error);
		roomInfoRes.setMsg(msg);
		roomInfoRes.setServiceStatus(ServiceStatus.FAILURE);
		roomInfoRes.setTimestamp(new Timestamp(System.currentTimeMillis()));
		roomInfoRes.setTraceId(Long.MAX_VALUE);
	}

}