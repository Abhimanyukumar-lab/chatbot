/**
 * 
 */
package com.technojade.allybot.entity;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import org.springframework.beans.factory.annotation.Autowired;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

/**
 * @author ugam.sharma
 *
 */
@Data
@Entity
@Table(name = "client_mst")
@Getter
@Setter
public class Client extends BaseEntity{
	
	@Id
	@Autowired
	private Long clientId;
	
	private String clientName;
	
	private String logo;
	
}
