package com.lti.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.lti.constant.SQLConstant;
import com.lti.exception.InvalidUserException;
import com.lti.utils.DbUtils;

public class UserDaoImplementation {

	Connection conn = null;

	public boolean validateUser(String username, String password, String role) throws InvalidUserException {
		PreparedStatement stmt = null;
		boolean result = false;

		int roleID = 0;

		if (role.equals("student")) {
			roleID = 1;
		} else if (role.equals("professor")) {
			roleID = 2;
		} else if (role.equals("admin")) {
			roleID = 3;
		}

		if (roleID == 0) {
			return false;
		}

		try {
			conn = DbUtils.getConnection();

			String sql = String.format(SQLConstant.VALIDATE_USER, username, password, roleID);

			stmt = conn.prepareStatement(sql);
			ResultSet queryResult = stmt.executeQuery(sql);

			String name = null;
			while (queryResult.next()) {
				name = queryResult.getString("username");
			}

			stmt.close();

			if (name != null) {
				result = true;
			} else {
				throw new InvalidUserException("username or password invalid!");
			}

			return result;

		} catch (SQLException se) {
			se.printStackTrace();			
		} finally {
			try {
				if (stmt != null) {
					stmt.close();					
				}
			} catch (SQLException se2) {
				
			}
		}

		return false;
	}
	
	public int getUserID(String username, String password, String role) throws InvalidUserException {
		
		int roleID = 0;

		if (role.equals("student")) {
			roleID = 1;
		} else if (role.equals("professor")) {
			roleID = 2;
		} else if (role.equals("admin")) {
			roleID = 3;
		}

		PreparedStatement stmt = null;

		try {
			conn = DbUtils.getConnection();

			String sql = String.format(SQLConstant.VALIDATE_USER, username, password, roleID);

			stmt = conn.prepareStatement(sql);
			ResultSet queryResult = stmt.executeQuery(sql);
			
			if(!queryResult.next()) {
				throw new InvalidUserException("unable to getuser id");
			}
			
			int userID = 0;
			while (queryResult.next()) {
				userID = queryResult.getInt("userID");
			}
			
			stmt.close();
			
			return userID;

		} catch (SQLException se) {
			se.printStackTrace();
		} finally {
			try {
				if (stmt != null)
					stmt.close();
			} catch (SQLException se2) {
				
			}
		}
		
		return 0;
	}
}
