package com.technojade.allybot.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Getter
@Setter
public class HotelReturnDto {

	@JsonProperty(value = "hotelName")
	private String name;
	private String location;
	private String rating;
	private String carausel_image;
	
	
}
