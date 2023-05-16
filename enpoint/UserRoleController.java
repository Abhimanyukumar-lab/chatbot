package com.technojade.allybot.enpoint;

import java.sql.Timestamp;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.technojade.allybot.dtos.PaginationDto;
import com.technojade.allybot.enpoint.response.AllyBotResponseDto;
import com.technojade.allybot.enpoint.response.ServiceStatus;
import com.technojade.allybot.entity.RoleTypeMst;
import com.technojade.allybot.entity.UserRole;
import com.technojade.allybot.service.RoleTypeService;
import com.technojade.allybot.service.UserRoleService;

import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/role")
@Slf4j
public class UserRoleController {

	@Autowired
	private UserRoleService userRoleService;

	@Autowired
	private RoleTypeService roleTypeService;

	@GetMapping("all/{hid}")
	public ResponseEntity<?> listOfRoleWithPagination(@PathVariable("hid") Long hId, @RequestParam("pageNo") int pageNo,
			@RequestParam("pageSize") int pageSize) {

		Pageable paging = PageRequest.of(pageNo - 1, pageSize);
		Page<UserRole> listOfRole = userRoleService.listOfRole(hId, paging);

		for (UserRole userRole : listOfRole) {
			if (userRole.getRoleTypeId() != null && userRole.getRoleTypeId() != 0) {
				RoleTypeMst roleType = roleTypeService.findByIdRoleType(userRole.getRoleTypeId());
				userRole.setRoleType(roleType.getRoleType());
			}
		}

		PaginationDto<List<UserRole>> userRolePagination = new PaginationDto<>();
		userRolePagination.setData(listOfRole.getContent());
		userRolePagination.setTotalElement(listOfRole.getTotalElements());
		userRolePagination.setTotalPage((long) listOfRole.getTotalPages());

		AllyBotResponseDto<PaginationDto<?>> response = new AllyBotResponseDto<>();
		response.setData(userRolePagination);
		response.setServiceStatus(ServiceStatus.SUCCESS);
		response.setTimestamp(new Timestamp(System.currentTimeMillis()));
		response.setTraceId(Long.MAX_VALUE);
		response.setMsg("UserRole information retireved");
		return ResponseEntity.status(HttpStatus.OK).body(response);
	}

	@GetMapping("/list/{hid}")
	public ResponseEntity<?> listOfRole(@PathVariable("hid") Long hId) {
		List<UserRole> listOfRole = userRoleService.listOfRole(hId);

		for (UserRole userRole : listOfRole) {
			if (userRole.getRoleTypeId() != null && userRole.getRoleTypeId() != 0) {
				RoleTypeMst roleType = roleTypeService.findByIdRoleType(userRole.getRoleTypeId());
				userRole.setRoleType(roleType.getRoleType());
			}
		}

		AllyBotResponseDto<List<UserRole>> response = new AllyBotResponseDto<>();
		response.setData(listOfRole);
		response.setServiceStatus(ServiceStatus.SUCCESS);
		response.setTimestamp(new Timestamp(System.currentTimeMillis()));
		response.setTraceId(Long.MAX_VALUE);
		response.setMsg("UserRole information retireved");
		return ResponseEntity.status(HttpStatus.OK).body(response);
	}

	@GetMapping("/{roleId}")
	public ResponseEntity<?> retriveRole(@PathVariable("roleId") Long roleId) {
		UserRole userRole = userRoleService.retrieveRole(roleId);
		AllyBotResponseDto<UserRole> response = new AllyBotResponseDto<>();
		response.setData(userRole);
		response.setServiceStatus(ServiceStatus.SUCCESS);
		response.setTimestamp(new Timestamp(System.currentTimeMillis()));
		response.setTraceId(Long.MAX_VALUE);
		response.setMsg("UserRole information retireved");
		return ResponseEntity.status(HttpStatus.OK).body(response);
	}

	@DeleteMapping("/{roleId}")
	public ResponseEntity<?> removeRole(@PathVariable("roleId") Long roleId) {
		userRoleService.removeRole(roleId);
		AllyBotResponseDto<String> response = new AllyBotResponseDto<>();
		response.setData("Role id: " + roleId);
		response.setServiceStatus(ServiceStatus.SUCCESS);
		response.setTimestamp(new Timestamp(System.currentTimeMillis()));
		response.setTraceId(Long.MAX_VALUE);
		response.setMsg("Role removed successfully");
		return ResponseEntity.status(HttpStatus.OK).body(response);
	}

	@PostMapping("/{hid}")
	public ResponseEntity<?> createRole(@PathVariable("hid") Long hId, @RequestBody UserRole userRole) {
		AllyBotResponseDto<UserRole> response = new AllyBotResponseDto<>();
		userRole.setHotelId(hId);
		UserRole persistedRole = userRoleService.createRole(userRole);
		if (persistedRole.getRoleId() != null) {
			response.setData(persistedRole);
			response.setServiceStatus(ServiceStatus.SUCCESS);
			response.setTimestamp(new Timestamp(System.currentTimeMillis()));
			response.setTraceId(Long.MAX_VALUE);
			response.setMsg("UserRole created successfully");
			return ResponseEntity.status(HttpStatus.OK).body(response);
		} else {
			response.setData(null);
			response.setServiceStatus(ServiceStatus.FAILURE);
			response.setTimestamp(new Timestamp(System.currentTimeMillis()));
			response.setTraceId(Long.MAX_VALUE);
			response.setMsg("Something went wrong!");
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
		}
	}

}
