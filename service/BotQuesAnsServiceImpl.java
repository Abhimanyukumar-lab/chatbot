package com.technojade.allybot.service;

import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.technojade.allybot.entity.BotQuestionAnswer;
import com.technojade.allybot.repository.BotQuestionAnsRepository;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class BotQuesAnsServiceImpl implements BotQuestionAnsService {

	@Autowired
	private BotQuestionAnsRepository botQuesAnsRepo;

	@Value("${file.upload.url.script}")
	private String uploadUrl;

	String randomFileName = null;

	@Override
	public BotQuestionAnswer saveScript(BotQuestionAnswer botAnswer, MultipartFile file) throws IOException {
		// TODO Auto-generated method stub
		randomFileName = "TJ-" + String.valueOf(new Random().nextInt(1000)) + "-";
		botAnswer.setFileName(randomFileName + file.getOriginalFilename());
		file.transferTo(new File(uploadUrl.concat("/").concat(randomFileName + file.getOriginalFilename())));
		BotQuestionAnswer questionAnswer = botQuesAnsRepo.save(botAnswer);
		return questionAnswer;
	}

	@Override
	public BotQuestionAnswer updateScript(BotQuestionAnswer botAnswer, MultipartFile file)
			throws IllegalStateException, IOException {
		// TODO Auto-generated method stub
		randomFileName = "TJ-" + String.valueOf(new Random().nextInt(1000)) + "-";
		Optional<BotQuestionAnswer> findById = botQuesAnsRepo.findById(botAnswer.getSId());

		try {
			File file2 = new File(uploadUrl.concat("/" + findById.get().getFileName()));
			if (file2.delete()) {
				log.info("File Deleted...");
			} else {
				log.info("Not deleted...");
			}
		} catch (Exception e) {
			// TODO: handle exception
			log.info("File cannot be deleted...");
		}
		botAnswer.setFileName(randomFileName + file.getOriginalFilename());
		file.transferTo(new File(uploadUrl.concat("/").concat(randomFileName + file.getOriginalFilename())));

		BotQuestionAnswer questionAnswer = botQuesAnsRepo.save(botAnswer);
		return questionAnswer;
	}

	@Override
	public void deleteScript(int id) {
		// TODO Auto-generated method stub

		Optional<BotQuestionAnswer> findById = botQuesAnsRepo.findById(id);

		try {
			File file2 = new File(uploadUrl.concat("/" + findById.get().getFileName()));
			if (file2.delete()) {
				log.info("File Deleted...");
			} else {
				log.info("Not deleted...");
			}
		} catch (Exception e) {
			// TODO: handle exception
			log.info("File cannot be deleted...");
		}
		botQuesAnsRepo.deleteById(id);
	}

	@Override
	public List<BotQuestionAnswer> listOfScript(Long hId) {
		// TODO Auto-generated method stub
		List<BotQuestionAnswer> list = botQuesAnsRepo.findAllByHotelId(hId);
		return list;
	}

	@Override
	public BotQuestionAnswer scriptById(int id) {
		// TODO Auto-generated method stub
		Optional<BotQuestionAnswer> findById = botQuesAnsRepo.findById(id);
		if (findById.isPresent()) {
			return findById.get();
		}
		return null;
	}

	@Override
	public Page<BotQuestionAnswer> listOfScriptWithPagination(Long hId, int page, int size) {
		// TODO Auto-generated method stub
		Pageable pageable = PageRequest.of(page, size, Sort.by("sId").descending());
		Page<BotQuestionAnswer> page2 = botQuesAnsRepo.findAllByHotelId(hId, pageable);
		return page2;
	}

}
