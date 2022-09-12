/**
 * 
 */
package com.lti.exception;

/**
 * @author 10710137
 *
 */
public class InvalidStudentIdException extends Exception {
	
    private String message;
	
	public InvalidStudentIdException(String message) {
		this.message = message;
	}
	
	public String getMessage() {
		return this.message;
	}  

}
