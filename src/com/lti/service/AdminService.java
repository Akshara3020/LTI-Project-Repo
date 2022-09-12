package com.lti.service;

import java.util.ArrayList;
import java.util.Scanner;

import com.lti.bean.Course;
import com.lti.bean.Grade;
import com.lti.bean.Login;
import com.lti.bean.Professor;
import com.lti.bean.Student;
import com.lti.dao.AdminDaoImplementation;
import com.lti.dao.ProfessorDaoImplementation;
import com.lti.dao.RegistrationDaoImplementation;
import com.lti.dao.StudentDaoImplementation;

public class AdminService implements AdminInterfaceOperation {

	public void addProfessor() {
		
		System.out.println("\n\n+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++");

		Scanner input = new Scanner(System.in);
		
		while (true) {

			// TODO: change input to client layer

			System.out.print("\n\tEnter professor name: ");
			String name = input.next();

			System.out.print("\n\tCreate password: ");
			String password = input.next();

			System.out.print("\n\tmobile number: ");
			String mobileNumber = input.next();

			System.out.print("\n\taddress: ");
			String address = input.next();

			System.out.print("\n\tage: ");
			int age = input.nextInt();

			System.out.print("\n\tdepartment id: ");
			int deptID = input.nextInt();

			Professor professor = new Professor();
			professor.setName(name);
			professor.setMobileNumber(mobileNumber);
			professor.setAddress(address);
			professor.setAge(age);
			professor.setDepartmentID(deptID);

			Login login = new Login();
			login.setUsername(name);
			login.setPassword(password);

			ProfessorDaoImplementation profDao = new ProfessorDaoImplementation();
			profDao.addProfessor(professor, login);				

			System.out.print("\n\tProfessor added successfully");

			System.out.print("\n\tAdd another professor? (Y/N): ");
			String option = input.next();

			if (option.contains("N") || option.contains("n")) {
				break;
			}
		}
	}

	public void approveStudentRegistration() {
		System.out.println("\n\n+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++");
		System.out.println("\n\tApprove students:");

		Scanner input = new Scanner(System.in);

		StudentDaoImplementation stuDao = new StudentDaoImplementation();
		ArrayList<Student> students = stuDao.getStudentList();

		for (Student s : students) {
			System.out.println("\n\tStudent name: " + s.getName() + ", Approved: " + (s.isApproved() ? "Y" : "N"));
		}

		for (Student s : students) {

			if (!s.isApproved()) {
				System.out.print("\n\tApprove student: " + s.getName() + " (Y/N) ?");
				String option = input.next();

				if (option.equals("Y") || option.equals("y")) {
					// set the table
					stuDao.approveStudent(s.getId());
				}

				System.out.print("\n\tContinue with approval? (Y/N):\t");
				option = input.next();

				if (option.equals("N") || option.equals("n")) {
					break;
				}
			}
		}
	}

	public void addCourse() {

		Scanner input = new Scanner(System.in);
		
		System.out.println("\n\n+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++");

		AdminDaoImplementation adminDao = new AdminDaoImplementation();

		while (true) {
			
			System.out.print("\n\tEnter course name:\t");
			String name = input.next();
			
			System.out.print("\n\tEnter course details:\t");
			String details = input.next();
			
			Course course = new Course();
			course.setCourseName(name);
			course.setCourseDetails(details);
			
			adminDao.addCourse(course);
			
			System.out.print("\n\tAdd another course? (Y/N):\t");
			String option = input.next();

			if (option.contains("N") || option.contains("n")) {
				break;
			}
		}
	}

	public void removeCourse() {

		System.out.println("\n\n+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++");
		Scanner input = new Scanner(System.in);
		
		RegistrationDaoImplementation regDao = new RegistrationDaoImplementation();
		ArrayList<Course> courseList = regDao.getCourseList();
		
		for(Course course: courseList) {
			System.out.println("\tId: " + course.getCourseID() + "\t" + "Name: " + course.getCourseName());
		}

		AdminDaoImplementation adminDao = new AdminDaoImplementation();

		while (true) {
			System.out.print("\n\tEnter course id:\t");
			int id = input.nextInt();
			
			adminDao.removeCourse(id);
			
			System.out.print("\n\tRemove another course? (Y/N):\t");
			String option = input.next();

			if (option.contains("N") || option.contains("n")) {
				break;
			}
		}
	}

	public void generateReportCard() {

		System.out.println("\n\n+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++");
		Scanner input = new Scanner(System.in);
		
		// list students
		StudentDaoImplementation stuDao = new StudentDaoImplementation();
		ArrayList<Student> students = stuDao.getStudentList();
		
		RegistrationDaoImplementation regDao = new RegistrationDaoImplementation();
		ArrayList<Course> courses = regDao.getCourseList();
		
		for(Student s: students) {
			System.out.println("\tid: " + s.getId() + ", name: " + s.getName());
		}
		
		while(true) {
			System.out.print("\tEnter student id to generate grade card: ");
			int studentID = input.nextInt();
			
			AdminDaoImplementation adminDoa = new AdminDaoImplementation();
			ArrayList<Grade> grades = adminDoa.getGrades(studentID);
			
			System.out.println("\n\tGrade card");
			
			for(Grade grade: grades) {
				
				String courseName = null;
				for(Course c: courses) {
					if(c.getCourseID() == grade.getCourseID()) {
						courseName = c.getCourseName();
						break;
					}
				}	
				System.out.println("\tcourse: " + courseName + ", grade: " + grade.getGrade());
			}
			
			if(grades.size() == 0) {
				System.out.println("\n\tgrades yet to be added!");
			}
			
			System.out.print("\n\tGenerate grade card for another student? (Y/N): ");
			String option = input.next();
			
			if(option.contains("N") || option.contains("n")) {
				break;
			}
		}
	}
}
