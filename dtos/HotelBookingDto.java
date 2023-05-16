package com.technojade.allybot.dtos;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class HotelBookingDto {

	private Long bookingId;
	private Long onlineBookingId;
	private LocalDateTime checkingDate;
	private LocalDateTime checkoutDate;
	private LocalDateTime bookingDate;
	private String bookedBy;
	private String mobileNumber;
	
}
