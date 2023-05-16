package com.technojade.allybot.service;

import java.util.List;
import java.util.Optional;

import com.technojade.allybot.entity.FaqMst;

public interface FaqService {

	public FaqMst saveFaq(FaqMst faqMst);

	public List<FaqMst> listOfFaq();

	public FaqMst updateFaq(FaqMst faqMst);

	public Optional<FaqMst> getFaqById(Long id);
}
