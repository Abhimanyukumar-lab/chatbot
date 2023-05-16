package com.technojade.allybot.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.technojade.allybot.entity.RoleTypeMst;

@Repository
public interface RoleTypeRepository extends JpaRepository<RoleTypeMst, Long>{

}
