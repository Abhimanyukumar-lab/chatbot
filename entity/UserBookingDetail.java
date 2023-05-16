/**
 * 
 */
package com.technojade.allybot.entity;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * @author BC70873
 *
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "user_booking_detail")
@JsonInclude(JsonInclude.Include.NON_NULL)
public class UserBookingDetail extends BaseEntity implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long bookingDetailId;

	private String firstName;

	private String lastName;

	private String proofName;

	private String mobileNumber;

	private boolean isPrimary;

	private String proofId;

	private String gender;

	private int age;
	private Long bookingId;

	private String email;
	private Long onlineBookingId;

}
