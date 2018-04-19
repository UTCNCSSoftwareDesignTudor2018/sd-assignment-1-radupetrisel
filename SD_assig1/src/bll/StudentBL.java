package bll;

import java.util.List;
import java.util.stream.Collectors;

import dao.CourseDAO;
import dao.EnrolDAO;
import dao.Student;
import dao.StudentDAO;
import dao.User;

public class StudentBL extends UserBL{
	
	public StudentBL() {
		this.dao = new StudentDAO();
	}
	
	private Student findStudentByEmail(String email) {

		Student s = null;
		
		try {

			s = (Student)(new StudentDAO()).findUserByFieldValue("email", email).get(0);
					
		} catch (IndexOutOfBoundsException ie) {

			return null;
		}

		return s;
	}

	public int login(String email, String password) {

		Student s = findStudentByEmail(email);
		
		if (s == null) {
			return -1;
		}

		if (!s.getPassword().equals(password)) {
			return -2;
		}
		
		if (s.isDeleted()) {
			return -3;
		}

		return s.getId();
	}

	public void enrol(int studentID, String courseName) {
		
		(new EnrolDAO()).createEnrol(studentID, (new CourseDAO()).findCourseByFieldValue("name", courseName).get(0).getId(), 0);
	}

	public List<Grade> viewGrades(int studentID) {
				
		return (new EnrolDAO()).findEnrolByFieldValue("studentId", studentID).stream().map(e -> new Grade(e.getCourse().getName(), e.getGrade())).collect(Collectors.toList());

	}

	@Override
	public User findById(int id) {
		
		return (new StudentDAO()).findUserByFieldValue("idstudents", id).get(0);
	}

}
