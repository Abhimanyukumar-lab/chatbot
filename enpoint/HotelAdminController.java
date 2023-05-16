/**
 * 
 */
package com.technojade.allybot.enpoint;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.technojade.allybot.entity.Hotel;
import com.technojade.allybot.service.HotelService;

/**
 * @author ugam.sharma
 *
 */
@RestController
@RequestMapping(value = "/hotel")
public class HotelAdminController {

	@Autowired
	private HotelService hs;

	@PostMapping
	public ResponseEntity<?> createHotel(@RequestBody Hotel hotel) {
		Hotel createHotel = hs.createHotel(hotel);
		return ResponseEntity.status(HttpStatus.OK).body(createHotel);
	}
}
