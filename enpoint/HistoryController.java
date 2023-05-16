package com.technojade.allybot.enpoint;

import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.technojade.allybot.dtos.HistoryDto;
import com.technojade.allybot.enpoint.response.AllyBotResponseDto;
import com.technojade.allybot.enpoint.response.ApiError;
import com.technojade.allybot.enpoint.response.ServiceStatus;
import com.technojade.allybot.entity.FaqMst;
import com.technojade.allybot.entity.History;
import com.technojade.allybot.service.HistoryService;

import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/history")
@Slf4j
public class HistoryController {

	@Autowired
	private HistoryService historyService;
	
	
	@PostMapping
	public ResponseEntity<?> saveHistory(@RequestBody History history){
				
		Optional<History> ofNullable = Optional.ofNullable(history);
		
		if(null == history || !ofNullable.isPresent()) {
			AllyBotResponseDto<History> historyRes = new AllyBotResponseDto<>();
			String errorMessage = "Something wen't wrong exist";
			String msg = "";
			noContent(historyRes, errorMessage, msg);
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(historyRes);
		}
		
		History historyResponse = historyService.saveHistory(history);
		
		HistoryDto historyDto = new HistoryDto();
		historyDto.setUserId(history.getUserId());
		historyDto.setCharJsonStr(history.getCharJsonStr());
		historyDto.setSuperwiserId(history.getSuperwiserId());
		
		AllyBotResponseDto<History> historyRes = new AllyBotResponseDto<>();
		historyRes.setData(historyResponse);
		historyRes.setServiceStatus(ServiceStatus.SUCCESS);
		historyRes.setTimestamp(new Timestamp(System.currentTimeMillis()));
		historyRes.setTraceId(Long.MAX_VALUE);
		historyRes.setMsg("History details has been saved successfully");
		return ResponseEntity.status(HttpStatus.CREATED).body(historyDto);
	}
	
	@GetMapping("/user/{id}")
	public ResponseEntity<?> getHistoryByUserId(@PathVariable("id") Long id){
		
		Optional<List<History>> byUserIdResponse = historyService.getHistoryByUserId(id);
		if(byUserIdResponse.get().isEmpty()) {
			AllyBotResponseDto<History> historyRes = new AllyBotResponseDto<>();
			String errorMessage = "Something wen't wrong exist";
			String msg = "";
			noContent(historyRes, errorMessage, msg);
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(historyRes);
		}
		
		AllyBotResponseDto<List<History>> historyRes = new AllyBotResponseDto<>();
		historyRes.setData(byUserIdResponse.get());
		historyRes.setServiceStatus(ServiceStatus.SUCCESS);
		historyRes.setTimestamp(new Timestamp(System.currentTimeMillis()));
		historyRes.setTraceId(Long.MAX_VALUE);
		return ResponseEntity.status(HttpStatus.OK).body(historyRes);	
	}
	
	@GetMapping("/superwiser/{id}")
	public ResponseEntity<?> getHistoryBySuperwiserId(@PathVariable("id") Long id){
		
		Optional<List<History>> bySuperwiserIdResponse = historyService.getHistoryBySuperwiserId(id);
		if(bySuperwiserIdResponse.get().isEmpty()) {
			AllyBotResponseDto<History> historyRes = new AllyBotResponseDto<>();
			String errorMessage = "Something wen't wrong exist";
			String msg = "";
			noContent(historyRes, errorMessage, msg);
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(historyRes);
		}
		
		AllyBotResponseDto<List<History>> historyRes = new AllyBotResponseDto<>();
		historyRes.setData(bySuperwiserIdResponse.get());
		historyRes.setServiceStatus(ServiceStatus.SUCCESS);
		historyRes.setTimestamp(new Timestamp(System.currentTimeMillis()));
		historyRes.setTraceId(Long.MAX_VALUE);
		return ResponseEntity.status(HttpStatus.OK).body(historyRes);	
	}
	
	
	private void noContent(AllyBotResponseDto<History> history, String errorMessage, String msg) {
		ApiError error = new ApiError();
		error.setErrorCode("TJ10103");
		error.setErrorMessage(errorMessage);
		history.setErrors(error);
		history.setMsg(msg); 
		history.setServiceStatus(ServiceStatus.FAILURE);
		history.setTimestamp(new Timestamp(System.currentTimeMillis()));
		history.setTraceId(Long.MAX_VALUE);
	}
	
	
}
