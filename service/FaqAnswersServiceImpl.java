package com.technojade.allybot.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.technojade.allybot.dtos.FaqQuestionAnswerDto;
import com.technojade.allybot.entity.FaqAnswers;
import com.technojade.allybot.entity.FaqMst;
import com.technojade.allybot.repository.FaqAnswersRepository;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class FaqAnswersServiceImpl implements FaqAnswersService {

	@Autowired
	private FaqAnswersRepository faqAnswersRepository;
	
	@Autowired
	private FaqService FaqService;

	@Override
	public FaqAnswers saveFaqAnswer(FaqAnswers faqAnswers) {
		faqAnswersRepository.save(faqAnswers);
		return faqAnswers;
	}

	@Override
	public List<FaqAnswers> listOfFaqAnswer() {
		return faqAnswersRepository.findAll();
	}

	@Override
	public Optional<FaqAnswers> getByFaqAnswerId(Long id) {
		return faqAnswersRepository.findById(id);
	}

	@Override
	public FaqQuestionAnswerDto getByFaqId(Long id) {
		
		Optional<FaqMst> faqById = FaqService.getFaqById(id);
		if(faqById.isPresent()) {
			FaqQuestionAnswerDto faqQuestionAnswerDto = new FaqQuestionAnswerDto();
			faqQuestionAnswerDto.setId(id);
			faqQuestionAnswerDto.setQuestion(faqById.get().getQuestion());
			faqQuestionAnswerDto.setAnswers(faqAnswersRepository.findAllByFaqId(id));
			return faqQuestionAnswerDto;
		}
		return null;
	}

	@Override
	public FaqAnswers updateFaqAnswer(FaqAnswers faqAnswers) {
		faqAnswersRepository.save(faqAnswers);
		return faqAnswers;
	}

}
