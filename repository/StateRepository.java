package com.technojade.allybot.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.technojade.allybot.entity.State;

@Repository
public interface StateRepository extends JpaRepository<State, Long> {

	
	
	
	
}
