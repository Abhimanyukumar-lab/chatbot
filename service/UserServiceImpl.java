/**
 * 
 */
package com.technojade.allybot.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.technojade.allybot.entity.User;
import com.technojade.allybot.repository.UserRepository;

import lombok.extern.slf4j.Slf4j;

/**
 * @author BC70873
 *
 */
@Service
@Slf4j
public class UserServiceImpl implements UserService {

	@Autowired
	private UserRepository userRepository;

	@Override
	public User registerOrUpdateProfile(User user) {
		user.setActive(true);
		userRepository.save(user);
		return user;
	}

	@Override
	public User login(User user) {
		User userEntity = userRepository.findByEmailIdAndPassword(user.getEmailId(), user.getPassword());

		if (null == userEntity) {
			return null;
		}
		return userEntity;
	}

	@Override
	public User loginByMobileNumber(String mobileNumber) {
		return userRepository.findByMobileNumberAndIsActive(mobileNumber, true);
	}

	@Override
	public User forgetPassword(String userId, String newPassword, String confirmPassword) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public User findByUserID(Long userId) {
		log.info("service layer");
		Optional<User> userOptional = userRepository.findById(userId);
		if (userOptional.isPresent())
			return userOptional.get();

		log.info("service layer user not found! ");

		return null;
	}

	@Override
	public User saveOrUpdate(User user) {
		log.info("");
		User imExist = null;
		if (null == user.getUserId())
			// checking taken emailId and username
			imExist = isEmailOrUsernameExist(user);

		if (null != imExist && null == user.getUserId())
			return null;

		if (null != user.getUserId()) {
			imExist = new User();
			BeanUtils.copyProperties(user, imExist);
			userRepository.save(imExist);
			return imExist;
		} else {
			user.setActive(true);
			userRepository.save(user);
		}
		return user;
	}

	@Override
	public List<User> findAll() {
		List<User> users = userRepository.findAll();
		log.info("service layer size {}", users.size());
		return users;
	}

	@Override
	public User isEmailOrUsernameExist(User user) {
		String usernameParam = (null != user.getUsername() ? user.getUsername() : "");
		String emailId = (null != user.getEmailId() ? user.getEmailId() : "");
		String mobileNumber = null != user.getMobileNumber() ? user.getMobileNumber() : "";
		return userRepository.findByEmailIdOrUsernameOrMobileNumber(emailId, usernameParam, mobileNumber);
	}

	@Override
	public User findByMobileNumberOrEmailId(User user) {
		String emailId = (null != user.getEmailId() ? user.getEmailId() : "");
		String mobileNumber = null != user.getMobileNumber() ? user.getMobileNumber() : "";
		return userRepository.findByMobileNumberOrEmailIdAndIsActive(mobileNumber, emailId, true);
	}

	@Transactional
	@Override
	public User deleteUser(Long id) {

		User userID = findByUserID(id);

		if (userID != null) {
			userRepository.deleteById(id);
			return userID;
		}
		return null;
	}

}
