package com.technojade.allybot.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.technojade.allybot.dtos.BotQuestionAnswerDto;
import com.technojade.allybot.entity.BotAnswer;
import com.technojade.allybot.entity.BotQuestion;
import com.technojade.allybot.repository.BotAnswerRepository;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class BotAnswerServiceImpl implements BotAnswerService {

	@Autowired
	private BotQuestionService botQuestionService;

	@Autowired
	private BotAnswerRepository botAnswerRepository;

	@Override
	public BotAnswer saveBotAnswer(BotAnswer botAnswer) {
		botAnswerRepository.save(botAnswer);
		return botAnswer;
	}

	@Override
	public List<BotAnswer> listOfBotAnswer() {
		return botAnswerRepository.findAll();
	}

	@Override
	public BotAnswer getByBotAnswerId(Long id) {
		Optional<BotAnswer> findById = botAnswerRepository.findById(id);
		if (findById.isPresent()) {
			return findById.get();
		}
		return null;
	}

	@Override
	public BotQuestionAnswerDto getByBotQuestionId(Long id) {
		// TODO Auto-generated method stub
		BotQuestion questionById = botQuestionService.getBotQuestionById(id);

		Optional<BotQuestion> ofNullable = Optional.ofNullable(questionById);
		if (ofNullable.isPresent()) {
			BotQuestionAnswerDto qADto = new BotQuestionAnswerDto();
			qADto.setId(id);
			qADto.setQuestion(questionById.getQuestion());
			List<BotAnswer> allByBotQid = botAnswerRepository.findAllByBotQid(id);
			qADto.setAnswers(allByBotQid);
			return qADto;
		}

		return null;
	}

	@Override
	public BotAnswer updateBotAnswer(BotAnswer botAnswers) {
		botAnswerRepository.save(botAnswers);
		return botAnswers;
	}

	@Override
	public List<BotAnswer> saveAllAnswer(List<BotAnswer> botAnswers) {
		botAnswerRepository.saveAll(botAnswers);
		return botAnswers;
	}

}
