package com.technojade.allybot.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.technojade.allybot.entity.History;

@Repository
public interface HistoryRepository extends JpaRepository<History, Long>{

	public Optional<List<History>> findAllByUserId(Long id);
	public Optional<List<History>> findAllBySuperwiserId(Long id);
	
}
