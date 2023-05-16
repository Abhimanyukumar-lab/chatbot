package com.technojade.allybot.dtos;

import java.util.List;

import com.technojade.allybot.entity.BookedRoomInfo;
import com.technojade.allybot.entity.UserBookingDetail;
import com.technojade.allybot.entity.UserBookingInfo;

import lombok.Data;

@Data
public class HotelInfoDto {
	private UserBookingInfo userBookingInfo;
	private List<BookedRoomInfo> rommInfoList; 
	private List<UserBookingDetail> userBookingdetails;
}
  