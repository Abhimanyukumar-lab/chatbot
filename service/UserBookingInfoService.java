package com.technojade.allybot.service;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.domain.Page;

import com.technojade.allybot.entity.UserBookingInfo;

public interface UserBookingInfoService {

	public UserBookingInfo saveUserBookingInfo(UserBookingInfo userBookingInfo);
	
	public UserBookingInfo findByUserId(Long uId);
	
	public List<UserBookingInfo> findByUserIdWithToken(Long id);

	public void deleteById(Long id);
	
	public UserBookingInfo findByUserIdAndCheckingDateAndCheckoutDateAndHotelId(Long id, LocalDateTime chknDate, LocalDateTime chktDate, Long hId);
	
	public Page<UserBookingInfo> findListOfUserBooking(Long hId, int page, int size);
	
	public UserBookingInfo updateUserBookingInfo(Long bookingId, LocalDateTime checkingDate, LocalDateTime checkoutDate);
	
	public List<UserBookingInfo> findBookingByOnlineId(Long oId);
	
	public void deleteUserBookingInfoByOnlineBID(Long onlineBID);
}