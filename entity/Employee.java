package com.technojade.allybot.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Transient;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Employee extends BaseEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long empId;
	private String empCode;
	@NotNull
	@NotBlank(message = "Username is Invalid")
	private String username;
	@NotNull
	@NotBlank(message = "Password is Invalid")
	private String password;
	@NotNull
	@NotBlank(message = "Firstname is Invalid")
	private String fname;	
	private String mname;
	private String lname;
	@NotNull
	@NotBlank(message = "Email is Invalid")
	@Email
	private String email;
	@NotNull
	@NotBlank(message = "Contact is Invalid")
	private String contactNo;
	private String workShift;
	private Long clientId;
	private Long hotelId;
	private String supervisorName;
	private Long departmentId;
	private Long designationId;
	private Long roleId;
	private Double expYears;
	private Double rating;
	private Long age;
	private String idProof;
	private String idNo;
	private String profileImageURL;

	private boolean isActive;

	@Transient
	private String newPassword;

	@Transient
	private String confirmPassword;

	@Transient
	private String oldPassword;

	@Transient
	private String otp;

	private Long addressId;
	
	private String alternateContactNo;
	
}
