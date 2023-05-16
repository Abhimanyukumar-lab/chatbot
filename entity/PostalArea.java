/**
 * 
 */
package com.technojade.allybot.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;

/**
 * @author BC70873
 *
 */
@Data
@Entity
@Table(name = "postal_area")
public class PostalArea {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long areaId;
	private String name;
	private Long cityId;
	private String postalCode;
}
