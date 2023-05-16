package com.technojade.allybot.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.technojade.allybot.entity.FaqMst;

@Repository
public interface FaqRepository extends JpaRepository<FaqMst, Long>{

}
