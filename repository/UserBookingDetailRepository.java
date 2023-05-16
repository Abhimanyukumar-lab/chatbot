package com.technojade.allybot.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.technojade.allybot.entity.UserBookingDetail;

@Repository
public interface UserBookingDetailRepository extends JpaRepository<UserBookingDetail, Long>{


	public List<UserBookingDetail> findByMobileNumberOrOnlineBookingId(String mobile, Long oId);

	public List<UserBookingDetail> findByBookingId(Long bId);

	public void deleteAllByOnlineBookingId(Long onlineId);
	
}