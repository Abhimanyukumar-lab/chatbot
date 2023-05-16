/**
 * 
 */
package com.technojade.allybot.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;

/**
 * @author ugam.sharma
 *
 */
@Entity
@Table(name = "hotel_contact_info")
@Getter
@Setter
public class HotelContactInfo extends BaseEntity{
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long contactInfoId; 
	
	private String contactPersonName;
	
	private String mobileNumber;
	
	private String emailId;
	
	private Long hotelId;
}
