package com.technojade.allybot.enpoint.response;

import java.time.LocalDateTime;
import java.util.ArrayList;

import com.technojade.allybot.entity.BookedRoomInfo;
import com.technojade.allybot.entity.UserBookingDetail;

public class HotelResponseByUser {
	private String hotelName;
	private Long hotelId;
	private String rating;
	private String location;
	private String carausel_image;
	
	private ArrayList<BookedRoomInfo> rommInfoList;
	private ArrayList<UserBookingDetail> userBookingdetails;
	private Long bookingId;
	private String  checkoutDate;
	private String  cheakingDate;
	private String  proofId;

	private String  proofName;
	private String  noOfRoom;
	private int  floor;
	public String getHotelName() {
		return hotelName;
	}
	public void setHotelName(String hotelName) {
		this.hotelName = hotelName;
	}
	public Long getHotelId() {
		return hotelId;
	}
	public void setHotelId(Long hotelId) {
		this.hotelId = hotelId;
	}
	public String getRating() {
		return rating;
	}
	public void setRating(String rating) {
		this.rating = rating;
	}
	public String getLocation() {
		return location;
	}
	public void setLocation(String location) {
		this.location = location;
	}
	public String getCarausel_image() {
		return carausel_image;
	}
	public void setCarausel_image(String carausel_image) {
		this.carausel_image = carausel_image;
	}
	
	public ArrayList<BookedRoomInfo> getRommInfoList() {
		return rommInfoList;
	}
	public void setRommInfoList(ArrayList<BookedRoomInfo> rommInfoList) {
		this.rommInfoList = rommInfoList;
	}
	public ArrayList<UserBookingDetail> getUserBookingdetails() {
		return userBookingdetails;
	}
	public void setUserBookingdetails(ArrayList<UserBookingDetail> userBookingdetails) {
		this.userBookingdetails = userBookingdetails;
	}
	public Long getBookingId() {
		return bookingId;
	}
	public void setBookingId(Long bookingId) {
		this.bookingId = bookingId;
	}
	public String getCheckoutDate() {
		return checkoutDate;
	}
	public void setCheckoutDate(String checkoutDate) {
		this.checkoutDate = checkoutDate;
	}
	public String getCheakingDate() {
		return cheakingDate;
	}
	public void setCheakingDate(String cheakingDate) {
		this.cheakingDate = cheakingDate;
	}
	public String getProofId() {
		return proofId;
	}
	public void setProofId(String proofId) {
		this.proofId = proofId;
	}
	public String getProofName() {
		return proofName;
	}
	public void setProofName(String proofName) {
		this.proofName = proofName;
	}
	public String getNoOfRoom() {
		return noOfRoom;
	}
	public void setNoOfRoom(String noOfRoom) {
		this.noOfRoom = noOfRoom;
	}
	public int getFloor() {
		return floor;
	}
	public void setFloor(int floor) {
		this.floor = floor;
	}
	
	
	
}
