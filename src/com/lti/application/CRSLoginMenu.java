/**
 * 
 */
package com.lti.application;

import java.util.Scanner;

import com.lti.service.ProfessorService;
import com.lti.service.StudentService;
import com.lti.dao.StudentDaoImplementation;
import com.lti.dao.UserDaoImplementation;
import com.lti.exception.InvalidUserException;

/**
 * @author 10710133
 *
 */

public class CRSLoginMenu {

	public void show() {

		boolean quit = false;
		while (!quit) {

			System.out.println("\n\n+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++");
			System.out.println("\n\tLogin screen");

			Scanner input = new Scanner(System.in);

			System.out.print("\n\tEnter username:\t");
			String username = input.nextLine();

			System.out.print("\tEnter password:\t");
			String password = input.nextLine();

			System.out.print("\tEnter role:\t");
			String role = input.nextLine();
			
			UserDaoImplementation userDao = new UserDaoImplementation();

			try {				
				userDao.validateUser(username, password, role);
			} catch(InvalidUserException e) {	
				System.out.println(e.getMessage());
				break;
			}
			
			int userID = 0;

			try {
				userID = userDao.getUserID(username, password, role);				
			} catch(InvalidUserException e) {
				System.out.println(e.getMessage());
				break;
			}
			
			if (role.equals("student")) {

				StudentDaoImplementation stuDao = new StudentDaoImplementation();
				if (!stuDao.isStudentApproved(username)) {
					System.out.println("\n\tWaiting for approval by admin");
					quit = true;
					continue;
				}
				
				CRSStudentMenu menu = new CRSStudentMenu();
				menu.show(userID);
				
			} else if (role.equals("professor")) {
				CRSProfessorMenu menu = new CRSProfessorMenu();
				menu.show(userID);
			} else if (role.equals("admin")) {
				CRSAdminMenu menu = new CRSAdminMenu();
				menu.show();
			} else {
				System.out.println("\tEnter a valid role.");
			}

			quit = true;
		}
	}
}
