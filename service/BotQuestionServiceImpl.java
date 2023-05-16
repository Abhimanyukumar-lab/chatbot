package com.technojade.allybot.service;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.technojade.allybot.dtos.BotQuestionAnswerFileDto;
import com.technojade.allybot.entity.BotQuestion;
import com.technojade.allybot.repository.BotQuestionRepository;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class BotQuestionServiceImpl implements BotQuestionService {

	@Autowired
	private BotQuestionRepository botQuestionRepository;

	@Override
	public BotQuestion saveBotQuestions(BotQuestion botQuestion) {
		BotQuestion save = botQuestionRepository.save(botQuestion);
		return save;
	}

	public BotQuestion saveBotQuestion(BotQuestion botQuestion) {
		botQuestionRepository.save(botQuestion);
		return botQuestion;
	}

	@Override
	public List<BotQuestion> listOfBotQuestion() {
		return botQuestionRepository.findAll();
	}

	@Override
	public BotQuestion updateBotQuestion(BotQuestion botQuestion) {
		return botQuestion;
	}

	@Override
	public BotQuestion getBotQuestionById(Long id) {
		Optional<BotQuestion> byId = botQuestionRepository.findById(id);
		if (byId.isPresent()) {
			return byId.get();
		}
		return null;
	}

	public List<BotQuestionAnswerFileDto> saveQAFile(MultipartFile multipartFile) {

		List<BotQuestionAnswerFileDto> csvToBot = null;
		try {
			csvToBot = BotCsvHelper.csvToBot(multipartFile.getInputStream());
		} catch (IOException e) {
			throw new RuntimeException("fail to store csv data: " + e.getMessage());
		}

		return csvToBot;
	}

}
