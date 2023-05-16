package com.technojade.allybot.repository;

import java.util.ArrayList;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.technojade.allybot.entity.QuickQueryCategary;



@Repository
public interface QuickQueryRepository extends JpaRepository<QuickQueryCategary, Long>{

	
	
	
	 
	 @Query(value="from QuickQueryCategary qc  where qc.hotelId =:id" , nativeQuery = false)
		public ArrayList<QuickQueryCategary> getCategory(@Param("id") long id);
}
