/**
 * 
 */
package com.technojade.allybot.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;

/**
 * @author ugam.sharma
 *
 */
@Entity
@Table(name = "file_type")
@Data
public class FileType {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long fileTypeId;
	private String fileName;
	
}
