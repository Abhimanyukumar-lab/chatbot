package com.technojade.allybot.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.technojade.allybot.entity.BotQuestion;

@Repository
public interface BotQuestionRepository extends JpaRepository<BotQuestion, Long>{

}
