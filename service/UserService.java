/**
 * 
 */
package com.technojade.allybot.service;

import java.util.List;

import com.technojade.allybot.entity.User;

/**
 * @author BC70873
 *
 */

public interface UserService {

	public User registerOrUpdateProfile(User user);

	public User findByUserID(Long userId);

	public List<User> findAll();
	
	public User login(User user);

	public User isEmailOrUsernameExist(User user);
	
	public User loginByMobileNumber(String mobileNumber);

	public User findByMobileNumberOrEmailId(User user);

	public User forgetPassword(String userId, String newPassword, String confirmPassword);
	
	public User saveOrUpdate(User user);
	
	public User deleteUser(Long id);

}
