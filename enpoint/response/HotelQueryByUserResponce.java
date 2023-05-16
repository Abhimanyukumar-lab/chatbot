package com.technojade.allybot.enpoint.response;

import java.util.ArrayList;

public class HotelQueryByUserResponce {
ArrayList<HotelResponseByUser> response = new ArrayList<HotelResponseByUser>();

public ArrayList<HotelResponseByUser> getResponse() {
	return response;
}

public void setResponse(ArrayList<HotelResponseByUser> response) {
	this.response = response;
}

}
