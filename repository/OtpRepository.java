/**
 * 
 */
package com.technojade.allybot.repository;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.technojade.allybot.entity.OtpEntity;

/**
 * @author BC70873
 *
 */
public interface OtpRepository extends JpaRepository<OtpEntity, Long>{

	public OtpEntity findByMobileNumberAndOtpAndIsValidated(String mobileNumber, String otp, String isValidated);
	
	public OtpEntity findByOtpAndIsValidated(String otp, String isValidated);

	@Modifying
	@Transactional
	@Query(value = "update OtpEntity t set t.isValidated='Y' where t.mobileNumber=:mobileNumber and t.otp=:otp")
	public void disableValidatedOtp(@Param("mobileNumber") String mobileNumber, @Param("otp") String otp);
	
}
