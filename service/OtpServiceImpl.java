/**
 * 
 */
package com.technojade.allybot.service;

import java.time.LocalDateTime;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.JsonNode;
import com.technojade.allybot.entity.OtpEntity;
import com.technojade.allybot.entity.User;
import com.technojade.allybot.entity.UserBookingInfo;
import com.technojade.allybot.integration.SmsUtil;
import com.technojade.allybot.repository.OtpRepository;

/**
 * @author BC70873
 *
 */
@Service
public class OtpServiceImpl implements OtpService {

	@Autowired
	private OtpRepository otpRepository;

	@Autowired
	private SmsUtil smsUtil;

	@Autowired
	private UserService userService;

	@Autowired
	private UserBookingInfoService userBookingInfoService;

	@Override
	public String sendOtpOnly(String mobileNumber) {

		User loginByMobileNumber = userService.loginByMobileNumber(mobileNumber);

		UserBookingInfo byUserId = null;

		if (loginByMobileNumber != null) {
			byUserId = userBookingInfoService.findByUserId(loginByMobileNumber.getUserId());
		}

		String otp = generateRandomNumber();
		JsonNode responseNode = smsUtil.sendOtpOnly(mobileNumber, otp);
		if (responseNode != null && responseNode.get("return").asBoolean() == true) {
			OtpEntity otpEntity = new OtpEntity();
			otpEntity.setMobileNumber(mobileNumber);
			otpEntity.setOtp(otp);
			otpEntity.setIsValidated("N");
			if (byUserId != null) {
				otpEntity.setOtpGeneratedTime(byUserId.getCheckoutDate());
			} else {
				return null;
			}
//			otpEntity.setOtpGeneratedTime(LocalDateTime.now());
			otpRepository.save(otpEntity);
			return otp;
		}
		return null;
	}
	

	@Override
	public String sendOtpOnlyWithCheckoutDate(String mobileNumber, LocalDateTime chkoutdate) {

		String otp = generateRandomNumber();
		JsonNode responseNode = smsUtil.sendOtpOnly(mobileNumber, otp);
		if (responseNode != null && responseNode.get("return").asBoolean() == true) {
			OtpEntity otpEntity = new OtpEntity();
			otpEntity.setMobileNumber(mobileNumber);
			otpEntity.setOtp(otp);
			otpEntity.setIsValidated("N");
			otpEntity.setOtpGeneratedTime(chkoutdate);
//			otpEntity.setOtpGeneratedTime(LocalDateTime.now());
			otpRepository.save(otpEntity);
			return otp;
		}
		return null;
	}

	@Override
	public boolean validate(String mobileNumber, String otp) {
		OtpEntity otpEntity = otpRepository.findByMobileNumberAndOtpAndIsValidated(mobileNumber, otp, "N");

		if (null != otpEntity && LocalDateTime.now().isBefore(otpEntity.getOtpGeneratedTime().plusMinutes(2))) {
			otpRepository.disableValidatedOtp(mobileNumber, otp);
			return true;
		} else {
			return false;
		}
	}

	@Override
	public OtpEntity validate(String otp) {
		// TODO Auto-generated method stub
		OtpEntity otpEntity = otpRepository.findByOtpAndIsValidated(otp, "N");

		if (null != otpEntity && LocalDateTime.now().isBefore(otpEntity.getOtpGeneratedTime().plusMinutes(2))) {
//			otpRepository.disableValidatedOtp(otpEntity.getMobileNumber(), otp);
			return otpEntity;
		}
		return null;
	}

	public String generateRandomNumber() {
		Random rnd = new Random();
		int number = rnd.nextInt(999999);
		return String.format("%06d", number);
	}

	@Override
	public String sendOtpWithCredential(String mobileNumber) {
		String otp = generateRandomNumber();
		JsonNode responseNode = smsUtil.sendOtpWithUserCredential(mobileNumber, otp);
		if (responseNode != null && responseNode.get("return").asBoolean() == true) {
			OtpEntity otpEntity = new OtpEntity();
			otpEntity.setMobileNumber(mobileNumber);
			otpEntity.setOtp(otp);
			otpEntity.setIsValidated("N");
			otpEntity.setOtpGeneratedTime(LocalDateTime.now());
			otpRepository.save(otpEntity);
			return otp;
		}
		return null;
	}



}
