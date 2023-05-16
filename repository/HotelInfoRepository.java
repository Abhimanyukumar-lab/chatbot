package com.technojade.allybot.repository;

import java.util.ArrayList;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.technojade.allybot.enpoint.response.QuickQueryDto;
import com.technojade.allybot.entity.HotelInfo;
import com.technojade.allybot.entity.BookedRoomInfo;
import com.technojade.allybot.entity.UserBookingDetail;

@Repository
public interface HotelInfoRepository extends JpaRepository<HotelInfo, Long>{

	 @Query(value="select ht.hotel_id hotelInfoId, ht.carausel_image carausel_image, ht.name hotelName,ht.rating rating,ad.address_1 location ,ubi.booking_id bookingId ,ubi.checking_date cheakingDate ,\r\n"
		 		+ "ubi.checkout_date checkoutDate,ubi.proof_id proofId,ubi.proof_name proofName,\r\n"
		 		+ "ubi.number_of_room noOfRoom\r\n"
		 		+ "from address As ad \r\n"
		 		+ "join hotel_mst As ht \r\n"
		 		+ "on ad.address_id = ht.address_id\r\n"
		 		+ "join user_booking_info ubi \r\n"
		 		+ "on ht.hotel_id = ubi.hotel_id\r\n"
		 		+ "where ht.hotel_id=:id" , nativeQuery = true)
			public ArrayList<QuickQueryDto> getQueryInfo(@Param("id") long id);
		 @Query(value="from UserBookingDetail where bookingId =:id" , nativeQuery = false)
				public ArrayList<UserBookingDetail> getBookingDetails(@Param("id") long id);
		 
		 @Query(value="from BookedRoomInfo rmif where rmif.bookingId =:id" , nativeQuery = false)
					public ArrayList<BookedRoomInfo> getRoomDetails(@Param("id") Long id);
		 
		 @Query(value="select ht.hotel_id hotelInfoId, ht.carausel_image carausel_image, ht.name hotelName,ht.rating rating,ad.address_1 location ,ubi.booking_id bookingId ,ubi.checking_date cheakingDate ,\r\n"
			 		+ "ubi.checkout_date checkoutDate,ubi.proof_id proofId,ubi.proof_name proofName,\r\n"
			 		+ "ubi.number_of_room noOfRoom\r\n"
			 		+ "from address As ad \r\n"
			 		+ "join hotel_mst As ht \r\n"
			 		+ "on ad.address_id = ht.address_id\r\n"
			 		+ "join user_booking_info ubi \r\n"
			 		+ "on ht.hotel_id = ubi.hotel_id\r\n"
			 		+ "where ubi.user_id=:id" , nativeQuery = true)
				public ArrayList<QuickQueryDto> getQueryInfoByUserId(@Param("id") long id);
}
