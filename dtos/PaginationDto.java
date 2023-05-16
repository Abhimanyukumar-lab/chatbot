package com.technojade.allybot.dtos;

import java.sql.Timestamp;
import java.util.List;

import com.technojade.allybot.enpoint.response.AllyBotResponseDto;
import com.technojade.allybot.enpoint.response.ApiError;
import com.technojade.allybot.enpoint.response.ServiceStatus;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PaginationDto<T> {

	private T data;
	private Long totalPage;
	private Long totalElement;
	
}
