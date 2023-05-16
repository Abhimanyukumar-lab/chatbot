package com.technojade.allybot.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.technojade.allybot.entity.State;
import com.technojade.allybot.repository.StateRepository;

@Service
public class StateServiceImpl implements StateService{

	@Autowired
	private StateRepository stateRepository;
	
	@Override
	public List<State> listOfState() {
		return stateRepository.findAll();
	}

	@Override
	public State stateById(Long id) {
		return stateRepository.findById(id).get();
	}

}
