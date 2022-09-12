package com.lti.service;

import java.util.Scanner;

import com.lti.bean.Login;
import com.lti.dao.StudentDaoImplementation;

/**
 * @author 10710133
 *
 */

public class UserService {
	public void createNewUser() {

		System.out.println("\nCreate new student");
		
		// TODO: collect all student personal information

		Scanner input = new Scanner(System.in);

		System.out.print("Enter username:");
		String username = input.nextLine();

		System.out.print("Enter password:");
		String password = input.nextLine();

		Login login = new Login();
		login.setUsername(username);
		login.setPassword(password);
		login.setRole("student");

		StudentDaoImplementation studentDao = new StudentDaoImplementation();
		studentDao.registration(login);
	}

	public void resetPassword() {

		System.out.println("\nUpdate password");

		Scanner input = new Scanner(System.in);

		System.out.print("Enter username: ");
		String username = input.nextLine();
//
//		while (true) {
//
//			System.out.print("Enter old password: ");
//			String oldPassword = input.nextLine();
//
//			if (DummyData.credentials.containsKey(username)
//					&& DummyData.credentials.get(username).equals(oldPassword)) {
//				System.out.print("Enter new password: ");
//				String newPassword1 = input.nextLine();
//				System.out.print("Confirm new password: ");
//				String newPassword2 = input.nextLine();
//				if (newPassword1.contentEquals(newPassword2)) {
//					DummyData.credentials.replace(username, oldPassword, newPassword1);
//					System.out.println("Password Updated.");
//					break;
//				}
//			} else {
//				System.out.println("Wrong password. Please try again.");
//				continue;
//			}
//		}
	}
}
