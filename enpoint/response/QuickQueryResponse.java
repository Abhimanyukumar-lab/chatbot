package com.technojade.allybot.enpoint.response;

import java.time.LocalDateTime;
import java.util.ArrayList;

import com.technojade.allybot.entity.BookedRoomInfo;
import com.technojade.allybot.entity.UserBookingDetail;

public class QuickQueryResponse {

	
	private ArrayList<BookedRoomInfo> room_details;
	private ArrayList<UserBookingDetail> traveller;
	private Long bookingId;
	private LocalDateTime  checkoutDate;
	private LocalDateTime  cheakingDate;
	private String  proofId;

	private String  proofName;
	private String  noOfRoom;
	public ArrayList<BookedRoomInfo> getRoom_details() {
		return room_details;
	}
	public void setRoom_details(ArrayList<BookedRoomInfo> room_details) {
		this.room_details = room_details;
	}
	public ArrayList<UserBookingDetail> getTraveller() {
		return traveller;
	}
	public void setTraveller(ArrayList<UserBookingDetail> traveller) {
		this.traveller = traveller;
	}
	public Long getBookingId() {
		return bookingId;
	}
	public void setBookingId(Long bookingId) {
		this.bookingId = bookingId;
	}
	public LocalDateTime getCheckoutDate() {
		return checkoutDate;
	}
	public void setCheckoutDate(LocalDateTime checkoutDate) {
		this.checkoutDate = checkoutDate;
	}
	public LocalDateTime getCheakingDate() {
		return cheakingDate;
	}
	public void setCheakingDate(LocalDateTime cheakingDate) {
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
	
	
	
	

		
	}
