package com.technojade.allybot.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "faq_answers_mst")
public class FaqAnswers {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long faqAnswerId;
	private Long faqId;
	private String answers;
	private Long clientId;
	private Long hotelId;
	
	
}
