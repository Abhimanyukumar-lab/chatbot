package com.technojade.allybot.service;

import java.util.List;
import java.util.Optional;

import com.technojade.allybot.dtos.BotQuestionAnswerDto;
import com.technojade.allybot.entity.BotAnswer;
import com.technojade.allybot.entity.FaqAnswers;

public interface BotAnswerService {

	public BotAnswer saveBotAnswer(BotAnswer botAnswer);
	public List<BotAnswer> saveAllAnswer(List<BotAnswer> botAnswers);
	public List<BotAnswer> listOfBotAnswer();
	public BotAnswer getByBotAnswerId(Long id);
	public BotQuestionAnswerDto getByBotQuestionId(Long id);
	public BotAnswer updateBotAnswer(BotAnswer botAnswers);
	
}
