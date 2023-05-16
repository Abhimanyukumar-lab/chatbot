package com.technojade.allybot.enpoint;

import java.sql.Timestamp;
import java.util.ArrayList;
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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.technojade.allybot.dtos.BotQuestionAnswerFileDto;
import com.technojade.allybot.dtos.BotQuestionDto;
import com.technojade.allybot.dtos.FaqDto;
import com.technojade.allybot.enpoint.response.AllyBotResponseDto;
import com.technojade.allybot.enpoint.response.ApiError;
import com.technojade.allybot.enpoint.response.ServiceStatus;
import com.technojade.allybot.entity.BotAnswer;
import com.technojade.allybot.entity.BotQuestion;
import com.technojade.allybot.entity.FaqMst;
import com.technojade.allybot.entity.GroupMst;
import com.technojade.allybot.service.BotAnswerService;
import com.technojade.allybot.service.BotCsvHelper;
import com.technojade.allybot.service.BotQuestionService;
import com.technojade.allybot.service.BotQuestionServiceImpl;

import lombok.extern.slf4j.Slf4j;
import springfox.documentation.service.ResponseMessage;

@RestController
@RequestMapping("/bot-question")
@Slf4j
public class BotQuestionController {

	@Autowired
	private BotQuestionService botQuestionService;

	@Autowired
	private BotAnswerService botAnswerService;

	@PostMapping
	public ResponseEntity<?> saveBotQuestion(@RequestBody BotQuestion botQuestion) {

		Optional<BotQuestion> ofNullable = Optional.ofNullable(botQuestion);

		if (null == botQuestion || !ofNullable.isPresent()) {
			AllyBotResponseDto<BotQuestion> botQuestionRes = new AllyBotResponseDto<>();
			String errorMessage = "Something wen't wrong exist";
			String msg = "";
			noContent(botQuestionRes, errorMessage, msg);
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(botQuestionRes);
		}
		BotQuestion botQuestionResponse = botQuestionService.saveBotQuestion(botQuestion);
		BotQuestionDto botQuestionDto = new BotQuestionDto();
		botQuestionDto.setQuestion(botQuestion.getQuestion());
		botQuestionDto.setClientId(botQuestion.getClientId());
		botQuestionDto.setHotelId(botQuestion.getHotelId());

		AllyBotResponseDto<BotQuestion> botQuestionRes = new AllyBotResponseDto<>();
		botQuestionRes.setData(botQuestionResponse);
		botQuestionRes.setServiceStatus(ServiceStatus.SUCCESS);
		botQuestionRes.setTimestamp(new Timestamp(System.currentTimeMillis()));
		botQuestionRes.setTraceId(Long.MAX_VALUE);
		botQuestionRes.setMsg("Bot question has been saved successfully");
		return ResponseEntity.status(HttpStatus.CREATED).body(botQuestionDto);
	}

	@GetMapping
	public ResponseEntity<?> getAllBotQuestion() {

		List<BotQuestion> ofBotQuestion = botQuestionService.listOfBotQuestion();
		AllyBotResponseDto<List<BotQuestion>> botQuestionRes = new AllyBotResponseDto<>();
		botQuestionRes.setData(ofBotQuestion);
		botQuestionRes.setServiceStatus(ServiceStatus.SUCCESS);
		botQuestionRes.setTimestamp(new Timestamp(System.currentTimeMillis()));
		botQuestionRes.setTraceId(Long.MAX_VALUE);
		return ResponseEntity.status(HttpStatus.OK).body(botQuestionRes);

	}

	@PutMapping
	public ResponseEntity<?> updateBotQuestion(@RequestBody BotQuestion botQuestion) {

		Optional<BotQuestion> ofNullable = Optional.ofNullable(botQuestion);

		if (null == botQuestion || !ofNullable.isPresent()) {
			AllyBotResponseDto<BotQuestion> botQuestionRes = new AllyBotResponseDto<>();
			String errorMessage = "Something wen't wrong exist";
			String msg = "";
			noContent(botQuestionRes, errorMessage, msg);
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(botQuestionRes);
		}
		BotQuestion botQuestionResponse = botQuestionService.updateBotQuestion(botQuestion);
		BotQuestionDto botQuestionDto = new BotQuestionDto();
		botQuestionDto.setQuestion(botQuestion.getQuestion());
		botQuestionDto.setClientId(botQuestion.getClientId());
		botQuestionDto.setHotelId(botQuestion.getHotelId());

		AllyBotResponseDto<BotQuestion> botQuestionRes = new AllyBotResponseDto<>();
		botQuestionRes.setData(botQuestionResponse);
		botQuestionRes.setServiceStatus(ServiceStatus.SUCCESS);
		botQuestionRes.setTimestamp(new Timestamp(System.currentTimeMillis()));
		botQuestionRes.setTraceId(Long.MAX_VALUE);
		botQuestionRes.setMsg("Bot question has been updated successfully");
		return ResponseEntity.status(HttpStatus.CREATED).body(botQuestionDto);
	}

	@GetMapping("/{id}")
	private ResponseEntity<?> getBotQuestionById(@PathVariable("id") Long id) {

		BotQuestion questionById = botQuestionService.getBotQuestionById(id);
		if (questionById == null) {
			AllyBotResponseDto<BotQuestion> botQuestionRes = new AllyBotResponseDto<>();
			String errorMessage = "Something wen't wrong exist";
			String msg = "";
			noContent(botQuestionRes, errorMessage, msg);
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(botQuestionRes);
		}
		AllyBotResponseDto<BotQuestion> botQuestionRes = new AllyBotResponseDto<>();
		botQuestionRes.setData(questionById);
		botQuestionRes.setServiceStatus(ServiceStatus.SUCCESS);
		botQuestionRes.setTimestamp(new Timestamp(System.currentTimeMillis()));
		botQuestionRes.setTraceId(Long.MAX_VALUE);
		return ResponseEntity.status(HttpStatus.OK).body(botQuestionRes);
	}

	@PostMapping("/upload")
	public ResponseEntity<?> fileRederFun(@RequestParam("file") MultipartFile file) {

		List<BotQuestionAnswerFileDto> saveScvFile = null;
		
		String message = "";
		if (BotCsvHelper.hasCSVFormat(file)) {
			try {
				saveScvFile = botQuestionService.saveQAFile(file);
				List<BotAnswer> botAns = new ArrayList<>();
				Boolean temp;
				for (BotQuestionAnswerFileDto dto : saveScvFile) {
					BotQuestion botQuestion = new BotQuestion();
					botQuestion.setQuestion(dto.getQuestion());
					BotQuestion saveBotQuestions = botQuestionService.saveBotQuestions(botQuestion);

					temp = dto.getAns1() != null ? botAns.add(new BotAnswer(dto.getAns1(), saveBotQuestions.getId()))
							: null;
					temp = dto.getAns2() != null ? botAns.add(new BotAnswer(dto.getAns1(), saveBotQuestions.getId()))
							: null;
					temp = dto.getAns3() != null ? botAns.add(new BotAnswer(dto.getAns1(), saveBotQuestions.getId()))
							: null;
					temp = dto.getAns4() != null ? botAns.add(new BotAnswer(dto.getAns1(), saveBotQuestions.getId()))
							: null;
					temp = dto.getAns5() != null ? botAns.add(new BotAnswer(dto.getAns1(), saveBotQuestions.getId()))
							: null;
					temp = dto.getAns6() != null ? botAns.add(new BotAnswer(dto.getAns1(), saveBotQuestions.getId()))
							: null;
					temp = dto.getAns7() != null ? botAns.add(new BotAnswer(dto.getAns1(), saveBotQuestions.getId()))
							: null;
					temp = dto.getAns8() != null ? botAns.add(new BotAnswer(dto.getAns1(), saveBotQuestions.getId()))
							: null;
					temp = dto.getAns9() != null ? botAns.add(new BotAnswer(dto.getAns1(), saveBotQuestions.getId()))
							: null;
					temp = dto.getAns10() != null ? botAns.add(new BotAnswer(dto.getAns1(), saveBotQuestions.getId()))
							: null;
					botAnswerService.saveAllAnswer(botAns);
				}
				message = "Uploaded the file successfully: " + file.getOriginalFilename();
				return ResponseEntity.status(HttpStatus.OK).body(saveScvFile);
			} catch (Exception e) {
				message = "Could not upload the file: " + file.getOriginalFilename() + "!";
				return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(message);
			}
		}
		message = "Please upload a csv file!";
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(message);
	}

	private void noContent(AllyBotResponseDto<BotQuestion> bot, String errorMessage, String msg) {
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
