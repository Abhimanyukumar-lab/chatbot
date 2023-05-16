package com.technojade.allybot.dtos;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class HistoryDto {

	private Long userId;
	private String charJsonStr;
	private Long superwiserId;
	
}
