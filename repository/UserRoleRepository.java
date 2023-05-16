package com.technojade.allybot.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.technojade.allybot.entity.UserRole;

@Repository
public interface UserRoleRepository extends JpaRepository<UserRole, Long> {

	public UserRole findByRoleId(Long roleId);

	public Page<UserRole> findAllByHotelId(Long hId, Pageable page);

	public List<UserRole> findAllByHotelId(Long hId);

}
