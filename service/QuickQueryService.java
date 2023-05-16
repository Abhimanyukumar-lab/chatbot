package com.technojade.allybot.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.technojade.allybot.enpoint.response.CategoryResponce;
import com.technojade.allybot.enpoint.response.HotelDtoResponce;
import com.technojade.allybot.enpoint.response.HotelResponse;
import com.technojade.allybot.enpoint.response.QuickQueryCategaryResponse;
import com.technojade.allybot.enpoint.response.QuickQueryDto;
import com.technojade.allybot.enpoint.response.QuickQueryResponse;
import com.technojade.allybot.entity.QuickQueryCategary;
import com.technojade.allybot.entity.BookedRoomInfo;
import com.technojade.allybot.entity.User;
import com.technojade.allybot.entity.UserBookingDetail;
import com.technojade.allybot.repository.QuickQueryRepository;


import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class QuickQueryService {
	@Autowired
	QuickQueryRepository quickQueryRepository;
	
	
		
	public QuickQueryCategaryResponse getByHotelId(Long hotelId) throws Exception {
	ArrayList<QuickQueryCategary> categoryList =	quickQueryRepository.getCategory(hotelId);
	CategoryResponce reponse ;
	 QuickQueryCategaryResponse quickQueryCategaryResponse = new QuickQueryCategaryResponse();
	 ArrayList<CategoryResponce> reponseList =	new ArrayList<CategoryResponce>();
	 
	 for (int i = 0; i < categoryList.size(); i++) { 
		 reponse = new CategoryResponce();
		 Long quickCatId = categoryList.get(i).getQuickCatId();
		 String  label = categoryList.get(i).getCategoryLabel();
		 String  icon = categoryList.get(i).getCategoryIcon();
		 reponse.setIcon(icon);
		 reponse.setLabel(label);
		 reponse.setQuick_cat_id(quickCatId);
		 reponseList.add(reponse);
			 
	 }
	 quickQueryCategaryResponse.setCategoryList(reponseList);
		return quickQueryCategaryResponse;
		
	}

}
