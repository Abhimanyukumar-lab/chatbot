package com.technojade.allybot.enpoint.response;

import java.util.ArrayList;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class QuickQueryCategaryResponse {
	 ArrayList<CategoryResponce> categoryList = new ArrayList<CategoryResponce>();
	 
   
}
