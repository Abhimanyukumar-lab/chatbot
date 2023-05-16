package com.technojade.allybot.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.technojade.allybot.entity.History;
import com.technojade.allybot.repository.HistoryRepository;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class HistoryServiceImpl implements HistoryService {

	@Autowired
	private HistoryRepository historyRepository;

	@Override
	public History saveHistory(History history) {
		historyRepository.save(history);
		return history;
	}

	@Override
	public Optional<List<History>> getHistoryByUserId(Long id) {
		return historyRepository.findAllByUserId(id);
	}

	@Override
	public Optional<List<History>> getHistoryBySuperwiserId(Long id) {
		return historyRepository.findAllBySuperwiserId(id);
	}

}
