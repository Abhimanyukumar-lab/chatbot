package com.technojade.allybot.entity;

import java.time.LocalDateTime;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "quick_query_category")
public class QuickQueryCategary {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long quickCatId;
	private String categoryLabel;
    private String categoryIcon;
    private Long hotelId;
    private  LocalDateTime updatedBy ;
    private Long updatedOn;  
    private  LocalDateTime createdOn;
    
}
