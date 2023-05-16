package com.technojade.allybot.service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.technojade.allybot.entity.User;
import com.technojade.allybot.entity.UserBookingInfo;
import com.technojade.allybot.repository.UserBookingInfoRepository;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class UserBookingInfoImpl implements UserBookingInfoService {

	@Autowired
	private UserBookingInfoRepository userBookingInfoRepository;

	@Autowired
	private OtpService otpService;

	@Autowired
	private UserService usrService;

	@Override
	public UserBookingInfo saveUserBookingInfo(UserBookingInfo userBookingInfo) {
		userBookingInfoRepository.save(userBookingInfo);
		return userBookingInfo;
	}

	/*
	 * @Scheduled(cron = "0 0 0 * * *") public void
	 * scheduleFixedRateWithInitialDelayTask() {
	 * 
	 * List<UserBookingInfo> findAll = userBookingInfoRepository.findAll();
	 * 
	 * findAll.stream().filter(e ->
	 * e.getCheckoutDate().isBefore(LocalDateTime.now()) && e.getToken()).forEach(e
	 * -> { e.setToken(false); userBookingInfoRepository.save(e); }); }
	 */

	@Override
	public UserBookingInfo findByUserId(Long id) {
		// TODO Auto-generated method stub
		UserBookingInfo findById = userBookingInfoRepository.findByUserId(id);
		return findById;
	}

	@Override
	public void deleteById(Long id) {
		// TODO Auto-generated method stub
		userBookingInfoRepository.deleteById(id);
	}

	@Override
	public UserBookingInfo findByUserIdAndCheckingDateAndCheckoutDateAndHotelId(Long id, LocalDateTime chknDate,
			LocalDateTime chktDate, Long hId) {
		UserBookingInfo info = userBookingInfoRepository.findByUserIdAndCheckingDateAndCheckoutDateAndHotelId(id,
				chknDate, chktDate, hId);
		return info;
	}

	@Override
	public List<UserBookingInfo> findByUserIdWithToken(Long id) {
		List<UserBookingInfo> findById = userBookingInfoRepository.findAllByUserId(id);
		return findById;
	}

	@Override
	public Page<UserBookingInfo> findListOfUserBooking(Long hId, int page, int size) {
		Pageable pageable = PageRequest.of(page, size);
		Page<UserBookingInfo> findAll = userBookingInfoRepository.findAllByHotelIdAndToken(hId, true, pageable);
		return findAll;
	}

	@Override
	public UserBookingInfo updateUserBookingInfo(Long bookingId, LocalDateTime checkingDate,
			LocalDateTime checkoutDate) {
		// TODO Auto-generated method stub
		UserBookingInfo userBookingInfo = userBookingInfoRepository.findById(bookingId).get();
		userBookingInfo.setCheckingDate(checkingDate);
		userBookingInfo.setCheckoutDate(checkoutDate);
		userBookingInfo.setUpdatedOn(LocalDateTime.now());

		UserBookingInfo bookingInfo = userBookingInfoRepository.save(userBookingInfo);
		User findByUserID = usrService.findByUserID(userBookingInfo.getUserId());

		String otp = otpService.sendOtpOnlyWithCheckoutDate(findByUserID.getMobileNumber(),
				userBookingInfo.getCheckoutDate());
		if (otp != null) {
			log.info("Otp sent successfully");
		}

		return bookingInfo;
	}

	@Override
	public List<UserBookingInfo> findBookingByOnlineId(Long oId) {
		// TODO Auto-generated method stub
		List<UserBookingInfo> userBookingInfo = userBookingInfoRepository.findByOnlineBookingId(oId);
		return userBookingInfo;
	}

	@Override
	public void deleteUserBookingInfoByOnlineBID(Long onlineBID) {
		// TODO Auto-generated method stub
		userBookingInfoRepository.deleteAllByOnlineBookingId(onlineBID);
		log.info("UserBookingInfo deleted...");
	}

}