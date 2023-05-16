package com.technojade.allybot.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BotAnswerDto {

	private String answer;
	private Long botQid;
	private Long clientId;
	private Long hotelId;
	
}
