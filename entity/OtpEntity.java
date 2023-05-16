/**
 * 
 */
package com.technojade.allybot.entity;

import java.io.Serializable;
import java.time.LocalDateTime;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author BC70873
 *
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "otp")
public class OtpEntity implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -568052475827532723L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long otpId;
	
	private Long userId;
	
	private String mobileNumber;
	
	private String otp;

	private String isValidated;
	
	private LocalDateTime otpGeneratedTime;
	
}
