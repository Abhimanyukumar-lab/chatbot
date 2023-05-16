package com.technojade.allybot.exception;

public class DataNotFoundException extends RuntimeException {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1797416566795896800L;

	public DataNotFoundException() {

        super("No data found");
    }
}
