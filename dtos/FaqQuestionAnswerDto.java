package com.technojade.allybot.dtos;

import java.util.List;

import com.technojade.allybot.entity.FaqAnswers;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class FaqQuestionAnswerDto {

	private Long id;
	private String question;
	private List<FaqAnswers> answers;
	
}
