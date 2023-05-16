package com.technojade.allybot.dtos;

import java.util.List;

import com.technojade.allybot.entity.BotAnswer;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BotQuestionAnswerDto {

	private Long id;
	private String question;
	private List<BotAnswer> answers;
	
}
