/**
 * 
 */
package com.technojade.allybot.enpoint;

import java.sql.Timestamp;
import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.technojade.allybot.dtos.GroupUserDto;
import com.technojade.allybot.dtos.UserGroupDto;
import com.technojade.allybot.enpoint.response.AllyBotResponseDto;
import com.technojade.allybot.enpoint.response.ApiError;
import com.technojade.allybot.enpoint.response.ServiceStatus;
import com.technojade.allybot.entity.GroupMst;
import com.technojade.allybot.entity.UserGroup;
import com.technojade.allybot.service.UserGroupService;

/**
 * @author ugam.sharma
 *
 */
@RestController
@RequestMapping("/user_group")
public class UserGroupController {

	@Autowired
	private UserGroupService userGroupService;

	@PostMapping
	public ResponseEntity<?> saveUserGroup(@RequestBody UserGroupDto userGroupDto) {

		if (null == userGroupDto) {
			AllyBotResponseDto<UserGroup> userGroupRes = new AllyBotResponseDto<>();
			String errorMessage = "Something wen't wrong exist";
			String msg = "";
			noContent(userGroupRes, errorMessage, msg);
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(userGroupRes);
		}

		UserGroup userGroup = null;
		for (Long empId : userGroupDto.getEmpIds()) {
			userGroup = new UserGroup();
			BeanUtils.copyProperties(userGroupDto, userGroup);
			userGroup.setEmployeeId(empId);
			userGroupService.saveUserGroup(userGroup);
		}

		AllyBotResponseDto<UserGroupDto> userGroupRes = new AllyBotResponseDto<>();
		userGroupRes.setData(userGroupDto);
		userGroupRes.setServiceStatus(ServiceStatus.SUCCESS);
		userGroupRes.setTimestamp(new Timestamp(System.currentTimeMillis()));
		userGroupRes.setTraceId(Long.MAX_VALUE);
		userGroupRes.setMsg("User group has been saved successfully");
		return ResponseEntity.status(HttpStatus.CREATED).body(userGroupDto);
	}
	
	@PutMapping
	public ResponseEntity<?> updateUserGroup(@RequestBody UserGroupDto userGroupDto) {

		if (null == userGroupDto) {
			AllyBotResponseDto<UserGroup> userGroupRes = new AllyBotResponseDto<>();
			String errorMessage = "Something wen't wrong exist";
			String msg = "";
			noContent(userGroupRes, errorMessage, msg);
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(userGroupRes);
		}

		userGroupService.deleteUserGroupByGroupId(userGroupDto.getGroupId());
		
		UserGroup userGroup = null;
		for (Long empId : userGroupDto.getEmpIds()) {
			userGroup = new UserGroup();
			BeanUtils.copyProperties(userGroupDto, userGroup);
			userGroup.setEmployeeId(empId);
			userGroupService.updateUserGroup(userGroup);
		}

		AllyBotResponseDto<UserGroupDto> userGroupRes = new AllyBotResponseDto<>();
		userGroupRes.setData(userGroupDto);
		userGroupRes.setServiceStatus(ServiceStatus.SUCCESS);
		userGroupRes.setTimestamp(new Timestamp(System.currentTimeMillis()));
		userGroupRes.setTraceId(Long.MAX_VALUE);
		userGroupRes.setMsg("User group has been saved successfully");
		return ResponseEntity.status(HttpStatus.CREATED).body(userGroupDto);
	}

	@GetMapping("/{id}")
	public ResponseEntity<?> gettingUserGroup(@PathVariable("id") Long id) {

		List<GroupUserDto> userGroupByGroupId = userGroupService.getUserGroupByGroupId(id);

		if (userGroupByGroupId == null) {
			AllyBotResponseDto<UserGroup> userGroupRes = new AllyBotResponseDto<>();
			String errorMessage = "Something wen't wrong exist";
			String msg = "";
			noContent(userGroupRes, errorMessage, msg);
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(userGroupRes);
		}
		AllyBotResponseDto<List<GroupUserDto>> userGroupRes = new AllyBotResponseDto<>();
		userGroupRes.setData(userGroupByGroupId);
		userGroupRes.setServiceStatus(ServiceStatus.SUCCESS);
		userGroupRes.setTimestamp(new Timestamp(System.currentTimeMillis()));
		userGroupRes.setTraceId(Long.MAX_VALUE);
		return ResponseEntity.status(HttpStatus.OK).body(userGroupRes);
	}

	@GetMapping("/grp/{id}")
	public ResponseEntity<?> gettingUserGroupByGroupId(@PathVariable("id") Long id) {
		
		UserGroupDto userGroupByGroupId = userGroupService.getAllUserGroupByGroupId(id);
		
		if (userGroupByGroupId == null) {
			AllyBotResponseDto<UserGroup> userGroupRes = new AllyBotResponseDto<>();
			String errorMessage = "Something wen't wrong exist";
			String msg = "";
			noContent(userGroupRes, errorMessage, msg);
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(userGroupRes);
		}
		AllyBotResponseDto<UserGroupDto> userGroupRes = new AllyBotResponseDto<>();
		userGroupRes.setData(userGroupByGroupId);
		userGroupRes.setServiceStatus(ServiceStatus.SUCCESS);
		userGroupRes.setTimestamp(new Timestamp(System.currentTimeMillis()));
		userGroupRes.setTraceId(Long.MAX_VALUE);
		return ResponseEntity.status(HttpStatus.OK).body(userGroupRes);
	}

	@GetMapping("/{hotelId}/{clientId}")
	public ResponseEntity<?> gettingUserGroupByClientIdAndHotelId(@PathVariable("hotelId") Long hId,
			@PathVariable("clientId") Long cId) {

		List<GroupUserDto> byHotelIdAndClientId = userGroupService.getUserGroupByHotelIdAndClientId(hId, cId);
		if (byHotelIdAndClientId == null) {
			AllyBotResponseDto<UserGroup> userGroupRes = new AllyBotResponseDto<>();
			String errorMessage = "Something wen't wrong exist";
			String msg = "";
			noContent(userGroupRes, errorMessage, msg);
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(userGroupRes);
		}

		AllyBotResponseDto<List<GroupUserDto>> userGroupRes = new AllyBotResponseDto<>();
		userGroupRes.setData(byHotelIdAndClientId);
		userGroupRes.setServiceStatus(ServiceStatus.SUCCESS);
		userGroupRes.setTimestamp(new Timestamp(System.currentTimeMillis()));
		userGroupRes.setTraceId(Long.MAX_VALUE);
		return ResponseEntity.status(HttpStatus.OK).body(userGroupRes);
	}

	@DeleteMapping("/{gid}/{eid}")
	public ResponseEntity<?> deleteUserGroupEmp(@PathVariable("gid") Long gId, @PathVariable("eid") Long eId) {

		UserGroup deleteUserGroupByGrpIdAndEMpId = userGroupService.deleteUserGroupByGrpIdAndEMpId(gId, eId);
		if (deleteUserGroupByGrpIdAndEMpId == null) {
			AllyBotResponseDto<UserGroup> groupRes = new AllyBotResponseDto<>();
			String errorMessage = "Something wen't wrong exist";
			String msg = "";
			noContent(groupRes, errorMessage, msg);
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(groupRes);
		}
		AllyBotResponseDto<UserGroup> userGroupRes = new AllyBotResponseDto<>();
		userGroupRes.setData(deleteUserGroupByGrpIdAndEMpId);
		userGroupRes.setServiceStatus(ServiceStatus.SUCCESS);
		userGroupRes.setTimestamp(new Timestamp(System.currentTimeMillis()));
		userGroupRes.setTraceId(Long.MAX_VALUE);
		return ResponseEntity.status(HttpStatus.OK).body(userGroupRes);
	}

	private void noContent(AllyBotResponseDto<UserGroup> usergroup, String errorMessage, String msg) {
		ApiError error = new ApiError();
		error.setErrorCode("TJ10103");
		error.setErrorMessage(errorMessage);
		usergroup.setErrors(error);
		usergroup.setMsg(msg);
		usergroup.setServiceStatus(ServiceStatus.FAILURE);
		usergroup.setTimestamp(new Timestamp(System.currentTimeMillis()));
		usergroup.setTraceId(Long.MAX_VALUE);
	}

}
