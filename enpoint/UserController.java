/**
 * 
 */
package com.technojade.allybot.enpoint;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.net.URLConnection;
import java.sql.Timestamp;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.technojade.allybot.dtos.UserDto;
import com.technojade.allybot.enpoint.response.AllyBotResponseDto;
import com.technojade.allybot.enpoint.response.ApiError;
import com.technojade.allybot.enpoint.response.ServiceStatus;
import com.technojade.allybot.entity.Employee;
import com.technojade.allybot.entity.FileInformation;
import com.technojade.allybot.entity.GroupMst;
import com.technojade.allybot.entity.OtpEntity;
import com.technojade.allybot.entity.User;
import com.technojade.allybot.service.FileUploadService;
import com.technojade.allybot.service.OtpService;
import com.technojade.allybot.service.UserService;

import lombok.extern.slf4j.Slf4j;

/**
 * @author BC70873
 *
 */

@RestController
@RequestMapping(value = "/user")
@Slf4j
public class UserController {

	@Autowired
	private UserService userService;

	@Autowired
	private OtpService otpService;

	@Autowired
	private FileUploadService fileUploadService;

	@Autowired
	private ObjectMapper objectMapper;

	@PostMapping("/register")
	public ResponseEntity<?> registerUser(@RequestBody User user) {
		log.info("start user registration username {} and emailId {} password", user.getUsername(), user.getPassword());
		// checking userId to identify whether request is coming for update or save
		boolean isUpdate = false;
		if (null != user.getUserId()) {
			isUpdate = true;
		}
		User userResponse = userService.saveOrUpdate(user);
		// Email exist
		if (null == userResponse) {
			AllyBotResponseDto<User> userRes = new AllyBotResponseDto<>();
			String errorMessage = "Something wen't wrong";
			String msg = "EmailId or Username or Mobile Number already taken";
			badRequest(userRes, errorMessage, msg);
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(userRes);
		}

		UserDto userDto = new UserDto();
		BeanUtils.copyProperties(user, userDto);

		// Update the user
		if (isUpdate) {
			AllyBotResponseDto<UserDto> userRes = new AllyBotResponseDto<>();
			userRes.setData(userDto);
			userRes.setMsg("User has been updated");
			userRes.setServiceStatus(ServiceStatus.SUCCESS);
			userRes.setTimestamp(new Timestamp(System.currentTimeMillis()));
			userRes.setTraceId(Long.MAX_VALUE);
			return ResponseEntity.status(HttpStatus.OK).body(userRes);
		}

		AllyBotResponseDto<UserDto> userRes = new AllyBotResponseDto<>();
		userRes.setData(userDto);
		userRes.setServiceStatus(ServiceStatus.SUCCESS);
		userRes.setTimestamp(new Timestamp(System.currentTimeMillis()));
		userRes.setTraceId(Long.MAX_VALUE);
		userRes.setMsg("User has been registred successfully");
		return ResponseEntity.status(HttpStatus.CREATED).body(userRes);
	}

	@GetMapping("/get/{userId}")
	public ResponseEntity<?> getUser(@PathVariable("userId") Long userId) {
		log.info("get user userId ", userId);
		User userResponse = userService.findByUserID(userId);
		if (null == userResponse) {
			AllyBotResponseDto<User> userRes = new AllyBotResponseDto<>();
			String errorMessage = "User does not exist";
			String msg = "User not found";
			notFound(userRes, errorMessage, msg);
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(userRes);
		}
		AllyBotResponseDto<User> userRes = new AllyBotResponseDto<>();
		userRes.setData(userResponse);
		userRes.setServiceStatus(ServiceStatus.SUCCESS);
		userRes.setTimestamp(new Timestamp(System.currentTimeMillis()));
		userRes.setTraceId(Long.MAX_VALUE);
		return ResponseEntity.status(HttpStatus.OK).body(userRes);
	}

	private void notFound(AllyBotResponseDto<User> userRes, String errorMessage, String msg) {
		userRes.setData(null);
		ApiError error = new ApiError();
		error.setErrorCode("TJ10102");
		error.setErrorMessage(errorMessage);
		userRes.setErrors(error);
		userRes.setMsg(msg);
		userRes.setServiceStatus(ServiceStatus.FAILURE);
		userRes.setTimestamp(new Timestamp(System.currentTimeMillis()));
		userRes.setTraceId(Long.MAX_VALUE);
	}

	@GetMapping("/all")
	public ResponseEntity<?> getUserAll() {
		log.info("fetching all user");
		List<User> userResponse = userService.findAll();
		AllyBotResponseDto<List<User>> userRes = new AllyBotResponseDto<>();
		userRes.setData(userResponse);
		userRes.setServiceStatus(ServiceStatus.SUCCESS);
		userRes.setTimestamp(new Timestamp(System.currentTimeMillis()));
		userRes.setTraceId(Long.MAX_VALUE);
		return ResponseEntity.status(HttpStatus.OK).body(userRes);
	}

	@PutMapping("/{userId}")
	public ResponseEntity<?> updateUser(@PathVariable("userId") Long userId, @Valid @RequestBody User user) {
		log.info("get user userId ", userId);
		log.info("user data {}", user.getEmailId());
		User userResponse = userService.findByUserID(userId);
		userResponse.setActive(true);
		userResponse.setUpdatedBy(userId);
		if (null != user.getEmailId() && !user.getEmailId().isEmpty())
			userResponse.setEmailId(user.getEmailId());

		if (null != user.getFirstName() && !user.getFirstName().isEmpty())
			userResponse.setFirstName(user.getFirstName());

		if (null != user.getMobileNumber() && !user.getMobileNumber().isEmpty())
			userResponse.setMobileNumber(user.getMobileNumber());
		if (null != user.getLastName() && !user.getLastName().isEmpty())
			userResponse.setLastName(user.getLastName());

		if (null != user.getAddress() && !user.getAddress().isEmpty())
			userResponse.setAddress(user.getAddress());

		UserDto userDto = new UserDto();
		BeanUtils.copyProperties(user, userDto);

		userService.saveOrUpdate(userResponse);
		AllyBotResponseDto<UserDto> userRes = new AllyBotResponseDto<>();
		userRes.setData(userDto);
		userRes.setMsg("Profile updated successfully");
		userRes.setServiceStatus(ServiceStatus.SUCCESS);
		userRes.setTimestamp(new Timestamp(System.currentTimeMillis()));
		userRes.setTraceId(Long.MAX_VALUE);
		return ResponseEntity.status(HttpStatus.OK).body(userRes);
	}

	@PostMapping("/login")
	public ResponseEntity<?> login(@RequestBody JsonNode jsonNode, String mobileNumber) {

		AllyBotResponseDto<User> userRes = new AllyBotResponseDto<>();
		if (null != mobileNumber && !mobileNumber.isEmpty()) {
			User userEntiry = userService.loginByMobileNumber(mobileNumber);
			if (null == userEntiry) {
				String errorMessage = "Mobile number does not exist.";
				String msg = "Invalid mobile number.";
				notFound(userRes, errorMessage, msg);
				return ResponseEntity.status(HttpStatus.NOT_FOUND).body(userRes);
			}
			String otp = otpService.sendOtpOnly(mobileNumber);
			AllyBotResponseDto<JsonNode> otpRes = new AllyBotResponseDto<>();
			ObjectNode objectNode = objectMapper.createObjectNode();
			objectNode.put("otp", otp);
			otpRes.setData(objectNode);

			if (otp != null) {
				otpRes.setServiceStatus(ServiceStatus.SUCCESS);
				otpRes.setMsg("Otp Sent");
			} else {
				otpRes.setServiceStatus(ServiceStatus.FAILURE);
				otpRes.setMsg("Otp sending failed");
			}

			otpRes.setTimestamp(new Timestamp(System.currentTimeMillis()));
			otpRes.setTraceId(Long.MAX_VALUE);
			return ResponseEntity.status(HttpStatus.OK).body(otpRes);
		} else {
			User usertemp = new User();
			usertemp.setEmailId(jsonNode.get("emailId").asText());
			usertemp.setPassword(jsonNode.get("password").asText());
			User userResponse = userService.login(usertemp);
			if (null == userResponse) {
				String errorMessage = "User does not exist.";
				String msg = "Invalid email or password.";
				notFound(userRes, errorMessage, msg);
				return ResponseEntity.status(HttpStatus.NOT_FOUND).body(userRes);
			}
			userRes.setData(userResponse);
			userRes.setServiceStatus(ServiceStatus.SUCCESS);
			userRes.setTimestamp(new Timestamp(System.currentTimeMillis()));
			userRes.setTraceId(Long.MAX_VALUE);
		}
		userRes.setMsg("Logged in successfully");
		return ResponseEntity.status(HttpStatus.OK).body(userRes);
	}

	/*
	 * @PostMapping(value = "/otp/validate") public ResponseEntity<?>
	 * validate(@RequestBody JsonNode otpReqDto) { boolean isValidated =
	 * otpService.validate(otpReqDto.get("mobileNumber").asText(),
	 * otpReqDto.get("otp").asText()); if (!isValidated) {
	 * AllyBotResponseDto<Employee> otpRes = new AllyBotResponseDto<>();
	 * otpRes.setData(null); otpRes.setServiceStatus(ServiceStatus.SUCCESS);
	 * otpRes.setMsg("OTP is invalid"); otpRes.setTraceId(Long.MAX_VALUE);
	 * otpRes.setTimestamp(Timestamp.from(Instant.now())); return
	 * ResponseEntity.status(HttpStatus.OK).body(otpRes); } User user =
	 * userService.loginByMobileNumber(otpReqDto.get("mobileNumber").asText());
	 * AllyBotResponseDto<User> otpRes = new AllyBotResponseDto<>();
	 * otpRes.setData(user); otpRes.setServiceStatus(ServiceStatus.SUCCESS);
	 * otpRes.setMsg("Otp validated, User will be logging now.");
	 * otpRes.setTraceId(Long.MAX_VALUE);
	 * otpRes.setTimestamp(Timestamp.from(Instant.now())); return
	 * ResponseEntity.status(HttpStatus.OK).body(otpRes); }
	 */

	@PostMapping(value = "/otp/validate")
	public ResponseEntity<?> validate(@RequestBody JsonNode otpReqDto) {
		OtpEntity validate = otpService.validate(otpReqDto.get("otp").asText());
		if (validate == null) {
			AllyBotResponseDto<Employee> otpRes = new AllyBotResponseDto<>();
			otpRes.setData(null);
			otpRes.setServiceStatus(ServiceStatus.SUCCESS);
			otpRes.setMsg("OTP is invalid");
			otpRes.setTraceId(Long.MAX_VALUE);
			otpRes.setTimestamp(Timestamp.from(Instant.now()));
			return ResponseEntity.status(HttpStatus.OK).body(otpRes);
		}
		User user = userService.loginByMobileNumber(validate.getMobileNumber());
		AllyBotResponseDto<User> otpRes = new AllyBotResponseDto<>();
		otpRes.setData(user);
		otpRes.setServiceStatus(ServiceStatus.SUCCESS);
		otpRes.setMsg("Otp validated, User will be logging now.");
		otpRes.setTraceId(Long.MAX_VALUE);
		otpRes.setTimestamp(Timestamp.from(Instant.now()));
		return ResponseEntity.status(HttpStatus.OK).body(otpRes);
	}

	@PostMapping("/changepassword")
	public ResponseEntity<?> changePassword(@RequestBody JsonNode jsonNode,
			@RequestParam(name = "type", required = false) String requestType) {

		AllyBotResponseDto<User> userRes = new AllyBotResponseDto<>();

		// validation for change password
		if (null != requestType && requestType.equals("changepassword")) {
			if (null == jsonNode.get("oldPassword").asText()) {
				String errorMessage = "";
				String msg = "Old password is empty";
				userRes.setData(null);
				badRequest(userRes, errorMessage, msg);
				return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(userRes);
			}
			User userEntity = userService.findByUserID(jsonNode.get("userId").asLong());

			if (!jsonNode.get("oldPassword").asText().equals(userEntity.getPassword())) {
				String errorMessage = "";
				String msg = "Old password did not match";
				userRes.setData(null);
				badRequest(userRes, errorMessage, msg);
				return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(userRes);
			}

			if (!jsonNode.get("newPassword").asText().equals(jsonNode.get("confirmPassword").asText())) {
				String errorMessage = "Password and confirm password must be same";
				String msg = "Confirm password did not match";
				userRes.setData(null);
				badRequest(userRes, errorMessage, msg);
				return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(userRes);
			}

			// everything looks good for change password
			userEntity.setPassword(jsonNode.get("newPassword").asText());
			userService.saveOrUpdate(userEntity);
			userRes = resetPasswordResponse(userEntity);
			return ResponseEntity.status(HttpStatus.OK).body(userRes);

		} else {
			// validation for forget password
			if (!jsonNode.get("newPassword").asText().equals(jsonNode.get("confirmPassword").asText())) {
				String errorMessage = "Password and confirm password must be same";
				String msg = "Confirm password did not match";
				userRes.setData(null);
				badRequest(userRes, errorMessage, msg);
				return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(userRes);
			}

			User userTemp = new User();
			userTemp.setEmailId(jsonNode.get("emailId").asText());
			userTemp.setMobileNumber(jsonNode.get("mobileNumber").asText());
			userTemp.setOldPassword(jsonNode.get("oldPassword").asText());
			userTemp.setConfirmPassword(jsonNode.get("confirmPassword").asText());
			userTemp.setNewPassword(jsonNode.get("newPassword").asText());
			// forget password case
			User userResponse = userService.findByMobileNumberOrEmailId(userTemp);
			userResponse.setPassword(userTemp.getNewPassword());
			userService.saveOrUpdate(userResponse);
			userRes = resetPasswordResponse(userResponse);
			return ResponseEntity.status(HttpStatus.OK).body(userRes);
		}
	}

	@PostMapping("/verifyemailorcontact")
	public ResponseEntity<?> emailOrMobileVerifiation(@RequestBody JsonNode jsonNode) {

		AllyBotResponseDto<User> userRes = new AllyBotResponseDto<>();

		User userTemp = new User();
		userTemp.setEmailId(jsonNode.get("emailId").asText());
		User userResponse = userService.findByMobileNumberOrEmailId(userTemp);

		if (null == userResponse) {
			String errorMessage = "User does not exist.";
			String msg = "Invalid email";
			notFound(userRes, errorMessage, msg);
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(userRes);
		}

		userRes.setData(userResponse);
		userRes.setMsg("Email is validated");
		userRes.setServiceStatus(ServiceStatus.SUCCESS);
		userRes.setTimestamp(new Timestamp(System.currentTimeMillis()));
		userRes.setTraceId(Long.MAX_VALUE);
		return ResponseEntity.status(HttpStatus.OK).body(userRes);
	}

	@PostMapping("/forgetpassword")
	public ResponseEntity<?> forgetPassword(@RequestBody JsonNode jsonNode) {

		AllyBotResponseDto<User> userRes = new AllyBotResponseDto<>();

		User user = userService.findByUserID(jsonNode.get("userId").asLong());

		if (!jsonNode.get("newPassword").asText().equals(jsonNode.get("confirmPassword").asText())) {
			String errorMessage = "Password and confirm password must be same";
			String msg = "Confirm password did not match";
			userRes.setData(null);
			badRequest(userRes, errorMessage, msg);
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(userRes);
		}
		user.setPassword(jsonNode.get("newPassword").asText());
		userService.saveOrUpdate(user);
		userRes = resetPasswordResponse(user);
		return ResponseEntity.status(HttpStatus.OK).body(userRes);
	}

	private AllyBotResponseDto<User> resetPasswordResponse(User userResponse) {
		AllyBotResponseDto<User> userRes;
		userRes = new AllyBotResponseDto<>();
		userRes.setMsg("Password has been changed successfully");
		userRes.setData(userResponse);
		userRes.setServiceStatus(ServiceStatus.SUCCESS);
		userRes.setTimestamp(new Timestamp(System.currentTimeMillis()));
		userRes.setTraceId(Long.MAX_VALUE);
		return userRes;
	}

	@PostMapping("/image/{userid}")
	public ResponseEntity<?> updateImage(@PathVariable("userid") String userId,
			@RequestParam("file") MultipartFile file) {
		AllyBotResponseDto<FileInformation> userRes = new AllyBotResponseDto<>();

		try {
			FileInformation fileInform = fileUploadService.uploadFile(file, userId, 1);
			if (null != fileInform) {
				userRes.setData(fileInform);
				userRes.setMsg("Image uploaded");
				userRes.setServiceStatus(ServiceStatus.SUCCESS);
				userRes.setTraceId(Long.MAX_VALUE);
				return ResponseEntity.ok(userRes);
			} else {
				ApiError apiError = new ApiError();
				apiError.setErrorCode(HttpStatus.INTERNAL_SERVER_ERROR.name());
				return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(userRes);
			}

		} catch (Exception e) {
			ApiError apiError = new ApiError();
			apiError.setErrorCode(HttpStatus.INTERNAL_SERVER_ERROR.name());
			List<String> errors = new ArrayList<>();
			errors.add(e.getMessage());
			userRes.setErrors(apiError);
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(userRes);
		}
	}

	@GetMapping("/image/{userid}")
	public ResponseEntity<?> getImage(@PathVariable("userid") String userId, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		// String fileName = "imagespgAdmin.PNG";
		FileInformation fileInformation = fileUploadService.getFile(userId, 1);
		File file = new File(fileInformation.getUrl() + "/" + fileInformation.getFileName());
		String mimeType = URLConnection.guessContentTypeFromName(fileInformation.getFileName());
		if (mimeType == null) {
			mimeType = "application/octet-stream";
		}

		response.setContentType(mimeType);

		response.setHeader("Content-Disposition",
				String.format("inline; filename=\"" + fileInformation.getFileName() + "\""));

		response.setContentLength((int) file.length());

		InputStream inputStream = new BufferedInputStream(new FileInputStream(file));

		FileCopyUtils.copy(inputStream, response.getOutputStream());

		return null;
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<?> deleteUser(@PathVariable("id") Long id) {

		User deleteUser = userService.deleteUser(id);

		if (deleteUser == null) {
			AllyBotResponseDto<User> userRes = new AllyBotResponseDto<>();
			String errorMessage = "Something wen't wrong";
			String msg = "";
			badRequest(userRes, errorMessage, msg);
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(userRes);
		}

		AllyBotResponseDto<User> userRes = new AllyBotResponseDto<>();
		userRes.setData(deleteUser);
		userRes.setServiceStatus(ServiceStatus.SUCCESS);
		userRes.setTimestamp(new Timestamp(System.currentTimeMillis()));
		userRes.setTraceId(Long.MAX_VALUE);
		return ResponseEntity.status(HttpStatus.OK).body(userRes);

	}

	private void badRequest(AllyBotResponseDto<User> userRes, String errorMessage, String msg) {
		ApiError error = new ApiError();
		error.setErrorCode("TJ10101");
		error.setErrorMessage(errorMessage);
		userRes.setErrors(error);
		userRes.setMsg(msg);
		userRes.setServiceStatus(ServiceStatus.FAILURE);
		userRes.setTimestamp(new Timestamp(System.currentTimeMillis()));
		userRes.setTraceId(Long.MAX_VALUE);
	}

}
