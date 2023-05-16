package com.technojade.allybot.service;

import java.util.List;
import java.util.Optional;

import com.technojade.allybot.entity.History;

public interface HistoryService {

	public History saveHistory(History history);

	public Optional<List<History>> getHistoryByUserId(Long id);

	public Optional<List<History>> getHistoryBySuperwiserId(Long id);

}
