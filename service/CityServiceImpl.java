package com.technojade.allybot.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import com.technojade.allybot.entity.City;
import com.technojade.allybot.repository.CityRepository;

@Service
public class CityServiceImpl implements CityService {

	@Autowired
	private CityRepository cityRepository;

	@Override
	public List<City> listOfCity() {
		return cityRepository.findAll();
	}

	@Override
	public List<City> listOfCityByState(Long id) {
		return cityRepository.findAllByStateId(id);
	}

	@Override
	public City cityById(Long id) {
		return cityRepository.findById(id).get();
	}

}
