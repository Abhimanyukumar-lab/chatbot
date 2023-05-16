package com.technojade.allybot.enpoint;

import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.technojade.allybot.dtos.GroupDto;
import com.technojade.allybot.dtos.PaginationDto;
import com.technojade.allybot.enpoint.response.AllyBotResponseDto;
import com.technojade.allybot.enpoint.response.ApiError;
import com.technojade.allybot.enpoint.response.ServiceStatus;
import com.technojade.allybot.entity.GroupMst;
import com.technojade.allybot.entity.GroupType;
import com.technojade.allybot.service.GroupService;
import com.technojade.allybot.service.GroupTypeService;

import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/group")
@Slf4j
public class GroupController {

	@Autowired
	private GroupService groupService;

	@Autowired
	private GroupTypeService groupTypeService;

	@PostMapping("/{hid}")
	public ResponseEntity<?> saveGroupDetails(@PathVariable("hid") Long hId, @Valid @RequestBody GroupMst groupMst) {

		Optional<GroupMst> groupResponse = Optional.ofNullable(groupMst);

		if (null == groupMst || !groupResponse.isPresent()) {
			AllyBotResponseDto<GroupMst> groupRes = new AllyBotResponseDto<>();
			String errorMessage = "Something wen't wrong exist";
			String msg = "";
			noContent(groupRes, errorMessage, msg);
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(groupRes);
		}
		groupMst.setHotelId(hId);
		GroupMst groupDetailResponse = groupService.saveGroupDetail(groupMst);
		GroupDto groupDto = new GroupDto();
		groupDto.setName(groupMst.getName());
		groupDto.setGroupTypeId(groupMst.getGroupTypeId());
		groupDto.setDescription(groupMst.getDescription());

		AllyBotResponseDto<GroupMst> groupRes = new AllyBotResponseDto<>();
		groupRes.setData(groupDetailResponse);
		groupRes.setServiceStatus(ServiceStatus.SUCCESS);
		groupRes.setTimestamp(new Timestamp(System.currentTimeMillis()));
		groupRes.setTraceId(Long.MAX_VALUE);
		groupRes.setMsg("Group details has been saved successfully");
		return ResponseEntity.status(HttpStatus.CREATED).body(groupDto);

	}

	@GetMapping("all/{hid}")
	public ResponseEntity<?> listOfGroupDetails(@PathVariable("hid") Long hId, @RequestParam("pageNo") int pageNo,
			@RequestParam("pageSize") int pageSize) {

		Pageable pageable = PageRequest.of(pageNo - 1, pageSize, Sort.by(Sort.Direction.DESC, "id"));

		Page<GroupMst> listOfGroupDetails = groupService.listOfGroupDetails(hId, pageable);

		PaginationDto<List<GroupMst>> groupPaginator = new PaginationDto<>();
		groupPaginator.setData(listOfGroupDetails.getContent());
		groupPaginator.setTotalElement(listOfGroupDetails.getTotalElements());
		groupPaginator.setTotalPage((long) listOfGroupDetails.getTotalPages());

		for (GroupMst groupMst : listOfGroupDetails.getContent()) {
			if (groupMst.getGroupTypeId() != null && groupMst.getGroupTypeId() != 0) {
				GroupType groupName = groupTypeService.groupTypeById(groupMst.getGroupTypeId());
				groupMst.setGroupTypeName(groupName.getName());
			}
		}

		AllyBotResponseDto<PaginationDto<?>> groupRes = new AllyBotResponseDto<>();
		groupRes.setData(groupPaginator);
		groupRes.setServiceStatus(ServiceStatus.SUCCESS);
		groupRes.setTimestamp(new Timestamp(System.currentTimeMillis()));
		groupRes.setTraceId(Long.MAX_VALUE);
		return ResponseEntity.status(HttpStatus.OK).body(groupRes);
	}

	@GetMapping("list/{hid}")
	public ResponseEntity<?> listOfGroup(@PathVariable("hid") Long hId) {
		List<GroupMst> groupDetails = groupService.listOfGroupDetails(hId);
		return ResponseEntity.status(HttpStatus.OK).body(groupDetails);
	}

	@GetMapping("/{id}")
	public ResponseEntity<?> groupDetailsById(@PathVariable("id") Long id) {

		GroupMst groupDetailById = groupService.getGroupDetailById(id);

		if (groupDetailById == null) {
			AllyBotResponseDto<GroupMst> groupRes = new AllyBotResponseDto<>();
			String errorMessage = "Something wen't wrong exist";
			String msg = "";
			noContent(groupRes, errorMessage, msg);
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(groupRes);
		}

		if (groupDetailById.getGroupTypeId() != null && groupDetailById.getGroupTypeId() != 0) {
			GroupType groupName = groupTypeService.groupTypeById(groupDetailById.getGroupTypeId());
			groupDetailById.setGroupTypeName(groupName.getName());
		}

		AllyBotResponseDto<GroupMst> groupRes = new AllyBotResponseDto<>();
		groupRes.setData(groupDetailById);
		groupRes.setServiceStatus(ServiceStatus.SUCCESS);
		groupRes.setTimestamp(new Timestamp(System.currentTimeMillis()));
		groupRes.setTraceId(Long.MAX_VALUE);
		return ResponseEntity.status(HttpStatus.OK).body(groupRes);
	}

	@PutMapping("/{hid}")
	public ResponseEntity<?> updateGroupDetails(@PathVariable("hid") Long hId, @RequestBody GroupMst groupMst) {
		Optional<GroupMst> groupResponse = Optional.ofNullable(groupMst);

		if (null == groupMst || !groupResponse.isPresent()) {
			AllyBotResponseDto<GroupMst> groupRes = new AllyBotResponseDto<>();
			String errorMessage = "Something wen't wrong exist";
			String msg = "";
			noContent(groupRes, errorMessage, msg);
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(groupRes);
		}
		groupMst.setHotelId(hId);
		GroupMst groupDetailResponse = groupService.updateGroupDetail(groupMst);
		GroupDto groupDto = new GroupDto();
		groupDto.setName(groupMst.getName());
		groupDto.setGroupTypeId(groupMst.getGroupTypeId());
		groupDto.setDescription(groupMst.getDescription());

		AllyBotResponseDto<GroupMst> groupRes = new AllyBotResponseDto<>();
		groupRes.setData(groupDetailResponse);
		groupRes.setServiceStatus(ServiceStatus.SUCCESS);
		groupRes.setTimestamp(new Timestamp(System.currentTimeMillis()));
		groupRes.setTraceId(Long.MAX_VALUE);
		groupRes.setMsg("Group details has been updated successfully");
		return ResponseEntity.status(HttpStatus.CREATED).body(groupDto);
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<?> deleteGroupById(@PathVariable("id") Long id) {

		GroupMst deleteGroupDetailById = groupService.deleteGroupDetailById(id);

		if (deleteGroupDetailById == null) {
			AllyBotResponseDto<GroupMst> groupRes = new AllyBotResponseDto<>();
			String errorMessage = "Something wen't wrong exist";
			String msg = "";
			noContent(groupRes, errorMessage, msg);
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(groupRes);
		}
		AllyBotResponseDto<GroupMst> groupRes = new AllyBotResponseDto<>();
		groupRes.setData(deleteGroupDetailById);
		groupRes.setServiceStatus(ServiceStatus.SUCCESS);
		groupRes.setTimestamp(new Timestamp(System.currentTimeMillis()));
		groupRes.setTraceId(Long.MAX_VALUE);
		return ResponseEntity.status(HttpStatus.CREATED).body(groupRes);
	}

	private void noContent(AllyBotResponseDto<GroupMst> group, String errorMessage, String msg) {
		ApiError error = new ApiError();
		error.setErrorCode("TJ10103");
		error.setErrorMessage(errorMessage);
		group.setErrors(error);
		group.setMsg(msg);
		group.setServiceStatus(ServiceStatus.FAILURE);
		group.setTimestamp(new Timestamp(System.currentTimeMillis()));
		group.setTraceId(Long.MAX_VALUE);
	}

}
