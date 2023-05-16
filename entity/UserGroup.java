/**
 * 
 */
package com.technojade.allybot.entity;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author ugam.sharma
 *
 */
@Entity
@Table(name = "users_group_mapping")
@Data
@NoArgsConstructor
public class UserGroup implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;


	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long userGroupId;
	
	private Long groupId;
	
	private Long employeeId;
	
	private Long hotelId;
	
	private Long clientId;
}
