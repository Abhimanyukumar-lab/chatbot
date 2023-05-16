/**
 * 
 */
package com.technojade.allybot.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.NotBlank;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.NonNull;

/**
 * @author BC70873
 *
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
@Table(name = "user_info")
@Entity
public class User extends BaseEntity{

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long userId;
	
	private String firstName;
	
	private String lastName;

	private String username;

	//@NotBlank(message = "password must not be blank")
	private String password;
	
	//@NotBlank(message = "mobileNumber must not be blank")
	private String mobileNumber;
	
	//@NotBlank(message = "emailId must not be blank")
	private String emailId;
	
	private boolean isActive;
	
	@Transient
	private String newPassword;
	
	@Transient
	private String confirmPassword;

	@Transient
	private String oldPassword;

	@Transient
	private String otp;
	
	private String address;
	
	@Transient
	private byte[] profileImageURL;
	
	private Long roleId;
	
}
