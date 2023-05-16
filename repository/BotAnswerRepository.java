package com.technojade.allybot.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.technojade.allybot.entity.BotAnswer;
import com.technojade.allybot.entity.FaqAnswers;

@Repository
public interface BotAnswerRepository extends JpaRepository<BotAnswer, Long>{

	public List<BotAnswer> findAllByBotQid(Long id);
	
}
