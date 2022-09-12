package com.lti.application;

import java.util.Scanner;

import com.lti.service.StudentInterfaceOperation;
import com.lti.service.StudentService;

/**
 * @author 10710133
 *
 */

public class CRSStudentMenu {

	public void show(int studentID) {

		boolean quit = false;

		while (!quit) {
			System.out.println("\n\n+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++");
			System.out.println("\n\tStudent menu\n");
			System.out.println("\t1) Register for course");
			System.out.println("\t2) Add course");
			System.out.println("\t3) Drop course");
			System.out.println("\t4) View enrolled courses");
			System.out.println("\t5) Fee payment");
			System.out.println("\t6) View grade card");
			System.out.println("\t7) Exit");

			System.out.print("\n\tEnter option:\t");

			Scanner input = new Scanner(System.in);
			int option = input.nextInt();

			StudentInterfaceOperation service = new StudentService();

			switch (option) {
			case 1:
				service.registerCourses(studentID);
				break;

			case 2:
				service.addCourse(studentID);
				break;

			case 3:
				service.dropCourse(studentID);
				break;

			case 4:
				service.viewEnrolledCourses(studentID);
				break;

			case 5:
				service.payFee(studentID);
				break;

			case 6:
				service.viewGrades(studentID);
				break;

			case 7:
				quit = true;
				break;

			default:
				System.out.println("\tEnter a valid option.");
				break;
			}
		}
	}
}
