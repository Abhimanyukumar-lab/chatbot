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
@Table(name = "chat_history")
public class ChatHistory extends BaseEntity{

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long historyId;
	
	private String conversation;
	
	private Long chatStartdBy;
	
	private Long discussionWithId;
	
}
