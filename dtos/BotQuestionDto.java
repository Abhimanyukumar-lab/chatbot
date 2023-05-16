package com.technojade.allybot.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BotQuestionDto {

	private String question;
	private Long hotelId;
	private Long clientId;
	
}
