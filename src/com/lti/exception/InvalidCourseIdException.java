/**
 * 
 */
package com.lti.exception;

/**
 * @author 10710137
 *
 */
public class InvalidCourseIdException extends Exception {
	
	
    private String message;
	
	public InvalidCourseIdException(String message) {
		this.message = message;
	}
	
	public String getMessage() {
		return this.message;
	}

}
