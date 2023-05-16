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
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
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
import com.technojade.allybot.dtos.EmployeeDto;
import com.technojade.allybot.dtos.EmployeeProfileDto;
import com.technojade.allybot.dtos.EmployeeProfileUpdate;
import com.technojade.allybot.dtos.FetchEmployeeProfileDto;
import com.technojade.allybot.dtos.PaginationDto;
import com.technojade.allybot.enpoint.response.AllyBotResponseDto;
import com.technojade.allybot.enpoint.response.ApiError;
import com.technojade.allybot.enpoint.response.ServiceStatus;
import com.technojade.allybot.entity.Address;
import com.technojade.allybot.entity.City;
import com.technojade.allybot.entity.Department;
import com.technojade.allybot.entity.Designation;
import com.technojade.allybot.entity.Employee;
import com.technojade.allybot.entity.FileInformation;
import com.technojade.allybot.entity.GroupMst;
import com.technojade.allybot.entity.State;
import com.technojade.allybot.entity.User;
import com.technojade.allybot.entity.UserRole;
import com.technojade.allybot.service.AddressService;
import com.technojade.allybot.service.CityService;
import com.technojade.allybot.service.DepartmentService;
import com.technojade.allybot.service.DesignationService;
import com.technojade.allybot.service.EmployeeService;
import com.technojade.allybot.service.FileUploadService;
import com.technojade.allybot.service.GroupService;
import com.technojade.allybot.service.OtpService;
import com.technojade.allybot.service.StateService;
import com.technojade.allybot.service.UserRoleService;

import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/employee")
@Slf4j
public class EmployeeController {

	@Autowired
	private EmployeeService employeeService;

	@Autowired
	private OtpService otpService;

	@Autowired
	private AddressService addressService;

	@Autowired
	private DesignationService designationService;

	@Autowired
	private DepartmentService departmentService;

	@Autowired
	private StateService stateService;

	@Autowired
	private CityService cityService;

	@Autowired
	private UserRoleService userRoleService;

	@Autowired
	private FileUploadService fileUploadService;

	@Autowired
	private ObjectMapper objectMapper;
	
	@Autowired
	private GroupService groupService;

	@PostMapping("/saveEmployee")
	public ResponseEntity<?> saveEmployeeDetails(@Valid @RequestBody Employee employee) {
		Employee saveEmployee = employeeService.saveEmployee(employee);
		AllyBotResponseDto<Employee> employeeRes = new AllyBotResponseDto<>();
		employeeRes.setData(saveEmployee);
		employeeRes.setServiceStatus(ServiceStatus.SUCCESS);
		employeeRes.setTimestamp(new Timestamp(System.currentTimeMillis()));
		employeeRes.setTraceId(Long.MAX_VALUE);
		employeeRes.setMsg("Employee details has been saved successfully");
		return ResponseEntity.status(HttpStatus.CREATED).body(employeeRes);
	}

	@GetMapping("/all/{clientId}/{hotelId}")
	public ResponseEntity<?> getAllEmployee(@PathVariable("hotelId") Long hotelId,
			@PathVariable("clientId") Long clientId, @RequestParam("pageNo") int pageNo,
			@RequestParam("pageSize") int pageSize) {

		Pageable paging = PageRequest.of(pageNo - 1, pageSize);
		PaginationDto<?> empResponse = employeeService.findAllByClientIdAndHotelId(clientId, hotelId, paging);

		AllyBotResponseDto<PaginationDto<?>> empRes = new AllyBotResponseDto<>();
		empRes.setData(empResponse);
		empRes.setServiceStatus(ServiceStatus.SUCCESS);
		empRes.setTimestamp(new Timestamp(System.currentTimeMillis()));
		empRes.setTraceId(Long.MAX_VALUE);
		return ResponseEntity.status(HttpStatus.OK).body(empRes);
	}

	@GetMapping("/list/{clientId}/{hotelId}")
	public ResponseEntity<?> getAllEmployee(@PathVariable("hotelId") Long hotelId,
			@PathVariable("clientId") Long clientId) {

		List<EmployeeDto> empResponse = employeeService.findAllByClientIdAndHotelId(clientId, hotelId);
		AllyBotResponseDto<List<EmployeeDto>> empRes = new AllyBotResponseDto<>();
		empRes.setData(empResponse);
		empRes.setServiceStatus(ServiceStatus.SUCCESS);
		empRes.setTimestamp(new Timestamp(System.currentTimeMillis()));
		empRes.setTraceId(Long.MAX_VALUE);
		return ResponseEntity.status(HttpStatus.OK).body(empRes);
	}

	// error handling method
	private void noContent(AllyBotResponseDto<Employee> employee, String errorMessage, String msg) {
		ApiError error = new ApiError();
		error.setErrorCode("TJ10103");
		error.setErrorMessage(errorMessage);
		employee.setErrors(error);
		employee.setMsg(msg);
		employee.setServiceStatus(ServiceStatus.FAILURE);
		employee.setTimestamp(new Timestamp(System.currentTimeMillis()));
		employee.setTraceId(Long.MAX_VALUE);
	}

	@GetMapping("/{id}")
	public ResponseEntity<?> getEmployeeById(@PathVariable("id") Long id) {
		Optional<Employee> employeeById = employeeService.findEmployeeById(id);

		if (!employeeById.isPresent()) {
			AllyBotResponseDto<Employee> employeeRes = new AllyBotResponseDto<>();
			String errorMessage = "Something wen't wrong exist";
			String msg = "";
			noContent(employeeRes, errorMessage, msg);
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(employeeRes);
		}

		FetchEmployeeProfileDto profileDto = new FetchEmployeeProfileDto();
		Employee employee = employeeById.get();
		Address address = new Address();

		if (employee.getAddressId() != null && employee.getAddressId() != 0) {
			address = addressService.fetchAddressById(employee.getAddressId()).get();
		}

		if (employee.getDepartmentId() != null && employee.getDepartmentId() != 0) {
//			Department department = departmentService.findById(employee.getDepartmentId());
			GroupMst groupMst = groupService.getGroupDetailById(employee.getDepartmentId());
			profileDto.setDepartment(groupMst != null ?groupMst.getName():"");
			profileDto.setDepartmentId(employee.getDepartmentId());
		}

		if (employee.getDesignationId() != null && employee.getDesignationId() != 0) {
			Designation designation = designationService.findById(employee.getDesignationId());
			profileDto.setDesignation(designation.getName());
			profileDto.setDesignationId(employee.getDesignationId());
		}

		if (address.getCityId() != null && address.getCityId() != 0) {
			City cityById = cityService.cityById(address.getCityId());
			profileDto.setCity(cityById.getCity());
		}

		if (address.getStateId() != null && address.getStateId() != 0) {
			State stateById = stateService.stateById(address.getStateId());
			profileDto.setState(stateById.getName());
		}

		if (employee.getRoleId() != null && employee.getRoleId() != 0) {
			UserRole roleById = userRoleService.userRoleById(employee.getRoleId());
			if (roleById != null) {
				profileDto.setRole(roleById.getName());
				profileDto.setRoleId(employee.getRoleId());
			}
		}

		BeanUtils.copyProperties(employee, profileDto);
		profileDto.setAddressObj(address);

		AllyBotResponseDto<FetchEmployeeProfileDto> empRes = new AllyBotResponseDto<>();
		empRes.setData(profileDto);
		empRes.setServiceStatus(ServiceStatus.SUCCESS);
		empRes.setTimestamp(new Timestamp(System.currentTimeMillis()));
		empRes.setTraceId(Long.MAX_VALUE);
		return ResponseEntity.status(HttpStatus.OK).body(empRes);
	}

	@PostMapping("/login")
	public ResponseEntity<?> login(@RequestBody JsonNode jsonNode, String mobileNumber) {

		AllyBotResponseDto<Employee> empRes = new AllyBotResponseDto<>();
		AllyBotResponseDto<EmployeeDto> empDtoRes = new AllyBotResponseDto<>();
		if (null != mobileNumber && !mobileNumber.isEmpty()) {
			Employee empEntiry = employeeService.loginByMobileNumber(mobileNumber);
			if (null == empEntiry) {
				String errorMessage = "Mobile number does not exist.";
				String msg = "Invalid mobile number.";
				notFound(empRes, errorMessage, msg);
				return ResponseEntity.status(HttpStatus.NOT_FOUND).body(empRes);
			}
			String otp = otpService.sendOtpOnly(mobileNumber);
			empEntiry.setOtp(otp);
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
			Employee emptemp = new Employee();
			emptemp.setEmail(jsonNode.get("email").asText());
			emptemp.setPassword(jsonNode.get("password").asText());

			Employee empResponse = employeeService.login(emptemp);
			if (null == empResponse) {
				String errorMessage = "Employee does not exist.";
				String msg = "Invalid email or password.";
				notFound(empRes, errorMessage, msg);
				return ResponseEntity.status(HttpStatus.NOT_FOUND).body(empRes);
			}

			EmployeeDto employeeDto = new EmployeeDto();
			BeanUtils.copyProperties(empResponse, employeeDto);
			String roleName = userRoleService.userRoleById(empResponse.getRoleId()).getName();
			employeeDto.setRole(roleName);

			empDtoRes.setData(employeeDto);
			empDtoRes.setServiceStatus(ServiceStatus.SUCCESS);
			empDtoRes.setTimestamp(new Timestamp(System.currentTimeMillis()));
			empDtoRes.setTraceId(Long.MAX_VALUE);
		}
		empDtoRes.setMsg("Logged in successfully");
		return ResponseEntity.status(HttpStatus.OK).body(empDtoRes);
	}

	@PostMapping(value = "/otp/validate")
	public ResponseEntity<?> validate(@RequestBody JsonNode otpReqDto) {
		boolean isValidated = otpService.validate(otpReqDto.get("contactNo").asText(), otpReqDto.get("otp").asText());
		if (!isValidated) {
			AllyBotResponseDto<Employee> otpRes = new AllyBotResponseDto<>();
			otpRes.setData(null);
			otpRes.setServiceStatus(ServiceStatus.SUCCESS);
			otpRes.setMsg("OTP is invalid");
			otpRes.setTraceId(Long.MAX_VALUE);
			otpRes.setTimestamp(Timestamp.from(Instant.now()));
			return ResponseEntity.status(HttpStatus.OK).body(otpRes);
		}
		Employee employee = employeeService.loginByMobileNumber(otpReqDto.get("contactNo").asText());
		AllyBotResponseDto<Employee> otpRes = new AllyBotResponseDto<>();
		otpRes.setData(employee);
		otpRes.setServiceStatus(ServiceStatus.SUCCESS);
		otpRes.setMsg("Otp validated, Employee will be logging now.");
		otpRes.setTraceId(Long.MAX_VALUE);
		otpRes.setTimestamp(Timestamp.from(Instant.now()));
		return ResponseEntity.status(HttpStatus.OK).body(otpRes);
	}

	/*
	 * @PutMapping public ResponseEntity<?> updateEmployeeDetails(@RequestBody
	 * EmployeeProfileDto employeeProfileDto) {
	 * 
	 * Address address = new Address();
	 * address.setAddressId(employeeProfileDto.getAddressId());
	 * address.setAddress_1(employeeProfileDto.getAddress());
	 * address.setStateId(employeeProfileDto.getStateId());
	 * address.setCityId(employeeProfileDto.getCityId()); // save and update address
	 * Address saveAddress = addressService.saveAddress(address);
	 * 
	 * Optional<Employee> employeeById =
	 * employeeService.findEmployeeById(employeeProfileDto.getEmpId());
	 * 
	 * if (!employeeById.isPresent()) { AllyBotResponseDto<Employee> employeeRes =
	 * new AllyBotResponseDto<>(); String errorMessage =
	 * "Something wen't wrong exist"; String msg = ""; noContent(employeeRes,
	 * errorMessage, msg); return
	 * ResponseEntity.status(HttpStatus.BAD_REQUEST).body(employeeRes); }
	 * 
	 * Employee employee = employeeById.get();
	 * BeanUtils.copyProperties(employeeProfileDto, employee);
	 * employee.setAddressId(saveAddress.getAddressId());
	 * 
	 * Employee updateEmployee = employeeService.updateEmployee(employee);
	 * AllyBotResponseDto<Employee> employeeRes = new AllyBotResponseDto<>();
	 * employeeRes.setData(updateEmployee);
	 * employeeRes.setServiceStatus(ServiceStatus.SUCCESS);
	 * employeeRes.setTimestamp(new Timestamp(System.currentTimeMillis()));
	 * employeeRes.setTraceId(Long.MAX_VALUE);
	 * employeeRes.setMsg("Employee details has been updated successfully"); return
	 * ResponseEntity.status(HttpStatus.CREATED).body(employeeRes); }
	 */

	@PutMapping
	public ResponseEntity<?> updateEmployeeDetails(@RequestBody Employee employee) {

		Optional<Employee> employeeById = employeeService.findEmployeeById(employee.getEmpId());

		if (!employeeById.isPresent()) {
			AllyBotResponseDto<Employee> employeeRes = new AllyBotResponseDto<>();
			String errorMessage = "Something wen't wrong exist";
			String msg = "";
			noContent(employeeRes, errorMessage, msg);
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(employeeRes);
		}

		Employee employees = employeeById.get();
		employee.setAddressId(employees.getAddressId());
		employee.setAlternateContactNo(employees.getAlternateContactNo());
		employee.setActive(employees.isActive());
		employee.setProfileImageURL(employees.getProfileImageURL());
		employee.setHotelId(employees.getHotelId());
		employee.setClientId(employees.getClientId());
		
		BeanUtils.copyProperties(employee, employees);

		Employee updateEmployee = employeeService.updateEmployee(employee);
		AllyBotResponseDto<Employee> employeeRes = new AllyBotResponseDto<>();
		employeeRes.setData(updateEmployee);
		employeeRes.setServiceStatus(ServiceStatus.SUCCESS);
		employeeRes.setTimestamp(new Timestamp(System.currentTimeMillis()));
		employeeRes.setTraceId(Long.MAX_VALUE);
		employeeRes.setMsg("Employee details has been updated successfully");
		return ResponseEntity.status(HttpStatus.CREATED).body(employeeRes);
	}

	@PutMapping("/profile")
	public ResponseEntity<?> updateEmployeeProfileDetails(@RequestBody EmployeeProfileUpdate employeeProfileUpdate) {

		Address address = new Address();
		address.setAddressId(employeeProfileUpdate.getAddressId());
		address.setAddress_1(employeeProfileUpdate.getAddress());
		address.setStateId(employeeProfileUpdate.getStateId());
		address.setCityId(employeeProfileUpdate.getCityId());
		// save and update address
		Address saveAddress = addressService.saveAddress(address);

		Optional<Employee> employeeById = employeeService.findEmployeeById(employeeProfileUpdate.getEmpId());

		if (!employeeById.isPresent()) {
			AllyBotResponseDto<Employee> employeeRes = new AllyBotResponseDto<>();
			String errorMessage = "Something wen't wrong exist";
			String msg = "";
			noContent(employeeRes, errorMessage, msg);
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(employeeRes);
		}

		Employee employee = employeeById.get();
		BeanUtils.copyProperties(employeeProfileUpdate, employee);
		employee.setAddressId(saveAddress.getAddressId());

		Employee updateEmployee = employeeService.updateEmployee(employee);
		AllyBotResponseDto<Employee> employeeRes = new AllyBotResponseDto<>();
		employeeRes.setData(updateEmployee);
		employeeRes.setServiceStatus(ServiceStatus.SUCCESS);
		employeeRes.setTimestamp(new Timestamp(System.currentTimeMillis()));
		employeeRes.setTraceId(Long.MAX_VALUE);
		employeeRes.setMsg("Employee details has been updated successfully");
		return ResponseEntity.status(HttpStatus.CREATED).body(employeeRes);
	}

	private AllyBotResponseDto<Employee> resetPasswordResponse(Employee empResponse) {
		AllyBotResponseDto<Employee> empRes;
		empRes = new AllyBotResponseDto<>();
		empRes.setMsg("Password has been changed successfully");
		empRes.setData(empResponse);
		empRes.setServiceStatus(ServiceStatus.SUCCESS);
		empRes.setTimestamp(new Timestamp(System.currentTimeMillis()));
		empRes.setTraceId(Long.MAX_VALUE);
		return empRes;
	}

	@PostMapping("/changepassword")
	public ResponseEntity<?> changePassword(@RequestBody JsonNode jsonNode,
			@RequestParam(name = "type", required = false) String requestType) {

		AllyBotResponseDto<Employee> empRes = new AllyBotResponseDto<>();

		// validation for change password
		if (null != requestType && requestType.equals("changepassword")) {
			if (null == jsonNode.get("oldPassword").asText()) {
				String errorMessage = "";
				String msg = "Old password is empty";
				empRes.setData(null);
				badRequest(empRes, errorMessage, msg);
				return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(empRes);
			}
			Optional<Employee> empEntityData = employeeService.findEmployeeById(jsonNode.get("empId").asLong());
			Employee empEntity = empEntityData.get();

			if (!jsonNode.get("oldPassword").asText().equals(empEntity.getPassword())) {
				String errorMessage = "";
				String msg = "Old password did not match";
				empRes.setData(null);
				badRequest(empRes, errorMessage, msg);
				return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(empRes);
			}

			if (!jsonNode.get("newPassword").asText().equals(jsonNode.get("confirmPassword").asText())) {
				String errorMessage = "Password and confirm password must be same";
				String msg = "Confirm password did not match";
				empRes.setData(null);
				badRequest(empRes, errorMessage, msg);
				return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(empRes);
			}

			// everything looks good for change password
			empEntity.setPassword(jsonNode.get("newPassword").asText());
			employeeService.updateEmployee(empEntity);
			empRes = resetPasswordResponse(empEntity);
			return ResponseEntity.status(HttpStatus.OK).body(empRes);

		} else {
			if (!jsonNode.get("newPassword").asText().equals(jsonNode.get("confirmPassword").asText())) {
				String errorMessage = "Password and confirm password must be same";
				String msg = "Confirm password did not match";
				empRes.setData(null);
				badRequest(empRes, errorMessage, msg);
				return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(empRes);
			}

			Employee empTemp = new Employee();
			empTemp.setEmail(jsonNode.get("email").asText());
			empTemp.setContactNo(jsonNode.get("contactNo").asText());
			empTemp.setOldPassword(jsonNode.get("oldPassword").asText());
			empTemp.setConfirmPassword(jsonNode.get("confirmPassword").asText());
			empTemp.setNewPassword(jsonNode.get("newPassword").asText());
			// forget password case
			Employee empResponse = employeeService.findByMobileNumberOrEmailId(empTemp);
			empResponse.setPassword(empTemp.getNewPassword());
			employeeService.updateEmployee(empResponse);
			empRes = resetPasswordResponse(empResponse);
			return ResponseEntity.status(HttpStatus.OK).body(empRes);
		}
	}

	@PostMapping("/verifyemailorcontact")
	public ResponseEntity<?> emailOrMobileVerifiation(@RequestBody JsonNode jsonNode) {

		AllyBotResponseDto<Employee> empRes = new AllyBotResponseDto<>();

		Employee empTemp = new Employee();
		empTemp.setEmail(jsonNode.get("email").asText());
		Employee empResponse = employeeService.findByMobileNumberOrEmailId(empTemp);

		if (null == empResponse) {
			String errorMessage = "User does not exist.";
			String msg = "Invalid email";
			notFound(empRes, errorMessage, msg);
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(empRes);
		}

		empRes.setData(empResponse);
		empRes.setMsg("Email is validated");
		empRes.setServiceStatus(ServiceStatus.SUCCESS);
		empRes.setTimestamp(new Timestamp(System.currentTimeMillis()));
		empRes.setTraceId(Long.MAX_VALUE);
		return ResponseEntity.status(HttpStatus.OK).body(empRes);
	}

	@PostMapping("/forgetpassword")
	public ResponseEntity<?> forgetPassword(@RequestBody JsonNode jsonNode) {

		AllyBotResponseDto<Employee> empRes = new AllyBotResponseDto<>();

		Optional<Employee> empEntity = employeeService.findEmployeeById(jsonNode.get("empId").asLong());
		Employee employee = empEntity.get();

		if (!jsonNode.get("newPassword").asText().equals(jsonNode.get("confirmPassword").asText())) {
			String errorMessage = "Password and confirm password must be same";
			String msg = "Confirm password did not match";
			empRes.setData(null);
			badRequest(empRes, errorMessage, msg);
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(empRes);
		}
		employee.setPassword(jsonNode.get("newPassword").asText());
		employeeService.updateEmployee(employee);
		empRes = resetPasswordResponse(employee);
		return ResponseEntity.status(HttpStatus.OK).body(empRes);
	}

	@PostMapping("/image/{empid}")
	public ResponseEntity<?> updateImage(@PathVariable("empid") String empid,
			@RequestParam("file") MultipartFile file) {
		AllyBotResponseDto<FileInformation> userRes = new AllyBotResponseDto<>();

		try {
			FileInformation fileInform = fileUploadService.uploadFile(file, empid, 1);
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

	@GetMapping("/image/{empid}")
	public ResponseEntity<?> getImage(@PathVariable("empid") String empid, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		// String fileName = "imagespgAdmin.PNG";
		FileInformation fileInformation = fileUploadService.getFile(empid, 1);
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
	public ResponseEntity<?> deleteEmployee(@PathVariable("id") long id) {

		Employee employeeById = employeeService.deleteEmployeeById(id);

		if (employeeById == null) {
			AllyBotResponseDto<Employee> empRes = new AllyBotResponseDto<>();
			String errorMessage = "Something wen't wrong";
			String msg = "";
			notFound(empRes, errorMessage, msg);
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(empRes);
		}

		AllyBotResponseDto<Employee> empRes = new AllyBotResponseDto<>();
		empRes.setData(employeeById);
		empRes.setServiceStatus(ServiceStatus.SUCCESS);
		empRes.setTimestamp(new Timestamp(System.currentTimeMillis()));
		empRes.setTraceId(Long.MAX_VALUE);
		return ResponseEntity.status(HttpStatus.OK).body(empRes);

	}

	private void notFound(AllyBotResponseDto<Employee> empRes, String errorMessage, String msg) {
		empRes.setData(null);
		ApiError error = new ApiError();
		error.setErrorCode("TJ10102");
		error.setErrorMessage(errorMessage);
		empRes.setErrors(error);
		empRes.setMsg(msg);
		empRes.setServiceStatus(ServiceStatus.FAILURE);
		empRes.setTimestamp(new Timestamp(System.currentTimeMillis()));
		empRes.setTraceId(Long.MAX_VALUE);
	}

	private void badRequest(AllyBotResponseDto<Employee> empRes, String errorMessage, String msg) {
		ApiError error = new ApiError();
		error.setErrorCode("TJ10101");
		error.setErrorMessage(errorMessage);
		empRes.setErrors(error);
		empRes.setMsg(msg);
		empRes.setServiceStatus(ServiceStatus.FAILURE);
		empRes.setTimestamp(new Timestamp(System.currentTimeMillis()));
		empRes.setTraceId(Long.MAX_VALUE);
	}

}
