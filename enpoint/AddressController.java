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
import com.technojade.allybot.entity.Address;
import com.technojade.allybot.service.AddressService;

import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping(value = "/address")
@Slf4j
public class AddressController {

	@Autowired
	private AddressService addressService;

	@PostMapping("/saveAddress")
	public ResponseEntity<?> saveAddress(@RequestBody Address address) {

		Address addressResponse = addressService.saveAddress(address);

		if (null == addressResponse) {
			AllyBotResponseDto<Address> addressRes = new AllyBotResponseDto<>();
			String errorMessage = "Something wen't wrong exist";
			String msg = "";
			badRequest(addressRes, errorMessage, msg);
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(addressRes);
		}

		AllyBotResponseDto<Address> addressRes = new AllyBotResponseDto<>();
		addressRes.setData(addressResponse);
		addressRes.setServiceStatus(ServiceStatus.SUCCESS);
		addressRes.setTimestamp(new Timestamp(System.currentTimeMillis()));
		addressRes.setTraceId(Long.MAX_VALUE);
		addressRes.setMsg("Address has been saved successfully");
		return ResponseEntity.status(HttpStatus.CREATED).body(addressRes);

	}

	private void badRequest(AllyBotResponseDto<Address> addressRes, String errorMessage, String msg) {
		ApiError error = new ApiError();
		error.setErrorCode("TJ10101");
		error.setErrorMessage(errorMessage);
		addressRes.setErrors(error);
		addressRes.setMsg(msg);
		addressRes.setServiceStatus(ServiceStatus.FAILURE);
		addressRes.setTimestamp(new Timestamp(System.currentTimeMillis()));
		addressRes.setTraceId(Long.MAX_VALUE);
	}

}