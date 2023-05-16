package com.technojade.allybot.entity;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.Data;
import lombok.ToString;

/**
 * @author ugam.sharma
 *
 */
@Entity
@Table(name = "tbl_file_upload")
@Data
@ToString
@JsonInclude(JsonInclude.Include.NON_NULL)
public class FileInformation {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Long uploadedId;
	private Integer fileTypeId;
	private String fileName;
	private String contentType;
	private String url;
	private String fileIdentifier;
	private String isApproved;
	private Long createdBy;
	private Long updatedBy;
	@CreationTimestamp
	private LocalDateTime createdDate;
	@UpdateTimestamp
	private LocalDateTime updatedDate;

}
