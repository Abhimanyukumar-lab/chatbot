/**
 * 
 */
package com.technojade.allybot.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * @author ugam.sharma
 *
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "hotel_mst")
@Getter
@Setter
public class Hotel extends BaseEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long hotelId;
	
	private String hotelDescription;
	
	private Long clientId;
	private Long empId;
	
	private Long numberOfHotelCount;

	private String name;
	
	private String ownerName;
	
	private String hotelDisplayName;
	
	private String carauselImage;
	
	private String logo;
	
	private int numberOfRoom;
	
	private int numberOfBed;
	
	private int numberOfParking;
	
	private String geoLocation;
	
	private String gstNo;
	
	private Long addressId;
	
	private String rating;
	
}
