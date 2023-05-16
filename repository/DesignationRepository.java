package com.technojade.allybot.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.technojade.allybot.entity.Designation;

@Repository
public interface DesignationRepository extends JpaRepository<Designation, Long>{

}
