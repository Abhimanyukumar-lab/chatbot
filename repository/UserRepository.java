/**
 * 
 */
package com.technojade.allybot.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.technojade.allybot.entity.User;

/**
 * @author BC70873
 *
 */
@Repository
public interface UserRepository extends JpaRepository<User, Long>{

	public User findByEmailIdAndPassword(String emailId, String password);

	public User findByEmailIdOrUsernameOrMobileNumber(String emailId, String username, String mobileNumber);

	public User findByMobileNumberAndIsActive(String mobileNumber, boolean isActive);
	
	public User findByMobileNumberOrEmailIdAndIsActive(String mobileNumber,String emailId, boolean isActive);
}
