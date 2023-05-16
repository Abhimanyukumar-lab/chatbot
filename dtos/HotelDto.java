/**
 * 
 */
package com.technojade.allybot.dtos;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author ugam.sharma
 *
 */
@Data
@NoArgsConstructor
public class HotelDto {

	private Long hotelId;
	
	private String hotelName;
	
	private String hotelDescription; 
	
	private String hotelAddress;
	
	private Double rating;
	
	private int numberOfRoom;
	
	private int numberOfBed;
	
	private int numberOfParking;
	
}
