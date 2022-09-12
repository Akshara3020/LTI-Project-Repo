package com.lti.application;

import java.util.Scanner;

import com.lti.service.UserService;
import com.lti.dao.AdminDaoImplementation;
import com.lti.dao.StudentDaoImplementation;

/**
 * @author 10710133
 *
 */

public class CRSApplication {

	public static void main(String[] args) {

		Scanner input = new Scanner(System.in);

		UserService userService = new UserService();

		boolean quit = false;
		while (!quit) {
			System.out.println("\n+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++");
			System.out.println("\n\tWelcome to CRS Application !\n");
			System.out.println("\t1) Login");
			System.out.println("\t2) Registration");
			System.out.println("\t3) Update password");
			System.out.println("\t4) Exit");

			System.out.print("\n\tEnter option:\t");

			int option = input.nextInt();

			switch (option) {
			case 1:
				CRSLoginMenu loginMenu = new CRSLoginMenu();
				loginMenu.show();
				break;

			case 2:
				userService.createNewUser();
				break;

			case 3:
				userService.resetPassword();
				break;

			case 4:
				System.out.println("\tExit successful!");
				quit = true;
				break;

			default:
				System.out.println("\tInvalid option!");
				break;
			}
		}
	}
}