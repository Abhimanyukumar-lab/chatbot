package com.technojade.allybot.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.technojade.allybot.entity.FaqAnswers;

@Repository
public interface FaqAnswersRepository extends JpaRepository<FaqAnswers, Long>{

	public List<FaqAnswers> findAllByFaqId(Long id);
	
}
