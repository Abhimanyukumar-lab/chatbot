package com.technojade.allybot.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GroupDto {

	
	private String name;
	private Long groupTypeId;
	private String description;
	
	
}
