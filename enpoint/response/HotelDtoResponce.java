package com.technojade.allybot.enpoint.response;

public class HotelDtoResponce {
HotelResponse hotelDetail;
QuickQueryResponse bookingDetails;
public HotelResponse getHotelDetail() {
	return hotelDetail;
}
public void setHotelDetail(HotelResponse hotelDetail) {
	this.hotelDetail = hotelDetail;
}
public QuickQueryResponse getBookingDetails() {
	return bookingDetails;
}
public void setBookingDetails(QuickQueryResponse bookingDetails) {
	this.bookingDetails = bookingDetails;
}

}
