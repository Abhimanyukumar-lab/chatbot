package com.technojade.allybot.service;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.technojade.allybot.dtos.BotQuestionAnswerFileDto;
import com.technojade.allybot.entity.BotQuestion;

public interface BotQuestionService {

	public BotQuestion saveBotQuestion(BotQuestion botQuestion);

	public BotQuestion saveBotQuestions(BotQuestion botQuestion);

	public List<BotQuestion> listOfBotQuestion();

	public BotQuestion updateBotQuestion(BotQuestion botQuestion);

	public BotQuestion getBotQuestionById(Long id);
	
	public List<BotQuestionAnswerFileDto> saveQAFile(MultipartFile file);
	
}
