/**
 * 
 */
package com.technojade.allybot.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.technojade.allybot.entity.HotelRating;

/**
 * @author ugam.sharma
 *
 */
@Repository
public interface HotelRatingRepository extends JpaRepository<HotelRating, Long> {

	@Query("select r from HotelRating r where hotelId in(:hotelIds)")
	public List<HotelRating> findByHotelIds(@Param("hotelIds") List<Long> hotelIds);
}
