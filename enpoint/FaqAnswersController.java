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

import com.technojade.allybot.dtos.FaqAnswersDto;
import com.technojade.allybot.dtos.FaqQuestionAnswerDto;
import com.technojade.allybot.enpoint.response.AllyBotResponseDto;
import com.technojade.allybot.enpoint.response.ApiError;
import com.technojade.allybot.enpoint.response.ServiceStatus;
import com.technojade.allybot.entity.FaqAnswers;
import com.technojade.allybot.service.FaqAnswersService;

import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/faq-answers")
@Slf4j
public class FaqAnswersController {

	@Autowired
	private FaqAnswersService faqAnswersService;

	@PostMapping
	public ResponseEntity<?> saveFaqAnswers(@RequestBody FaqAnswers faqAnswers) {

		Optional<FaqAnswers> ofNullable = Optional.ofNullable(faqAnswers);
		if (null == faqAnswers || !ofNullable.isPresent()) {
			AllyBotResponseDto<FaqAnswers> faqRes = new AllyBotResponseDto<>();
			String errorMessage = "Something wen't wrong exist";
			String msg = "";
			noContent(faqRes, errorMessage, msg);
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(faqRes);
		}

		FaqAnswers saveFaqAnswerResponse = faqAnswersService.saveFaqAnswer(faqAnswers);
		FaqAnswersDto fq = new FaqAnswersDto();
		fq.setAnswers(faqAnswers.getAnswers());
		fq.setFaqId(faqAnswers.getFaqId());
		fq.setClientId(faqAnswers.getClientId());
		fq.setHotelId(faqAnswers.getHotelId());

		AllyBotResponseDto<FaqAnswers> faqAnswerRes = new AllyBotResponseDto<>();
		faqAnswerRes.setData(saveFaqAnswerResponse);
		faqAnswerRes.setServiceStatus(ServiceStatus.SUCCESS);
		faqAnswerRes.setTimestamp(new Timestamp(System.currentTimeMillis()));
		faqAnswerRes.setTraceId(Long.MAX_VALUE);
		faqAnswerRes.setMsg("Faq answer details has been saved successfully");
		return ResponseEntity.status(HttpStatus.CREATED).body(fq);
	}

	@GetMapping
	public ResponseEntity<?> listOfFaqAnswers() {
		List<FaqAnswers> faqAnswerResponse = faqAnswersService.listOfFaqAnswer();
		AllyBotResponseDto<List<FaqAnswers>> faqAnswerRes = new AllyBotResponseDto<>();
		faqAnswerRes.setData(faqAnswerResponse);
		faqAnswerRes.setServiceStatus(ServiceStatus.SUCCESS);
		faqAnswerRes.setTimestamp(new Timestamp(System.currentTimeMillis()));
		faqAnswerRes.setTraceId(Long.MAX_VALUE);
		return ResponseEntity.status(HttpStatus.OK).body(faqAnswerRes);
	}

	@GetMapping("/{id}")
	public ResponseEntity<?> getFaqAnswerById(@PathVariable("id") Long id) {
		Optional<FaqAnswers> faqAnswerResponse = faqAnswersService.getByFaqAnswerId(id);
		if (!faqAnswerResponse.isPresent()) {
			AllyBotResponseDto<FaqAnswers> faqAnswerRes = new AllyBotResponseDto<>();
			String errorMessage = "Something wen't wrong exist";
			String msg = "";
			noContent(faqAnswerRes, errorMessage, msg);
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(faqAnswerRes);
		}
		AllyBotResponseDto<FaqAnswers> faqAnswerRes = new AllyBotResponseDto<>();
		faqAnswerRes.setData(faqAnswerResponse.get());
		faqAnswerRes.setServiceStatus(ServiceStatus.SUCCESS);
		faqAnswerRes.setTimestamp(new Timestamp(System.currentTimeMillis()));
		faqAnswerRes.setTraceId(Long.MAX_VALUE);
		return ResponseEntity.status(HttpStatus.OK).body(faqAnswerRes);
	}

	@GetMapping("qid/{id}")
	public ResponseEntity<?> getFaqAnswerByQuestionId(@PathVariable("id") Long id) {
		FaqQuestionAnswerDto faqAnswerResponse = faqAnswersService.getByFaqId(id);
		if (faqAnswerResponse == null) {
			AllyBotResponseDto<FaqAnswers> faqAnswerRes = new AllyBotResponseDto<>();
			String errorMessage = "Something wen't wrong exist";
			String msg = "";
			noContent(faqAnswerRes, errorMessage, msg);
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(faqAnswerRes);
		}
		AllyBotResponseDto<FaqQuestionAnswerDto> faqAnswerRes = new AllyBotResponseDto<>();
		faqAnswerRes.setData(faqAnswerResponse);
		faqAnswerRes.setServiceStatus(ServiceStatus.SUCCESS);
		faqAnswerRes.setTimestamp(new Timestamp(System.currentTimeMillis()));
		faqAnswerRes.setTraceId(Long.MAX_VALUE);
		return ResponseEntity.status(HttpStatus.OK).body(faqAnswerRes);
	}

	@PutMapping
	public ResponseEntity<?> updateFaqAnswers(@RequestBody FaqAnswers faqAnswers) {

		Optional<FaqAnswers> ofNullable = Optional.ofNullable(faqAnswers);
		if (null == faqAnswers || !ofNullable.isPresent()) {
			AllyBotResponseDto<FaqAnswers> faqRes = new AllyBotResponseDto<>();
			String errorMessage = "Something wen't wrong exist";
			String msg = "";
			noContent(faqRes, errorMessage, msg);
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(faqRes);
		}
		FaqAnswers saveFaqAnswerResponse = faqAnswersService.updateFaqAnswer(faqAnswers);
		FaqAnswersDto fq = new FaqAnswersDto();
		fq.setAnswers(faqAnswers.getAnswers());
		fq.setFaqId(faqAnswers.getFaqId());
		fq.setClientId(faqAnswers.getClientId());
		fq.setHotelId(faqAnswers.getHotelId());

		AllyBotResponseDto<FaqAnswers> faqAnswerRes = new AllyBotResponseDto<>();
		faqAnswerRes.setData(saveFaqAnswerResponse);
		faqAnswerRes.setServiceStatus(ServiceStatus.SUCCESS);
		faqAnswerRes.setTimestamp(new Timestamp(System.currentTimeMillis()));
		faqAnswerRes.setTraceId(Long.MAX_VALUE);
		faqAnswerRes.setMsg("Faq answer details has been updated successfully");
		return ResponseEntity.status(HttpStatus.CREATED).body(fq);
	}

	private void noContent(AllyBotResponseDto<FaqAnswers> faqAnswer, String errorMessage, String msg) {
		ApiError error = new ApiError();
		error.setErrorCode("TJ10103");
		error.setErrorMessage(errorMessage);
		faqAnswer.setErrors(error);
		faqAnswer.setMsg(msg);
		faqAnswer.setServiceStatus(ServiceStatus.FAILURE);
		faqAnswer.setTimestamp(new Timestamp(System.currentTimeMillis()));
		faqAnswer.setTraceId(Long.MAX_VALUE);
	}

}
