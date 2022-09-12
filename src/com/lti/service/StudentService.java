package com.lti.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

import com.lti.bean.Course;
import com.lti.bean.Grade;
import com.lti.bean.Payment;
import com.lti.bean.Registration;
import com.lti.dao.AdminDaoImplementation;
import com.lti.dao.RegistrationDaoImplementation;
import com.lti.dao.StudentDaoImplementation;

/**
 * @author 10710133
 *
 */

public class StudentService implements StudentInterfaceOperation {

	public void registerCourses(int studentID) {
		System.out.println("\n\n+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++");
		System.out.println("\n\tRegister for courses");

		RegistrationDaoImplementation regDao = new RegistrationDaoImplementation();
		
		ArrayList<Course> courseList = regDao.getCourseList();			
		for (Course course : courseList) {
			System.out.println("\tId: " + course.getCourseID() + "\t" + "Name: " + course.getCourseName());
		}

		addCourse(studentID);
	}

	public void addCourse(int studentID) {

		Scanner input = new Scanner(System.in);

		RegistrationDaoImplementation regDao = new RegistrationDaoImplementation();

		while (true) {

			System.out.print("\n\tEnter course id to add:\t");
			int courseID = input.nextInt();

			System.out.print("\n\tConfirm course " + "Id: " + courseID + "(Y/N):\t");
			String opt = input.next();

			if (opt.contains("Y") || opt.contains("y")) {

				regDao.registerCourse(studentID, courseID);

				System.out.print("\n\tAdd another course? (Y/N):\t");
				String option = input.next();
				if (option.contains("N") || option.contains("n")) {
					break;
				}
			}
		}
	}

	public void dropCourse(int studentID) {

		System.out.println("\n\n+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++");
		Scanner input = new Scanner(System.in);

		RegistrationDaoImplementation regDao = new RegistrationDaoImplementation();

		while (true) {
			System.out.print("\tEnter course id to drop:\t");
			int courseID = input.nextInt();

			regDao.deRegisterCourse(studentID, courseID);

			System.out.print("\n\tRemove another course? (Y/N):\t");
			String option = input.next();

			if (option.contains("N") || option.contains("n")) {
				break;
			}
		}
	}

	public void viewEnrolledCourses(int studentID) {
		System.out.println("\n\n+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++");
		System.out.println("\n\tEnrolled courses");

		RegistrationDaoImplementation regDao = new RegistrationDaoImplementation();
		ArrayList<Course> courseList = regDao.getStudentCourseList(studentID);

		for (Course course : courseList) {
			System.out.println("\tid: " + course.getCourseID() + "\t" + "course: " + course.getCourseName());
		}
	}

	public void viewGrades(int studentID) {
		System.out.println("\n\n+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++");

		RegistrationDaoImplementation regDao = new RegistrationDaoImplementation();
		ArrayList<Course> courses = regDao.getCourseList();

		AdminDaoImplementation adminDoa = new AdminDaoImplementation();
		ArrayList<Grade> grades = adminDoa.getGrades(studentID);

		System.out.println("\n\tGrade card");

		for (Grade grade : grades) {

			String courseName = null;
			for (Course c : courses) {
				if (c.getCourseID() == grade.getCourseID()) {
					courseName = c.getCourseName();
					break;
				}
			}
			
			System.out.println("\tcourse: " + courseName + ", grade: " + grade.getGrade());
		}
	}

	public void payFee(int studentID) {
		System.out.println("\n++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++");

		System.out.println("\n\t\tFee Payment Portal");
		this.viewEnrolledCourses(studentID);
		System.out.println("\n++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++");

		RegistrationDaoImplementation regDao = new RegistrationDaoImplementation();
		ArrayList<Course> courseList = regDao.getStudentCourseList(studentID);

		int pricePerCourse = 10000;
		int fee = courseList.size() * pricePerCourse;
		System.out.println("\tYour total fee is: " + fee + " INR.");
		System.out.println("\tNote: Price for each course is " + pricePerCourse + " INR.");

		Scanner input = new Scanner(System.in);

		Payment payment = new Payment();
		payment.setStudentID(studentID);
		payment.setTotalAmount(fee);

		boolean paymentDone = false;
		while (!paymentDone) {

			System.out.print("\n\tDo you want to make payment (Y/N)?\t");
			String in = input.next();

			if (in.contains("N") || in.contains("n")) {
				break;
			}

			System.out.println("\n\tSelect Payment Method:\n");
			System.out.println("\t\t1. Credit Card");
			System.out.println("\t\t2. Debit Card");
			System.out.println("\t\t3. Net Banking");
			System.out.println("\t\t4. UPI");

			System.out.print("\n\t\tSelect Payment Method:\t");

			int opt = input.nextInt();

			switch (opt) {
			case 1:

				payment.setPaymentMethod("credit card");

				System.out.print("\t\tEnter credit card number:\t");
				Long cCardNumber = input.nextLong();
				System.out.print("\t\tEnter Expiry Month:\t");
				int cExpiryMonth = input.nextInt();
				System.out.print("\t\tEnter Expiry Year:\t");
				int cExpiryYear = input.nextInt();
				System.out.print("\t\tEnter CVV:\t");
				int cCVV = input.nextInt();

				// Details
				System.out.println("\n++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++");

				System.out.println("\n\t\tYour payment is processing.....");

				System.out.print("\n\t\tEnter the OTP:\t");
				int cOTP = input.nextInt();

				// Check for transaction successful
				System.out.println("\n\t\tValidating OTP.....");

				System.out.println("\t\tYour payment is successful.");
				paymentDone = true;
				break;

			case 2:

				payment.setPaymentMethod("debit card");

				System.out.print("\t\tEnter debit card number:\t");
				Long dCardNumber = input.nextLong();
				System.out.print("\t\tEnter Expiry Month:\t");
				int dExpiryMonth = input.nextInt();
				System.out.print("\t\tEnter Expiry Year:\t");
				int dExpiryYear = input.nextInt();
				System.out.print("\t\tEnter CVV:\t");
				int dCVV = input.nextInt();

				// Details
				System.out.println("\n++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++");

				System.out.println("\t\tYour payment is processing.....");

				System.out.print("\n\t\tEnter the OTP:\t");
				int dOTP = input.nextInt();

				// Check for transaction successful
				System.out.println("\n\t\tValidating OTP.....");

				System.out.println("\n\t\tYour payment is successful.");
				paymentDone = true;
				break;

			case 3:

				payment.setPaymentMethod("net card");

				System.out.println("\t\tSelect your Bank:");
				System.out.println("\t\tSBI\t\t\nPNB\t\t\nPAYTM\n");
				System.out.print("\n\t\tEnter Bank Name for net banking:\t");

				String bankName = input.next();
				System.out.println("Bank Name:" + bankName);

				// Check for transaction successful
				System.out.println("Your payment is successful");
				paymentDone = true;
				break;

			case 4:

				payment.setPaymentMethod("upi");

				System.out.print("Enter you UPI Id:\t");
				int upiId = input.nextInt();
				System.out.println("UPI ID:" + upiId);
				// Check for transaction successful

				System.out.println("Your payment is successful");
				paymentDone = true;
				break;
			default:
				System.out.println("Please enter a valid option!");
				break;
			}

			if (paymentDone) {
				StudentDaoImplementation stuDao = new StudentDaoImplementation();
				stuDao.payFee(payment);
			}
		}
	}
}
