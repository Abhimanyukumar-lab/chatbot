package com.technojade.allybot.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.technojade.allybot.entity.UserRole;
import com.technojade.allybot.repository.UserRoleRepository;

@Service
public class UserRoleServiceImpl implements UserRoleService {

	@Autowired
	private UserRoleRepository userRoleRepository;

	@Override
	public List<UserRole> listOfRole(Long hId) {
		return userRoleRepository.findAllByHotelId(hId);
	}

	@Override
	public Page<UserRole> listOfRole(Long hId, Pageable pageable) {
		return userRoleRepository.findAllByHotelId(hId, pageable);
	}

	@Override
	public UserRole createRole(UserRole role) {
		return userRoleRepository.save(role);
	}

	@Override
	public UserRole retrieveRole(Long roleId) {
		return userRoleRepository.findByRoleId(roleId);
	}

	@Override
	public void removeRole(Long roleId) {
		UserRole userRole = userRoleRepository.findByRoleId(roleId);
		userRoleRepository.delete(userRole);
	}

	@Override
	public UserRole userRoleById(Long id) {
		Optional<UserRole> findById = userRoleRepository.findById(id);
		if (findById.isPresent()) {
			return findById.get();
		}
		return null;
	}

}
