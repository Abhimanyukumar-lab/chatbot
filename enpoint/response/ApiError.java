/**
 * 
 */
package com.technojade.allybot.enpoint.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author BC70873
 *
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ApiError {

	private String errorCode;
	private String errorMessage;
}
