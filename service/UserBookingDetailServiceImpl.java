package com.technojade.allybot.service;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.technojade.allybot.entity.UserBookingDetail;
import com.technojade.allybot.repository.UserBookingDetailRepository;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class UserBookingDetailServiceImpl implements UserBookingDetailService {

	@Autowired
	private UserBookingDetailRepository userBookingDetailRepository;

	@Autowired
	private RoomInfoService roomInfoService;

	@Autowired
	private UserBookingInfoService userBookingInfoService;

	@Override
	public UserBookingDetail saveUserBookingDetail(UserBookingDetail userBookingDetail) {
		userBookingDetailRepository.save(userBookingDetail);
		return userBookingDetail;
	}

	@Override
	public List<UserBookingDetail> saveUserBookingDetailList(List<UserBookingDetail> userBookingDetail) {
		// TODO Auto-generated method stub
		userBookingDetailRepository.saveAll(userBookingDetail);
		return userBookingDetail;
	}

	@Override
	public List<UserBookingDetail> findByMobileOrOnlineBookingId(String mobile, Long onlineId) {
		// TODO Auto-generated method stub
		List<UserBookingDetail> numberOrOnlineBookingId = userBookingDetailRepository
				.findByMobileNumberOrOnlineBookingId(mobile, onlineId);
		return numberOrOnlineBookingId;
	}

	@Override
	public List<UserBookingDetail> findListOfBookingDetail(Long bId) {
		// TODO Auto-generated method stub
		List<UserBookingDetail> bookingId = userBookingDetailRepository.findByBookingId(bId);
		return bookingId;
	}

	@Override
	@Transactional
	public void deleteUserBooking(Long onlineBId) {
		// TODO Auto-generated method stub
		userBookingInfoService.deleteUserBookingInfoByOnlineBID(onlineBId);
		roomInfoService.deleteRoomInfoByOnlineBId(onlineBId);
		userBookingDetailRepository.deleteAllByOnlineBookingId(onlineBId);
		log.info("User Booking Detail info deleted...");
	}

}