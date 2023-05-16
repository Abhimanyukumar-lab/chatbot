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
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.technojade.allybot.dtos.FaqDto;
import com.technojade.allybot.enpoint.response.AllyBotResponseDto;
import com.technojade.allybot.enpoint.response.ApiError;
import com.technojade.allybot.enpoint.response.ServiceStatus;
import com.technojade.allybot.entity.Employee;
import com.technojade.allybot.entity.FaqMst;
import com.technojade.allybot.service.FaqService;

import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/faq")
@Slf4j
public class FaqController {
	
	@Autowired
	private FaqService faqService;
	
	@PostMapping
	public ResponseEntity<?> saveFaqData(@RequestBody FaqMst faqMst){
				
		Optional<FaqMst> ofNullable = Optional.ofNullable(faqMst);
				
		if(null == faqMst || !ofNullable.isPresent()) {
			AllyBotResponseDto<FaqMst> faqRes = new AllyBotResponseDto<>();
			String errorMessage = "Something wen't wrong exist";
			String msg = "";
			noContent(faqRes, errorMessage, msg);
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(faqRes);
		}
		
		FaqMst faqResponse = faqService.saveFaq(faqMst);
		FaqDto faqDto = new FaqDto();
		faqDto.setQuestion(faqMst.getQuestion());
		faqDto.setClientId(faqMst.getClientId());
		faqDto.setHotelId(faqMst.getHotelId());
		
		AllyBotResponseDto<FaqMst> faqRes = new AllyBotResponseDto<>();
		faqRes.setData(faqResponse);
		faqRes.setServiceStatus(ServiceStatus.SUCCESS);
		faqRes.setTimestamp(new Timestamp(System.currentTimeMillis()));
		faqRes.setTraceId(Long.MAX_VALUE);
		faqRes.setMsg("Faq details has been saved successfully");
		return ResponseEntity.status(HttpStatus.CREATED).body(faqDto);
		
	}
	
	@GetMapping
	public ResponseEntity<?> listOfFaqData(){
		
		List<FaqMst> listOfFaq = faqService.listOfFaq();
		AllyBotResponseDto<List<FaqMst>> faqRes = new AllyBotResponseDto<>();
		faqRes.setData(listOfFaq);
		faqRes.setServiceStatus(ServiceStatus.SUCCESS);
		faqRes.setTimestamp(new Timestamp(System.currentTimeMillis()));
		faqRes.setTraceId(Long.MAX_VALUE);
		return ResponseEntity.status(HttpStatus.OK).body(faqRes);		
	}
	
	
	@GetMapping("/{id}")
	public ResponseEntity<?> getFaqById(@PathVariable("id") Long id){

		Optional<FaqMst> faqById = faqService.getFaqById(id);
		
		if(!faqById.isPresent()) {
			AllyBotResponseDto<FaqMst> faqRes = new AllyBotResponseDto<>();
			String errorMessage = "Something wen't wrong exist";
			String msg = "";
			noContent(faqRes, errorMessage, msg);
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(faqRes);
		}
		
		AllyBotResponseDto<FaqMst> faqRes = new AllyBotResponseDto<>();
		faqRes.setData(faqById.get());
		faqRes.setServiceStatus(ServiceStatus.SUCCESS);
		faqRes.setTimestamp(new Timestamp(System.currentTimeMillis()));
		faqRes.setTraceId(Long.MAX_VALUE);
		return ResponseEntity.status(HttpStatus.OK).body(faqRes);		
	}
	
	
	@PutMapping
	public ResponseEntity<?> updateFaqData(@RequestBody FaqMst faqMst){
		Optional<FaqMst> ofNullable = Optional.ofNullable(faqMst);
		
		if(null == faqMst || !ofNullable.isPresent()) {
			AllyBotResponseDto<FaqMst> faqRes = new AllyBotResponseDto<>();
			String errorMessage = "Something wen't wrong exist";
			String msg = "";
			noContent(faqRes, errorMessage, msg);
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(faqRes);
		}
		
		FaqMst faqResponse = faqService.updateFaq(faqMst);
		FaqDto faqDto = new FaqDto();
		faqDto.setQuestion(faqMst.getQuestion());
		faqDto.setClientId(faqMst.getClientId());
		faqDto.setHotelId(faqMst.getHotelId());
		
		AllyBotResponseDto<FaqMst> faqRes = new AllyBotResponseDto<>();
		faqRes.setData(faqResponse);
		faqRes.setServiceStatus(ServiceStatus.SUCCESS);
		faqRes.setTimestamp(new Timestamp(System.currentTimeMillis()));
		faqRes.setTraceId(Long.MAX_VALUE);
		faqRes.setMsg("Faq details has been saved successfully");
		return ResponseEntity.status(HttpStatus.CREATED).body(faqDto);
	}
	
	private void noContent(AllyBotResponseDto<FaqMst> faq, String errorMessage, String msg) {
		ApiError error = new ApiError();
		error.setErrorCode("TJ10103");
		error.setErrorMessage(errorMessage);
		faq.setErrors(error);
		faq.setMsg(msg);
		faq.setServiceStatus(ServiceStatus.FAILURE);
		faq.setTimestamp(new Timestamp(System.currentTimeMillis()));
		faq.setTraceId(Long.MAX_VALUE);
	}
	
	
}
