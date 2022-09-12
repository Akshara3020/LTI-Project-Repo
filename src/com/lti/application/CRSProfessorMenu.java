package com.lti.application;

import java.util.Scanner;

import com.lti.service.ProfessorInterfaceOperation;
import com.lti.service.ProfessorService;

public class CRSProfessorMenu {
	public void show(int profID) {

		boolean quit = false;
		while (!quit) {
			System.out.println("\n\n+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++");
			System.out.println("\n\tProfessor menu\n");
			System.out.println("\t1) View enrolled students");
			System.out.println("\t2) Grade students");
			System.out.println("\t3) Exit");

			System.out.print("\n\tEnter option:\t");

			Scanner input = new Scanner(System.in);
			int option = input.nextInt();

			ProfessorInterfaceOperation service = new ProfessorService();

			switch (option) {
			case 1:
				service.viewStudentsEnrolled(profID);
				break;

			case 2:
				service.addGrade(profID);
				break;

			case 3:
				quit = true;
				break;

			default:
				System.out.println("Enter a valid option!");
				break;
			}
		}
	}
}
