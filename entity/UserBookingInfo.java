/**
 * 
 */
package com.technojade.allybot.entity;

import java.time.LocalDateTime;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * @author BC70873
 *
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "user_booking_info")
public class UserBookingInfo extends BaseEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long bookingId;
	
	private Long userId;
	
	private Long hotelId;
	
	private LocalDateTime checkingDate;
	
	private LocalDateTime checkoutDate;
	
	private String proofid;
	private int numberOfRroom;
	private String proofName;
	private Boolean token;
	private String tokenValue;
	private Long onlineBookingId;
	

}
