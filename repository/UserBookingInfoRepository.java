package com.technojade.allybot.repository;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.technojade.allybot.entity.UserBookingInfo;

@Repository
public interface UserBookingInfoRepository extends JpaRepository<UserBookingInfo, Long> {

	public UserBookingInfo findByUserId(Long uId);

	public List<UserBookingInfo> findAllByUserId(Long uId);

	public UserBookingInfo findByUserIdAndCheckingDateAndCheckoutDateAndHotelId(Long id, LocalDateTime chknDate,
			LocalDateTime chktDate, Long hId);

	public Page<UserBookingInfo> findAllByHotelIdAndToken(Long hId, boolean token, Pageable pageable);

	public List<UserBookingInfo> findByOnlineBookingId(Long oId);
	
	public void deleteAllByOnlineBookingId(Long onlineId);

}