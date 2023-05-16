package com.technojade.allybot.service;

import java.util.List;
import java.util.Optional;

import com.technojade.allybot.dtos.FaqQuestionAnswerDto;
import com.technojade.allybot.entity.FaqAnswers;

public interface FaqAnswersService {

	public FaqAnswers saveFaqAnswer(FaqAnswers faqAnswers);
	public List<FaqAnswers> listOfFaqAnswer();
	public Optional<FaqAnswers> getByFaqAnswerId(Long id);
	public FaqQuestionAnswerDto getByFaqId(Long id);
	public FaqAnswers updateFaqAnswer(FaqAnswers faqAnswers);
	
}
