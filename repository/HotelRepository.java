/**
 * 
 */
package com.technojade.allybot.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.technojade.allybot.entity.Hotel;

/**
 * @author ugam.sharma
 *
 */
@Repository
public interface HotelRepository extends JpaRepository<Hotel, Long>{

	@Query("select f from Hotel f where f.clientId=:clientId")
	public List<Hotel> findHotelsByClientId(@Param("clientId") Long clientId);

	@Query("select f from Hotel f where f.empId=:empId")
	public List<Hotel> findHotelsById(@Param("empId") Long empId);
	
}
