package com.technojade.allybot.service;

import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.technojade.allybot.enpoint.response.HotelDtoResponce;
import com.technojade.allybot.enpoint.response.HotelQueryByUserResponce;
import com.technojade.allybot.enpoint.response.HotelResponse;
import com.technojade.allybot.enpoint.response.HotelResponseByUser;
import com.technojade.allybot.enpoint.response.QuickQueryDto;
import com.technojade.allybot.enpoint.response.QuickQueryResponse;
import com.technojade.allybot.entity.HotelInfo;
import com.technojade.allybot.entity.BookedRoomInfo;
import com.technojade.allybot.entity.UserBookingDetail;
import com.technojade.allybot.repository.HotelInfoRepository;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class HotelInfoService {

	@Autowired
	HotelInfoRepository hotelRepository;

	@SuppressWarnings({ "unused", "unused" })
	public HotelDtoResponce findByHotelId(Long hotelId) throws Exception {
		HotelDtoResponce hotelResponse = new HotelDtoResponce();
		HotelResponse hoteldetails = new HotelResponse();
		QuickQueryResponse response = new QuickQueryResponse();
		try {
			log.info("service layer");
			ArrayList<QuickQueryDto> quickList = hotelRepository.getQueryInfo(hotelId);
			for (QuickQueryDto quk : quickList) {
				response.setCheakingDate(quk.getCheakingDate());
				response.setCheckoutDate(quk.getCheckoutDate());
				hoteldetails.setHotelName(quk.getHotelname());
				hoteldetails.setRating(quk.getRating());
				hoteldetails.setLocation(quk.getLocation());
				response.setNoOfRoom(quk.getNoOfRoom());
				response.setProofId(quk.getProofId());
				response.setProofName(quk.getProofName());
				response.setBookingId(quk.getBookingId());
				hoteldetails.setHotelId(quk.getHotelInfoId());
				hoteldetails.setCarausel_image(quk.getCarausel_image());
			}
			ArrayList<UserBookingDetail> bookingDetails = hotelRepository.getBookingDetails(response.getBookingId());

			ArrayList<UserBookingDetail> bookingDetailsInfo = new ArrayList<UserBookingDetail>();
			for (int counter = 0; counter < bookingDetails.size(); counter++) {

				UserBookingDetail booking = new UserBookingDetail();
				String firstName = bookingDetails.get(counter).getFirstName();
				String lastName = bookingDetails.get(counter).getLastName();
				String gender = bookingDetails.get(counter).getGender();

				long bookingId = bookingDetails.get(counter).getBookingId();
				int age = bookingDetails.get(counter).getAge();
				booking.setFirstName(firstName);
				booking.setLastName(lastName);
				booking.setGender(gender);

				booking.setAge(age);
				bookingDetailsInfo.add(booking);
			}
			response.setTraveller(bookingDetailsInfo);
			ArrayList<BookedRoomInfo> roominfoList = hotelRepository.getRoomDetails(response.getBookingId());
			ArrayList<BookedRoomInfo> roomList = new ArrayList<BookedRoomInfo>();
			for (int i = 0; i < roominfoList.size(); i++) {
				BookedRoomInfo info = new BookedRoomInfo();
				String roomType = roominfoList.get(i).getRoomType();
				int floor = roominfoList.get(i).getFloor();
				int numOfTraveller = roominfoList.get(i).getNumOfTraveller();
				info.setFloor(floor);
				info.setNumOfTraveller(numOfTraveller);
				info.setRoomType(roomType);
				roomList.add(info);

			}
			response.setRoom_details(roomList);
			hotelResponse.setBookingDetails(response);
			hotelResponse.setHotelDetail(hoteldetails);

			log.info("service layer user not found! ");

		} catch (Exception e) {
			log.error("Exception occurred while fetching  content: {}", e.getMessage());
			e.printStackTrace();
			throw new Exception("Content fetching failed - [Reason]: " + e.getMessage());
		}
		return hotelResponse;
	}

	public ArrayList<HotelResponseByUser> findByUserId(Long userId) throws Exception {

		ArrayList<HotelQueryByUserResponce> hotelResponseList = new ArrayList<HotelQueryByUserResponce>();
		HotelResponseByUser hoteldetailsresponse = null;
		;
		HotelQueryByUserResponce response = new HotelQueryByUserResponce();
		ArrayList<HotelResponseByUser> responseList = new ArrayList<HotelResponseByUser>();
		// ArrayList<HotelResponse> hoteldetailsList = new ArrayList<HotelResponse>();

		try {
			log.info("service layer");
			ArrayList<QuickQueryDto> quickList = hotelRepository.getQueryInfoByUserId(userId);
			for (QuickQueryDto quk : quickList) {
				hoteldetailsresponse = new HotelResponseByUser();
				DateTimeFormatter f = DateTimeFormatter.ofPattern("dd-MMM-uuuu");
				hoteldetailsresponse.setCheakingDate(f.format(quk.getCheakingDate()));
				hoteldetailsresponse.setCheckoutDate(f.format(quk.getCheckoutDate()));
				hoteldetailsresponse.setHotelName(quk.getHotelname());
				hoteldetailsresponse.setRating(quk.getRating());
				hoteldetailsresponse.setLocation(quk.getLocation());
				hoteldetailsresponse.setNoOfRoom(quk.getNoOfRoom());
				hoteldetailsresponse.setProofId(quk.getProofId());
				hoteldetailsresponse.setProofName(quk.getProofName());
				hoteldetailsresponse.setBookingId(quk.getBookingId());
				hoteldetailsresponse.setHotelId(quk.getHotelInfoId());
				hoteldetailsresponse.setCarausel_image(quk.getCarausel_image());

				ArrayList<UserBookingDetail> bookingDetailsInfo = new ArrayList<UserBookingDetail>();
				ArrayList<UserBookingDetail> bookingDetails = hotelRepository
						.getBookingDetails(hoteldetailsresponse.getBookingId());

				for (int counter = 0; counter < bookingDetails.size(); counter++) {

					UserBookingDetail booking = new UserBookingDetail();
					String firstName = bookingDetails.get(counter).getFirstName();
					String lastName = bookingDetails.get(counter).getLastName();
					String gender = bookingDetails.get(counter).getGender();

					// long bookingId = bookingDetails.get(counter).getBookingId();
					int age = bookingDetails.get(counter).getAge();
					booking.setFirstName(firstName);
					booking.setLastName(lastName);
					booking.setGender(gender);

					booking.setAge(age);

					bookingDetailsInfo.add(booking);

					// responseList.add(hoteldetailsresponse);
				}
				hoteldetailsresponse.setUserBookingdetails(bookingDetailsInfo);
				// responseList.add(hoteldetailsresponse);
				ArrayList<BookedRoomInfo> roominfoList = hotelRepository
						.getRoomDetails(hoteldetailsresponse.getBookingId());
				ArrayList<BookedRoomInfo> roomList = new ArrayList<BookedRoomInfo>();

				for (int i = 0; i < roominfoList.size(); i++) {
					BookedRoomInfo info = new BookedRoomInfo();
					String roomType = roominfoList.get(i).getRoomType();
					int floor = roominfoList.get(i).getFloor();
					int numOfTraveller = roominfoList.get(i).getNumOfTraveller();
					info.setFloor(floor);
					info.setNumOfTraveller(numOfTraveller);
					info.setRoomType(roomType);
					roomList.add(info);

					// //responseList.add(hoteldetailsresponse);

				}
				hoteldetailsresponse.setRommInfoList(roomList);
				responseList.add(hoteldetailsresponse);
			}

			// response.setTraveller(bookingDetailsInfo);
			response.setResponse(responseList);

			hotelResponseList.add(response);
			log.info("service layer user not found! ");

		} catch (

		Exception e) {
			log.error("Exception occurred while fetching  content: {}", e.getMessage());
			e.printStackTrace();
			throw new Exception("Content fetching failed - [Reason]: " + e.getMessage());
		}
		return responseList;
	}

	public HotelInfo saveHotelInfo(HotelInfo hotelInfo) {
		hotelRepository.save(hotelInfo);
		return hotelInfo;
	}

}