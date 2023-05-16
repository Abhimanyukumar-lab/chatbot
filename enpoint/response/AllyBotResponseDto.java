/**
 * 
 */
package com.technojade.allybot.enpoint.response;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author BC70873
 *
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class AllyBotResponseDto<T> {

	private T data;
	private ServiceStatus serviceStatus;
	private String msg;
	private List<ApiError> errors;
	private Long traceId;
	private Timestamp timestamp;

	
	public void setErrors(ApiError apiError) {
		errors = new ArrayList<>();
		errors.add(apiError);
	}
}
