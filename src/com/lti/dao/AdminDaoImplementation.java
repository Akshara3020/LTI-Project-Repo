/**
 * 
 */
package com.lti.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import com.lti.bean.Course;
import com.lti.bean.Grade;
import com.lti.constant.SQLConstant;
import com.lti.utils.DbUtils;

/**
 * @author 10710133
 *
 */

public class AdminDaoImplementation {

	Connection conn = null;
	
	public void addAdmin() {
		PreparedStatement stmt = null;

		try {
			conn = DbUtils.getConnection();
			stmt = conn.prepareStatement(SQLConstant.CREATE_ADMIN);
			stmt.setInt(1, 1);
			stmt.setString(2, "root");
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

			} // nothing we can do

//			try {
//				if (conn != null)
//					conn.close();
//			} catch (SQLException se) {
//				se.printStackTrace();
//			} // end finally try
		} // end try
	}

	public void addCourse(Course course) {

		PreparedStatement stmt = null;

		try {
			conn = DbUtils.getConnection();
			stmt = conn.prepareStatement(SQLConstant.ADD_COURSE);
			stmt.setString(1, course.getCourseName());
			stmt.setString(2, course.getCourseDetails());
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

			} // nothing we can do

//			try {
//				if (conn != null)
//					conn.close();
//			} catch (SQLException se) {
//				se.printStackTrace();
//			} // end finally try
		} // end try
	}

	public void removeCourse(int courseID) {

		PreparedStatement stmt = null;

		try {
			
			conn = DbUtils.getConnection();
			String sql = String.format(SQLConstant.REMOVE_COURSE, courseID);
			stmt = conn.prepareStatement(sql);
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

			} // nothing we can do

//			try {
//				if (conn != null)
//					conn.close();
//			} catch (SQLException se) {
//				se.printStackTrace();
//			} // end finally try
		} // end try
	}
	
	public ArrayList<Grade> getGrades(int studentID) {
		
		ArrayList<Grade> grades = new ArrayList<Grade>();

		PreparedStatement stmt = null;

		try {

			conn = DbUtils.getConnection();
			String sql = String.format(SQLConstant.GET_STUDENT_GRADE_LIST, studentID);
			stmt = conn.prepareStatement(sql);
			ResultSet result = stmt.executeQuery(sql);
			
			while(result.next()) {
				int gradeID = result.getInt("gradeID");
				String grade = result.getString("grade");
				int courseID = result.getInt("courseID");
				
				Grade g = new Grade();
				g.setGradeID(gradeID);
				g.setGrade(grade);
				g.setCourseID(courseID);
				g.setStudentID(studentID);
				
				grades.add(g);
			}
			
			stmt.close();

			return grades;

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
		
		return grades;
	}
}
