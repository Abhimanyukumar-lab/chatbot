/**
 * 
 */
package com.technojade.allybot.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * @author BC70873
 *
 */
@Entity
@Table(name = "user_role_page_access")
public class UserRoleAccess extends BaseEntity{

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long userRoleAccessId;
	
	private String userId;
	
	private String userRoleId;
	
	private String pageAccessId;
	
}
