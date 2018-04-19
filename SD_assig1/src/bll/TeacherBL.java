package bll;

import java.util.ArrayList;
import java.util.List;

import dao.CourseDAO;
import dao.Enrol;
import dao.EnrolDAO;
import dao.Student;
import dao.StudentDAO;
import dao.Teacher;
import dao.TeacherDAO;
import dao.User;

public class TeacherBL extends UserBL{
	
	public TeacherBL() {
		this.dao = new TeacherDAO();
	}
	private Teacher findTeacherByEmail(String email) {

		Teacher t = null;

		try {

			t = (Teacher)(new TeacherDAO()).findUserByFieldValue("email", email).get(0);

		} catch (IndexOutOfBoundsException ie) {

			return null;
		}

		return t;
	}

	public int login(String email, String password) {

		Teacher t = findTeacherByEmail(email);

		if (t == null) {

			return -1;
		}

		if (!t.getPassword().equals(password)) {

			return -2;
		}
		
		if (t.isDeleted()) {
			return -3;
		}

		return t.getId();

	}
	
	public void createStudent(String firstName, String lastName, String address, String phoneNumber, String email,
			String cnp) {
		
		(new StudentDAO()).createUser(firstName, lastName, cnp, phoneNumber, email, address, "1234");
		
	}
	
	public Student viewStudent(int studentID) {
						
		return (Student)(new StudentDAO()).findUserByFieldValue("idstudents", studentID).get(0);
	}
	
	public void giveMarkToStudent (String course, int studentID, int grade) {
		
		List<String> fields = new ArrayList<String>();
		fields.add("studentId");
		fields.add("courseId");
		
		List<Object> values = new ArrayList<Object>();
		values.add(studentID);
		values.add((new CourseDAO()).findCourseByFieldValue("name", course).get(0).getId());
		
		EnrolDAO ed = new EnrolDAO();
		
		Enrol e = ed.findEnrolByFieldValue(fields, values).get(0);
		
		ed.updateEnrol(e.getId(), "grade", grade);
	}
	
	public int findStudentByName(String firstName, String lastName) {
		
		List<String> fields = new ArrayList<String>();
		fields.add("firstName");
		fields.add("lastName");
		
		List<Object> values = new ArrayList<Object>();
		values.add(firstName);
		values.add(lastName);
		
		return (new StudentDAO()).findUserByFieldValue(fields, values).get(0).getId();
		
	}
	
	public void removeStudent(int studentID) {
		
		(new StudentDAO()).deleteUserByFieldValue("idstudents", studentID);
		
	}

	public User findById(int id) {
		
		return (new TeacherDAO()).findUserByFieldValue("idteachers", id).get(0);
	}
	

}
