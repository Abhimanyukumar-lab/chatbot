package com.technojade.allybot.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.technojade.allybot.entity.UserRole;

public interface UserRoleService {

	public List<UserRole> listOfRole(Long hId);

	public Page<UserRole> listOfRole(Long hId, Pageable pageable);

	public UserRole userRoleById(Long id);

	public UserRole createRole(UserRole role);

	public UserRole retrieveRole(Long roleId);

	public void removeRole(Long roleId);
}
