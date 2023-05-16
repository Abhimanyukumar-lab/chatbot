package com.technojade.allybot.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EmployeeDto {

	private Long empId;
	private String empCode;
	private String username;
	private String password;
	private String fname;
	private String mname;
	private String lname;
	private String email;
	private String contactNo;
	private String workShift;
	private Long clientId;
	private Long hotelId;
	private String supervisorName;
	private String departmentName;
	private String designationName;
	private Long roleId;
	private Double expYears;
	private Double rating;
	private Long age;
	private String idProof;
	private String idNo;
	private String profileImageURL;
	private Long departmentId;
	private Long designationId;
	private String role;
	private boolean isActive;
	private Long addressId;
}
