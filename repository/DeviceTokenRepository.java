package com.technojade.allybot.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.technojade.allybot.entity.DeviceTokenMst;

@Repository
public interface DeviceTokenRepository extends JpaRepository<DeviceTokenMst, Long> {

	public Optional<DeviceTokenMst> findByUserid(Long uId);

}
