package com.lti.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import com.lti.bean.Login;
import com.lti.bean.Professor;
import com.lti.bean.Student;
import com.lti.constant.SQLConstant;
import com.lti.utils.DbUtils;
import com.lti.bean.Course;

public class ProfessorDaoImplementation {

	Connection conn = null;

	public void addProfessor(Professor professor, Login login) {
		PreparedStatement stmt = null;

		try {
			conn = DbUtils.getConnection();
			stmt = conn.prepareStatement(SQLConstant.CREATE_PROFESSOR, Statement.RETURN_GENERATED_KEYS);

			stmt.setString(1, professor.getName());
			stmt.setString(2, professor.getMobileNumber());
			stmt.setString(3, professor.getAddress());
			stmt.setInt(4, professor.getDepartmentID());
			stmt.setInt(5, professor.getAge());

			stmt.executeUpdate();
			
			int insertedID = 0;
			ResultSet rs = stmt.getGeneratedKeys();
			if (rs.next()) {
				insertedID = rs.getInt(1);
			}
			
			stmt.close();
			
			stmt = conn.prepareStatement(SQLConstant.CREATE_USER);
			stmt.setString(1, login.getUsername());
			stmt.setString(2, login.getPassword());
			stmt.setInt(3, 2);
			stmt.setInt(4, insertedID);
			
			stmt.executeUpdate();

			stmt.close();

		} catch (SQLException se) {
			// Handle errors for JDBC
			se.printStackTrace();
		} catch (Exception e) {
			// Handle errors for Class.forName
			e.printStackTrace();
 		} finally {
			// finally block used to close resources
			try {
				if (stmt != null)
					stmt.close();
			} catch (SQLException se2) {

			}		
		}
	}

	public ArrayList<Course> getCourseList(int profID) {

		ArrayList<Course> courses = new ArrayList<Course>();
		
		PreparedStatement stmt = null;

		try {
			conn = DbUtils.getConnection();

			String sql = String.format(SQLConstant.GET_PROFESSOR_COURSE_LIST, profID);
			stmt = conn.prepareStatement(sql);
			ResultSet queryResult = stmt.executeQuery(sql);

			while (queryResult.next()) {
				int id = queryResult.getInt("courseID");
				String name = queryResult.getString("courseName");
				Course course = new Course();
				course.setCourseName(name);
				course.setCourseID(id);
				courses.add(course);
			}

			stmt.close();
			return courses;

		} catch (SQLException se) {
			// Handle errors for JDBC
			se.printStackTrace();
		} catch (Exception e) {
			// Handle errors for Class.forName
			e.printStackTrace();
		} finally {
			// finally block used to close resources
			try {
				if (stmt != null)
					stmt.close();
			} catch (SQLException se2) {
			} // nothing we can do
//			try {
//				if (conn != null)
//					conn.close();
//			} catch (SQLException se) {
//				se.printStackTrace();
//			} // end finally try
		} // end try

		return courses;
	}

	public ArrayList<Student> getStudentList(int courseID) {

		ArrayList<Integer> studentIDs = new ArrayList<Integer>();
		ArrayList<Student> students = new ArrayList<Student>();

		PreparedStatement stmt = null;
		
		try {
			
			conn = DbUtils.getConnection();
			
			String sql = String.format(SQLConstant.GET_COURSE_STUDENT_LIST, courseID);
			stmt = conn.prepareStatement(sql);
			ResultSet queryResult = stmt.executeQuery(sql);

			while (queryResult.next()) {
				int id = queryResult.getInt("studentID");
				studentIDs.add(id);
			}
			
			for (int n : studentIDs) {
				String sql1 = String.format(SQLConstant.GET_STUDENT_DETAILS, n);
				stmt = conn.prepareStatement(sql1);				
				ResultSet queryResult1 = stmt.executeQuery(sql1);
				queryResult1.next();
				
				int id = queryResult1.getInt("studentID");
				String name = queryResult1.getString("studentName");
				
				Student student = new Student();
				student.setId(id);
				student.setName(name);
				students.add(student);
			}

			stmt.close();

			return students;

		} catch (SQLException se) {
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
	
	public void addGrade(int studentID, int courseID, String grade) {
		PreparedStatement stmt = null;

		//TODO: check if the studentID and courseID is already in grade table		
		try {
			conn = DbUtils.getConnection();
			stmt = conn.prepareStatement(SQLConstant.ADD_GRADE);
			stmt.setInt(1, studentID);
			stmt.setInt(2, courseID);
			stmt.setString(3, grade);
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
}
