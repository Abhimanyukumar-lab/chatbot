package com.technojade.allybot.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.technojade.allybot.entity.BotQuestionAnswer;

@Repository
public interface BotQuestionAnsRepository extends JpaRepository<BotQuestionAnswer, Integer>{

	public List<BotQuestionAnswer> findAllByHotelId(Long hId);

	public Page<BotQuestionAnswer> findAllByHotelId(Long hId, Pageable pageable);
	
}
