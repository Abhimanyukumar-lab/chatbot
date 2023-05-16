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

import com.technojade.allybot.dtos.BotAnswerDto;
import com.technojade.allybot.dtos.BotQuestionAnswerDto;
import com.technojade.allybot.enpoint.response.AllyBotResponseDto;
import com.technojade.allybot.enpoint.response.ApiError;
import com.technojade.allybot.enpoint.response.ServiceStatus;
import com.technojade.allybot.entity.BotAnswer;
import com.technojade.allybot.entity.BotQuestion;
import com.technojade.allybot.service.BotAnswerService;

import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/bot-answer")
@Slf4j
public class BotAnswerController {

	@Autowired
	private BotAnswerService botAnswerService;

	@PostMapping
	public ResponseEntity<?> saveBoatAnswer(@RequestBody BotAnswer botAnswer) {

		Optional<BotAnswer> ofNullable = Optional.ofNullable(botAnswer);
		if (null == botAnswer || !ofNullable.isPresent()) {
			AllyBotResponseDto<BotAnswer> botAnswerRes = new AllyBotResponseDto<>();
			String errorMessage = "Something wen't wrong exist";
			String msg = "";
			noContent(botAnswerRes, errorMessage, msg);
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(botAnswerRes);
		}
		BotAnswer botAnswerResponse = botAnswerService.saveBotAnswer(botAnswer);
		BotAnswerDto answerDto = new BotAnswerDto();
		answerDto.setAnswer(botAnswer.getAnswer());
		answerDto.setBotQid(botAnswer.getBotQid());
		answerDto.setClientId(botAnswer.getClientId());
		answerDto.setHotelId(botAnswer.getHotelId());

		AllyBotResponseDto<BotAnswer> botAnswerRes = new AllyBotResponseDto<>();
		botAnswerRes.setData(botAnswerResponse);
		botAnswerRes.setServiceStatus(ServiceStatus.SUCCESS);
		botAnswerRes.setTimestamp(new Timestamp(System.currentTimeMillis()));
		botAnswerRes.setTraceId(Long.MAX_VALUE);
		botAnswerRes.setMsg("Bot Answer has been saved successfully");
		return ResponseEntity.status(HttpStatus.CREATED).body(answerDto);
	}

	@GetMapping
	public ResponseEntity<?> getAllBotAnswers() {
		List<BotAnswer> botAnswerResponse = botAnswerService.listOfBotAnswer();
		AllyBotResponseDto<List<BotAnswer>> botAnswerRes = new AllyBotResponseDto<>();
		botAnswerRes.setData(botAnswerResponse);
		botAnswerRes.setServiceStatus(ServiceStatus.SUCCESS);
		botAnswerRes.setTimestamp(new Timestamp(System.currentTimeMillis()));
		botAnswerRes.setTraceId(Long.MAX_VALUE);
		return ResponseEntity.status(HttpStatus.OK).body(botAnswerRes);
	}

	@GetMapping("/{id}")
	public ResponseEntity<?> getBotANswerById(@PathVariable("id") Long id) {

		BotAnswer botAnswerId = botAnswerService.getByBotAnswerId(id);
		if (botAnswerId == null) {
			AllyBotResponseDto<BotAnswer> botAnswerRes = new AllyBotResponseDto<>();
			String errorMessage = "Something wen't wrong exist";
			String msg = "";
			noContent(botAnswerRes, errorMessage, msg);
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(botAnswerRes);
		}
		AllyBotResponseDto<BotAnswer> botAnswerRes = new AllyBotResponseDto<>();
		botAnswerRes.setData(botAnswerId);
		botAnswerRes.setServiceStatus(ServiceStatus.SUCCESS);
		botAnswerRes.setTimestamp(new Timestamp(System.currentTimeMillis()));
		botAnswerRes.setTraceId(Long.MAX_VALUE);
		return ResponseEntity.status(HttpStatus.OK).body(botAnswerRes);
	}

	@PutMapping
	public ResponseEntity<?> updateBoatAnswer(@RequestBody BotAnswer botAnswer) {

		Optional<BotAnswer> ofNullable = Optional.ofNullable(botAnswer);
		if (null == botAnswer || !ofNullable.isPresent()) {
			AllyBotResponseDto<BotAnswer> botAnswerRes = new AllyBotResponseDto<>();
			String errorMessage = "Something wen't wrong exist";
			String msg = "";
			noContent(botAnswerRes, errorMessage, msg);
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(botAnswerRes);
		}
		BotAnswer botAnswerResponse = botAnswerService.saveBotAnswer(botAnswer);
		BotAnswerDto answerDto = new BotAnswerDto();
		answerDto.setAnswer(botAnswer.getAnswer());
		answerDto.setBotQid(botAnswer.getBotQid());
		answerDto.setClientId(botAnswer.getClientId());
		answerDto.setHotelId(botAnswer.getHotelId());

		AllyBotResponseDto<BotAnswer> botAnswerRes = new AllyBotResponseDto<>();
		botAnswerRes.setData(botAnswerResponse);
		botAnswerRes.setServiceStatus(ServiceStatus.SUCCESS);
		botAnswerRes.setTimestamp(new Timestamp(System.currentTimeMillis()));
		botAnswerRes.setTraceId(Long.MAX_VALUE);
		botAnswerRes.setMsg("Bot Answer has been updated successfully");
		return ResponseEntity.status(HttpStatus.CREATED).body(answerDto);
	}

	@GetMapping("/qid/{id}")
	public ResponseEntity<?> getBotAnswerByQuestionId(@PathVariable("id") Long id) {

		BotQuestionAnswerDto botQuestionId = botAnswerService.getByBotQuestionId(id);
		if (botQuestionId == null) {
			AllyBotResponseDto<BotAnswer> botAnswerRes = new AllyBotResponseDto<>();
			String errorMessage = "Something wen't wrong exist";
			String msg = "";
			noContent(botAnswerRes, errorMessage, msg);
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(botAnswerRes);
		}
		AllyBotResponseDto<BotQuestionAnswerDto> botAnswerRes = new AllyBotResponseDto<>();
		botAnswerRes.setData(botQuestionId);
		botAnswerRes.setServiceStatus(ServiceStatus.SUCCESS);
		botAnswerRes.setTimestamp(new Timestamp(System.currentTimeMillis()));
		botAnswerRes.setTraceId(Long.MAX_VALUE);
		return ResponseEntity.status(HttpStatus.OK).body(botAnswerRes);
	}

	private void noContent(AllyBotResponseDto<BotAnswer> bot, String errorMessage, String msg) {
		ApiError error = new ApiError();
		error.setErrorCode("TJ10103");
		error.setErrorMessage(errorMessage);
		bot.setErrors(error);
		bot.setMsg(msg);
		bot.setServiceStatus(ServiceStatus.FAILURE);
		bot.setTimestamp(new Timestamp(System.currentTimeMillis()));
		bot.setTraceId(Long.MAX_VALUE);
	}

}
