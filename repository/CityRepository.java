package com.technojade.allybot.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.technojade.allybot.entity.City;


@Repository
public interface CityRepository extends JpaRepository<City, Long>{

	public List<City> findAllByStateId(Long id);
	
}
