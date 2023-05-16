package com.technojade.allybot.enpoint;

import java.sql.Timestamp;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.technojade.allybot.enpoint.response.AllyBotResponseDto;
import com.technojade.allybot.enpoint.response.ApiError;
import com.technojade.allybot.enpoint.response.ServiceStatus;
import com.technojade.allybot.entity.City;
import com.technojade.allybot.entity.User;
import com.technojade.allybot.service.CityService;

@RestController
@RequestMapping("/city")
public class CityController {

	@Autowired
	private CityService cityService;

	@GetMapping
	public ResponseEntity<?> listOfCity() {
		List<City> listOfCity = cityService.listOfCity();
		AllyBotResponseDto<List<City>> cityRes = new AllyBotResponseDto<>();
		cityRes.setData(listOfCity);
		cityRes.setServiceStatus(ServiceStatus.SUCCESS);
		cityRes.setTimestamp(new Timestamp(System.currentTimeMillis()));
		cityRes.setTraceId(Long.MAX_VALUE);
		return ResponseEntity.status(HttpStatus.OK).body(cityRes);
	}

	@GetMapping("/state/{id}")
	public ResponseEntity<?> listOfCityByState(@PathVariable("id") Long id) {

		List<City> cityResponse = cityService.listOfCityByState(id);
		
		if (null == cityResponse || cityResponse.size() == 0) {
			AllyBotResponseDto<City> cityRes = new AllyBotResponseDto<>();
			String errorMessage = "State does not exist";
			String msg = "State id not found";
			notFound(cityRes, errorMessage, msg);
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(cityRes);
		}

		AllyBotResponseDto<List<City>> cityRes = new AllyBotResponseDto<>();
		cityRes.setData(cityResponse);
		cityRes.setServiceStatus(ServiceStatus.SUCCESS);
		cityRes.setTimestamp(new Timestamp(System.currentTimeMillis()));
		cityRes.setTraceId(Long.MAX_VALUE);
		return ResponseEntity.status(HttpStatus.OK).body(cityRes);
	}

	private void notFound(AllyBotResponseDto<City> cityRes, String errorMessage, String msg) {
		cityRes.setData(null);
		ApiError error = new ApiError();
		error.setErrorCode("TJ10102");
		error.setErrorMessage(errorMessage);
		cityRes.setErrors(error);
		cityRes.setMsg(msg);
		cityRes.setServiceStatus(ServiceStatus.FAILURE);
		cityRes.setTimestamp(new Timestamp(System.currentTimeMillis()));
		cityRes.setTraceId(Long.MAX_VALUE);
	}

}
