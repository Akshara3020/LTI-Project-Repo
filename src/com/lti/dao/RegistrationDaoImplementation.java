package com.lti.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import com.lti.bean.Course;
import com.lti.bean.Student;
import com.lti.constant.SQLConstant;
import com.lti.exception.EmptyCourselistException;
import com.lti.exception.InvalidCourseIdException;
import com.lti.exception.InvalidStudentIdException;
import com.lti.exception.InvalidUserException;
import com.lti.utils.DbUtils;

public class RegistrationDaoImplementation {
	
	Connection conn = null;
	
	//course list is empty

	public ArrayList<Course> getCourseList() throws EmptyCourselistException {

		ArrayList<Course> courses = new ArrayList<Course>();

		PreparedStatement stmt = null;

		try {

			conn = DbUtils.getConnection();
			stmt = conn.prepareStatement(SQLConstant.GET_COURSE_LIST);
			ResultSet queryResult = stmt.executeQuery(SQLConstant.GET_COURSE_LIST);

			while (queryResult.next()) {

				int id = queryResult.getInt("courseID");
				String courseName = queryResult.getString("courseName");
				String descp = queryResult.getString("courseDetails");

				Course course = new Course();
				course.setCourseID(id);
				course.setCourseName(courseName);
				course.setCourseDetails(descp);

				courses.add(course);
			}
			
			//check if  the list is empty
			if(courses.isEmpty()) {
				throw new EmptyCourselistException("Course list is Empty");
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
		} // end try

		return courses;
	}
	
	//invalid course id and student id
	public void registerCourse(int studentID, int courseID) throws InvalidStudentIdException,InvalidCourseIdException {

		PreparedStatement stmt = null;
		
		try {
			conn = DbUtils.getConnection();
			stmt = conn.prepareStatement(SQLConstant.REGISTER_COURSE);
			stmt.setInt(1, studentID);
			stmt.setInt(2, courseID);
			stmt.executeUpdate();
			stmt.close();
			//to check if student id exist
			StudentDaoImplementation stuDao = new StudentDaoImplementation();
			ArrayList<Student> stu = stuDao.getStudentList();
			Integer flag = 0;
			for (Student s : stu) {
				if(s.getId()==studentID) {
					flag=1;
				}
					 
			}
			if(flag==0) {
				throw new InvalidStudentIdException("Student id doesn't exist");
				
			}
			// to check if course id exist
			
			RegistrationDaoImplementation couDao = new RegistrationDaoImplementation();
			ArrayList<Course> cou =couDao.getCourseList();
			Integer Flag = 0;
			for (Course c : cou) {
				if(c.getCourseID()==courseID) {
					Flag=1;
				}
					 
			}
			if(Flag==0) {
				throw new InvalidCourseIdException("Course id doesn't exist");
				
			}
			
			
			
			
		} catch (SQLException se) {
			// Handle errors for JDBC
			se.printStackTrace();
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
	
	public void deRegisterCourse(int studentID, int courseID) {
		
		PreparedStatement stmt = null;
		
		try {
			conn = DbUtils.getConnection();
			String sql = String.format(SQLConstant.DEREGISTER_COURSE, courseID, studentID);
			stmt = conn.prepareStatement(sql);
			stmt.executeUpdate();
			stmt.close();
			
			//to check if student id exist
			StudentDaoImplementation stuDao = new StudentDaoImplementation();
			ArrayList<Student> stu = stuDao.getStudentList();
			Integer flag = 0;
			for (Student s : stu) {
				if(s.getId()==studentID) {
					flag=1;
				}
					 
			}
			if(flag==0) {
				throw new InvalidStudentIdException("Student id doesn't exist");
				
			}
			
			
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
	
	public ArrayList<Course> getStudentCourseList(int studentID) throws EmptyCourselistException {

		ArrayList<Course> courses = new ArrayList<Course>();
		ArrayList<Integer> courseIDs = new ArrayList<Integer>();
	
		PreparedStatement stmt = null;
		
		try {

			conn = DbUtils.getConnection();
			String sql = String.format(SQLConstant.GET_STUDENT_COURSE_LIST, studentID);
			stmt = conn.prepareStatement(sql);
			ResultSet queryResult = stmt.executeQuery(sql);

			while (queryResult.next()) {
				int id = queryResult.getInt("courseID");
				courseIDs.add(id);
			}
						
			for (int n : courseIDs) {
				String sql1 = String.format(SQLConstant.GET_COURSE_DETAILS, n);
				stmt = conn.prepareStatement(sql1);				
				
				ResultSet queryResult1 = stmt.executeQuery(sql1);
				queryResult1.next();				
				String name = queryResult1.getString("courseName");
				
				Course course = new Course();
				course.setCourseName(name);
				course.setCourseID(n);

				courses.add(course);
			}
			
			//check if its empty
			if(courses.isEmpty()) {
				throw new EmptyCourselistException("Course list is Empty");
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
}
