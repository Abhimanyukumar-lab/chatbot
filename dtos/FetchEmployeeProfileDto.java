package com.technojade.allybot.dtos;

import com.technojade.allybot.entity.Address;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class FetchEmployeeProfileDto {

	private Long empId;
	private String fname;
	private String mname;
	private String lname;
	private String email;
	private String username;
	private String password;
	private Long age;
	private String contactNo;
	private String alternateContactNo;
	private Long addressId;
	private Address addressObj;
	private String city;
	private String state;
	private String department;
	private String designation;
	private String role;
	private String empCode;
	private String supervisorName;
	private String idProof;
	private String idNo;
	private Double expYears;
	private String workShift;
	private Double rating;
	private String profileImageURL;
	
	private Long departmentId;
	private Long designationId;
	private Long roleId;
	
	
}
