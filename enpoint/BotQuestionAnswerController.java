package com.technojade.allybot.enpoint;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.technojade.allybot.entity.BotQuestionAnswer;
import com.technojade.allybot.service.BotQuestionAnsService;

@RestController
@RequestMapping("/botscript")
public class BotQuestionAnswerController {

	@Autowired
	private BotQuestionAnsService botQuestionAnsService;

	@PostMapping("/upload/{hid}")
	public ResponseEntity<?> savingBotScript(@PathVariable("hid") Long hId,
			@RequestParam("scriptName") String scriptName, @RequestParam("clientId") String cId,
			@RequestParam("scriptFile") MultipartFile file) {

		BotQuestionAnswer questionAnswer = new BotQuestionAnswer();

		questionAnswer.setScriptName(scriptName);
		questionAnswer.setFileName(file.getOriginalFilename());
		questionAnswer.setClientId(cId);
		questionAnswer.setCreatedAt(LocalDateTime.now());
		questionAnswer.setHotelId(hId);

		try {
			botQuestionAnsService.saveScript(questionAnswer, file);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Script Uploading Failed...");
		}

		return ResponseEntity.status(HttpStatus.OK).body(questionAnswer);
	}

	@PutMapping("/update/{hid}")
	public ResponseEntity<?> updateScript(@PathVariable("hid") Long hId, @RequestParam("scriptId") int sId,
			@RequestParam("scriptName") String scriptName, @RequestParam("clientId") String cId,
			@RequestParam("scriptFile") MultipartFile file) {

		BotQuestionAnswer questionAnswer = new BotQuestionAnswer();
		questionAnswer.setSId(sId);
		questionAnswer.setScriptName(scriptName);
		questionAnswer.setFileName(file.getOriginalFilename());
		questionAnswer.setClientId(cId);
		questionAnswer.setCreatedAt(LocalDateTime.now());
		questionAnswer.setHotelId(hId);

		try {
			botQuestionAnsService.updateScript(questionAnswer, file);
		} catch (IllegalStateException | IOException e) {
			// TODO Auto-generated catch block
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Script updation failed...");
		}

		return ResponseEntity.status(HttpStatus.OK).body(questionAnswer);
	}

	@GetMapping("/scriptlist/{hid}")
	public ResponseEntity<?> listOfScript(@PathVariable("hid") Long hId) {
		List<BotQuestionAnswer> listOfScript = botQuestionAnsService.listOfScript(hId);
		return ResponseEntity.status(HttpStatus.OK).body(listOfScript);
	}

	@GetMapping("/list/{hid}")
	public ResponseEntity<?> listOfScriptWithPagination(@PathVariable("hid") Long hId, @RequestParam("page") int page,
			@RequestParam("size") int size) {
		Page<BotQuestionAnswer> listOfScriptWithPagination = botQuestionAnsService.listOfScriptWithPagination(hId,
				page - 1, size);
		return ResponseEntity.status(HttpStatus.OK).body(listOfScriptWithPagination);
	}

	@DeleteMapping("/delete/{id}")
	public ResponseEntity<?> deleteScriptUsingId(@PathVariable("id") int id) {
		botQuestionAnsService.deleteScript(id);
		return ResponseEntity.status(HttpStatus.OK).body("Script Deleted...");
	}

}
