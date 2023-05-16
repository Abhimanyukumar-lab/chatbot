package com.technojade.allybot.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.technojade.allybot.entity.UserGroup;

@Repository
public interface UserGroupRepository extends JpaRepository<UserGroup, Long>{

	public List<UserGroup> findAllByGroupId(Long id);
	
	public List<UserGroup> findAllByHotelIdAndClientId(long hId, long cId);
	
	public Optional<UserGroup> findByGroupIdAndEmployeeId(Long gId, Long eId);
	
	public void deleteAllByGroupId(Long gId);
	
	public void deleteByGroupIdAndEmployeeId(Long gId, Long eId);
	
}
