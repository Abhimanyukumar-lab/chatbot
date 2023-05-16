/**
 * 
 */
package com.technojade.allybot.enpoint;

import java.sql.Timestamp;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.technojade.allybot.enpoint.response.AllyBotResponseDto;
import com.technojade.allybot.enpoint.response.HotelDtoResponce;
import com.technojade.allybot.enpoint.response.QuickQueryCategaryResponse;
import com.technojade.allybot.enpoint.response.ServiceStatus;
import com.technojade.allybot.service.QuickQueryService;

/**
 * @author BC70873
 *
 */

@RestController
public class CategoryController {
	@Autowired
	QuickQueryService quickService;
	@GetMapping("/quick-query-category/hotel/{id}")
	public ResponseEntity<?> getQuickQueryCategory(@PathVariable("id") long id) throws Exception{
		QuickQueryCategaryResponse categoryResponse = quickService.getByHotelId(id);
		AllyBotResponseDto<QuickQueryCategaryResponse> response = new AllyBotResponseDto<>();
		response.setData(categoryResponse);
		response.setServiceStatus(ServiceStatus.SUCCESS);
		response.setTimestamp(new Timestamp(System.currentTimeMillis()));
		response.setTraceId(Long.MAX_VALUE);
		response.setMsg("Hotel information retireved");
		return ResponseEntity.status(HttpStatus.OK).body(response);
		 
		
	}
}
