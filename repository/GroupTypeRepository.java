package com.technojade.allybot.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.technojade.allybot.entity.GroupType;

@Repository
public interface GroupTypeRepository extends JpaRepository<GroupType, Long>{

}
