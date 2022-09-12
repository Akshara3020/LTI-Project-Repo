package com.lti.application;

import java.util.Scanner;

import com.lti.service.AdminInterfaceOperation;
import com.lti.service.AdminService;

public class CRSAdminMenu {
	public void show() {

		boolean quit = false;

		while (!quit) {
			System.out.println("\n\n+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++");
			System.out.println("\n\tAdmin menu\n");
			System.out.println("\t1) Add professor");
			System.out.println("\t2) Approve students registration");
			System.out.println("\t3) Add course");
			System.out.println("\t4) Remove course");
			System.out.println("\t5) Generate grade card");
			System.out.println("\t6) Exit");

			System.out.print("\n\tEnter option:\t");

			Scanner input = new Scanner(System.in);
			int option = input.nextInt();

			AdminInterfaceOperation service = new AdminService();

			switch (option) {
			case 1:
				service.addProfessor();
				break;

			case 2:
				service.approveStudentRegistration();
				break;

			case 3:
				service.addCourse();
				break;

			case 4:
				service.removeCourse();
				break;

			case 5:
				service.generateReportCard();
				break;

			case 6:
				quit = true;
				break;

			default:
				System.out.println("Enter a valid option!");
				break;
			}
		}
	}
}
