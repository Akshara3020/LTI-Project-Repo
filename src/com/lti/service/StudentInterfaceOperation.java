package com.lti.service;

public interface StudentInterfaceOperation {
	public void registerCourses(int studentID);

	public void addCourse(int studentID);

	public void dropCourse(int studentID);

	public void viewEnrolledCourses(int studentID);

	public void viewGrades(int studentID);

	public void payFee(int studentID);
}
