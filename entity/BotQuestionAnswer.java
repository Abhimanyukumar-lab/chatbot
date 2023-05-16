package com.technojade.allybot.entity;

import java.time.LocalDateTime;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class BotQuestionAnswer {

	@Id 
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int sId;
	private String scriptName;
	private String fileName;
	private String clientId;
	private Long hotelId;
	private LocalDateTime createdAt;
	
}
