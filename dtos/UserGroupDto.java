package com.technojade.allybot.dtos;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserGroupDto {

	private Long groupId;
	
	private List<Long> empIds;
	
	private Long hotelId;
	
	private Long clientId;
	
}
