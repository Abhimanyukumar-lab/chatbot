package com.technojade.allybot.service;

import java.io.IOException;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.web.multipart.MultipartFile;

import com.technojade.allybot.entity.BotQuestionAnswer;

public interface BotQuestionAnsService {

	public BotQuestionAnswer saveScript(BotQuestionAnswer botAnswer, MultipartFile file) throws IOException;
	
	public BotQuestionAnswer updateScript(BotQuestionAnswer botAnswer, MultipartFile file) throws IllegalStateException, IOException;
	
	public void deleteScript(int id);
	
	public List<BotQuestionAnswer> listOfScript(Long hId);

	public Page<BotQuestionAnswer> listOfScriptWithPagination(Long hId, int page, int size);
	
	public BotQuestionAnswer scriptById(int id);
	
}
