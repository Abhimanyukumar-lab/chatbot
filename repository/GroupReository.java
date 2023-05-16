package com.technojade.allybot.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.technojade.allybot.entity.GroupMst;

@Repository
public interface GroupReository extends JpaRepository<GroupMst, Long>{

	public Page<GroupMst> findAllByHotelId(Long hId, Pageable page);

	public List<GroupMst> findAllByHotelId(Long hId);
	
	
	
}
