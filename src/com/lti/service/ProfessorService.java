package com.lti.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

import com.lti.bean.Course;
import com.lti.bean.Student;
import com.lti.dao.ProfessorDaoImplementation;

/**
 * @author 10710133
 *
 */

public class ProfessorService implements ProfessorInterfaceOperation {

	public void addGrade(int profID) {
		
		Scanner input = new Scanner(System.in);
		
		ProfessorDaoImplementation profDao = new ProfessorDaoImplementation();
		
		ArrayList<Course> courseList = profDao.getCourseList(profID);
		
		System.out.println("\n\tCourse list");
		
		for(Course course: courseList) {
			System.out.println("\tid: " + course.getCourseID() + ", course: " + course.getCourseName());
		}
		
		while (true) {
			System.out.print("\n\tEnter id of course to grade: ");
			int courseID = input.nextInt();
			
			ArrayList<Student> students = profDao.getStudentList(courseID);
			
			System.out.println("\n\tStudent list");
			
			for(Student student: students) {
				System.out.println("\tid: " +student.getId() +", student name: " + student.getName());
			}
			
			while(true) {
				System.out.print("\tEnter id of student to grade: ");
				int studentID = input.nextInt();
				
				System.out.print("\tEnter grade (A,B,C,D,E,F): ");
				String grade = input.next();
				
				profDao.addGrade(studentID, courseID, grade);
				
				System.out.print("\tDo you want to grade another student? (Y/N): ");
				String option = input.next();
				
				if (option.contains("N") || option.contains("n")) {
					break;
				}
			}
			
			System.out.print("\tDo you want to grade another course? (Y/N): ");
			String option = input.next();
			
			if (option.contains("N") || option.contains("n")) {
				break;
			}
		}
	}

	public void viewStudentsEnrolled(int profID) {
		
		System.out.println("\n\tEnrolled student list");
		
		ProfessorDaoImplementation profDao = new ProfessorDaoImplementation();
		
		ArrayList<Course> courseList = profDao.getCourseList(profID);
		
		for(Course course: courseList) {
			System.out.println("\n\tcourse: " + course.getCourseName());
			
			ArrayList<Student> students = profDao.getStudentList(course.getCourseID()); 
			
			for(Student student: students) {
				System.out.println("\t\tstudent name: " + student.getName());
			}
		}
	}
}
