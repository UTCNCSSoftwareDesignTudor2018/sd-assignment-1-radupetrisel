package bll;

import java.util.ArrayList;
import java.util.List;

import dao.Enrol;
import dao.EnrolDAO;
import dao.Student;
import dao.StudentDAO;
import dao.Teacher;
import dao.TeacherDAO;

public class TeacherBL extends UserBL{

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

		return t.getId();

	}
	
	public void createStudent(String firstName, String lastName, String address, String phoneNumber, String email,
			String cnp) {
		
		(new StudentDAO()).createUser(firstName, lastName, address, phoneNumber, email, cnp, "1234");
		
	}
	
	public Student viewStudent(int studentID) {
						
		return (Student)(new StudentDAO()).findUserByFieldValue("idstudents", studentID).get(0);
	}
	
	public void giveMarkToStudent (int courseID, int studentID, int grade) {
		
		List<String> fields = new ArrayList<String>();
		fields.add("studentId");
		fields.add("courseId");
		
		List<Object> values = new ArrayList<Object>();
		values.add(studentID);
		values.add(courseID);
		
		EnrolDAO ed = new EnrolDAO();
		
		Enrol e = ed.findEnrolByFieldValue(fields, values).get(0);
		
		ed.updateEnrol(e.getId(), "grade", grade);
	}
	
	public void removeStudent(int studentID) {
		
		(new StudentDAO()).deleteUserByFieldValue("idstudents", studentID);
		
	}
	

}
