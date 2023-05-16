package com.technojade.allybot.service;

import java.util.List;

import com.technojade.allybot.entity.UserBookingDetail;

public interface UserBookingDetailService {


	public UserBookingDetail saveUserBookingDetail(UserBookingDetail userBookingDetail);

	public List<UserBookingDetail> saveUserBookingDetailList(List<UserBookingDetail> userBookingDetail);

	public List<UserBookingDetail> findByMobileOrOnlineBookingId(String mobile, Long onlineId );

	public List<UserBookingDetail> findListOfBookingDetail(Long bId);
	
	public void deleteUserBooking(Long onlineBId);
	
}

