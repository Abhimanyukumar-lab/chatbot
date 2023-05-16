package com.technojade.allybot.service;

import java.util.List;

import com.technojade.allybot.entity.State;

public interface StateService {

	
	public List<State> listOfState();
	public State stateById(Long id);
	
}
