package com.technojade.allybot.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.technojade.allybot.entity.Designation;
import com.technojade.allybot.repository.DesignationRepository;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class DesignationServiceImpl implements DesignationService{

	@Autowired
	private DesignationRepository designationRepository;
	
	@Override
	public List<Designation> listOfDesignation() {
		return designationRepository.findAll();
	}

	@Override
	public Designation findById(Long designationId) {

		Optional<Designation> desingntgionOpt = designationRepository.findById(designationId);
		
		if(desingntgionOpt.isPresent())
			return desingntgionOpt.get();
		
		return null;
	}

}
