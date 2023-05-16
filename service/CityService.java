package com.technojade.allybot.service;

import java.util.List;

import com.technojade.allybot.entity.City;

public interface CityService {

	public List<City> listOfCity();
	public List<City> listOfCityByState(Long id);
	public City cityById(Long id);
	
}
