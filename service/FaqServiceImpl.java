package com.technojade.allybot.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.technojade.allybot.entity.FaqMst;
import com.technojade.allybot.repository.FaqRepository;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class FaqServiceImpl implements FaqService{

	@Autowired
	private FaqRepository faqMstRepository;
	
	@Override
	public FaqMst saveFaq(FaqMst faqMst) {
		FaqMst save = faqMstRepository.save(faqMst);
		return faqMst;
	}

	@Override
	public List<FaqMst> listOfFaq() {
		return faqMstRepository.findAll();
	}

	@Override
	public FaqMst updateFaq(FaqMst faqMst) {
		FaqMst save = faqMstRepository.save(faqMst);
		return faqMst;
	}

	@Override
	public Optional<FaqMst> getFaqById(Long id) {
		Optional<FaqMst> findById = faqMstRepository.findById(id);
		return findById;
	}

}
