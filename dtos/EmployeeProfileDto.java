package com.technojade.allybot.dtos;

import com.technojade.allybot.entity.Address;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EmployeeProfileDto {

	private Long empId;
	private String fname;
	private String mname;
	private String lname;
	private String email;
	private String password;
	private String username;
	private Long age;
	private String contactNo;
	private String alternateContactNo;
	private Long addressId;
	private String address;
	private Address addressObj;
	private Long cityId;
	private Long stateId;
	private Long departmentId;
	private Long designationId;
	private Long roleId;
	private String empCode;
	private String supervisorName;
	private String idProof;
	private String idNo;
	private Double expYears;
	private Double rating;
	private String workShift;
	private String profileImageURL;
	
	
}
