package com.technojade.allybot.service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.springframework.web.multipart.MultipartFile;

import com.technojade.allybot.dtos.BotQuestionAnswerFileDto;

public class BotCsvHelper {

	public static String TYPE = "text/csv";

	public static boolean hasCSVFormat(MultipartFile file) {
		if (!TYPE.equals(file.getContentType())) {
			return false;
		}
		return true;
	}

	public static List<BotQuestionAnswerFileDto> csvToBot(InputStream is) {
		try (BufferedReader fileReader = new BufferedReader(new InputStreamReader(is, "UTF-8"));
				CSVParser csvParser = new CSVParser(fileReader,
						CSVFormat.DEFAULT.withFirstRecordAsHeader().withIgnoreHeaderCase().withTrim());) {

			List<BotQuestionAnswerFileDto> fileDto = new ArrayList<BotQuestionAnswerFileDto>();
			Iterable<CSVRecord> csvRecords = csvParser.getRecords();

			for (CSVRecord csvRecord : csvRecords) {
				BotQuestionAnswerFileDto qAfileDto = new BotQuestionAnswerFileDto(csvRecord.get("question"),
						csvRecord.get("ans1"), csvRecord.get("ans2"), csvRecord.get("ans3"), csvRecord.get("ans4"),
						csvRecord.get("ans5"), csvRecord.get("ans6"), csvRecord.get("ans7"), csvRecord.get("ans8"),
						csvRecord.get("ans9"), csvRecord.get("ans10"));

				fileDto.add(qAfileDto);
			}
			return fileDto;
		} catch (IOException e) {
			throw new RuntimeException("fail to parse CSV file: " + e.getMessage());
		}
	}

}
