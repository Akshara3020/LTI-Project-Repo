package com.lti.exception;

public class EmptyCourselistException extends Exception {
	
    private String message;
	
	public EmptyCourselistException(String message) {
		this.message = message;
	}
	
	public String getMessage() {
		return this.message;
	}
	
	

}
