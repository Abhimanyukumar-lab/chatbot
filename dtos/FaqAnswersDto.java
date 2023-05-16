package com.technojade.allybot.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class FaqAnswersDto {

	private Long faqId;
	private String answers;
	private Long clientId;
	private Long hotelId;
	
}
