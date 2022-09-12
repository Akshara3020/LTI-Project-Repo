package com.lti.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import com.lti.bean.Login;
import com.lti.bean.Payment;
import com.lti.bean.Student;
import com.lti.constant.SQLConstant;
import com.lti.utils.DbUtils;

public class StudentDaoImplementation {

	Connection conn = null;

	public void registration(Login login) {
		PreparedStatement stmt = null;

		try {

			int id = this.addStudent(login.getUsername());

			conn = DbUtils.getConnection();
			
			stmt = conn.prepareStatement(SQLConstant.CREATE_USER);
			stmt.setString(1, login.getUsername());
			stmt.setString(2, login.getPassword());
			stmt.setInt(3, 1);
			stmt.setInt(4, id);
			
			stmt.executeUpdate();

			stmt.close();

		} catch (SQLException se) {
			se.printStackTrace();
		} finally {
			try {
				if (stmt != null)
					stmt.close();
			} catch (SQLException se2) {
			}
		}
	}
	
	// TODO: take student bean as parameter collect all info from calling block
	public int addStudent(String studentName) {

		PreparedStatement stmt = null;

		try {

			conn = DbUtils.getConnection();

			stmt = conn.prepareStatement(SQLConstant.CREATE_STUDENT, Statement.RETURN_GENERATED_KEYS);

			stmt.setString(1, studentName);
			stmt.setInt(2, 0);
			stmt.setInt(3, 0);
			stmt.setString(4, "123456789");
			stmt.setInt(5, 0);
			stmt.setString(6, "default address");
			stmt.executeUpdate();
			
			int insertedID = 0;
			ResultSet rs = stmt.getGeneratedKeys();
			if (rs.next()) {
				insertedID = rs.getInt(1);
			}

			stmt.close();
			
			return insertedID;
			
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

	public void approveStudent(int studentID) {

		PreparedStatement stmt = null;

		try {
			conn = DbUtils.getConnection();
			String sql = String.format("update student set isApproved=1 where studentID = '%d'", studentID);
			stmt = conn.prepareStatement(sql);
			stmt.executeUpdate();
			stmt.close();
		} catch (SQLException se) {
			// Handle errors for JDBC
			se.printStackTrace();
		} finally {
			try {
				if (stmt != null)
					stmt.close();
			} catch (SQLException se2) {
			}
		}
	}

	public boolean isStudentApproved(String studentName) {

		PreparedStatement stmt = null;

		boolean result = false;

		try {

			conn = DbUtils.getConnection();

			String sql = String.format(SQLConstant.STUDENT_APPROVAL_STATUS, studentName);			
			stmt = conn.prepareStatement(sql);
			ResultSet queryResult = stmt.executeQuery(sql);

			while (queryResult.next()) {
				result = queryResult.getBoolean("isApproved");
			}

			stmt.close();
			
			return result;

		} catch (SQLException se) {
			// Handle errors for JDBC
			se.printStackTrace();
		} finally {
			try {
				if (stmt != null)
					stmt.close();
			} catch (SQLException se2) {
			}
		}
		return false;
	}

	public ArrayList<Student> getStudentList() {
		ArrayList<Student> students = new ArrayList<Student>();
		PreparedStatement stmt = null;

		try {

			conn = DbUtils.getConnection();
			stmt = conn.prepareStatement(SQLConstant.GET_STUDENT_LIST);
			ResultSet queryResult = stmt.executeQuery(SQLConstant.GET_STUDENT_LIST);

			while (queryResult.next()) {
				int id = queryResult.getInt("studentID");
				String name = queryResult.getString("studentName");
				boolean approved = queryResult.getBoolean("isApproved");

				Student student = new Student();
				student.setName(name);
				student.setId(id);
				student.setApproved(approved);
				students.add(student);
			}

			stmt.close();

			return students;
			
		} catch (SQLException se) {
			// Handle errors for JDBC
			se.printStackTrace();
		} finally {
			try {
				if (stmt != null)
					stmt.close();
			} catch (SQLException se2) {
			}
		}
		return students;
	}
	
	public void payFee(Payment payment) {
		
		PreparedStatement stmt = null;

		try {

			conn = DbUtils.getConnection();

			stmt = conn.prepareStatement(SQLConstant.CREATE_PAYMENT);
			
			stmt.setString(1, payment.getPaymentMethod());
			stmt.setInt(2, payment.getStudentID());
			stmt.setFloat(3, payment.getTotalAmount());

			stmt.executeUpdate();

			stmt.close();			
		} catch (SQLException se) {
			// Handle errors for JDBC
			se.printStackTrace();
		} finally {
			try {
				if (stmt != null)
					stmt.close();
			} catch (SQLException se2) {
			}
		}
	}
}
