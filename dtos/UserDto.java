package com.technojade.allybot.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserDto {

	private String firstName;

	private String lastName;

	private String username;

	private String password;

	private String mobileNumber;

	private String emailId;

	private String address;

	private Long roleId;

}
