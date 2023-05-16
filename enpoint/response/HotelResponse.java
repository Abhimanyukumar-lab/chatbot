package com.technojade.allybot.enpoint.response;

public class HotelResponse {
	private String hotelName;
	private Long hotelId;
	private String rating;
	private String location;
	private String carausel_image;
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
	
}
