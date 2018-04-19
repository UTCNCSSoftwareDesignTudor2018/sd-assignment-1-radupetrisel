package dao;

import static dao.DBDriver.getConnection;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class EnrolDAO{

	private final String table = "enrols";
	
	private List<Enrol> createEnrolsFromResultSet(ResultSet rs) throws SQLException {

		List<Enrol> enrols = new ArrayList<Enrol>();

		while (rs.next()) {

			Enrol e = new Enrol();
			e.setId(rs.getInt("idenrols"));
			e.setCourse((new CourseDAO()).findCourseByFieldValue("idcourses", rs.getInt("courseId")).get(0));
			e.setStudent((Student)(new StudentDAO()).findUserByFieldValue("idstudents", rs.getInt("studentId")).get(0));
			e.setGrade(rs.getInt("grade"));
			enrols.add(e);
		}

		return enrols;

	}

	public void createEnrol(int studentId, int courseId, Integer grade) {

		PreparedStatement statement;
		try {

			statement = getConnection()
					.prepareStatement("INSERT INTO asgn1.enrols (studentId, courseId, grade) VALUES(?, ?, ?);");
			statement.setInt(1, studentId);
			statement.setInt(2, courseId);
			statement.setInt(3, grade);

			statement.executeUpdate();

		} catch (SQLException e) {
			e.printStackTrace();
		}

	}
	
	public List<Enrol> findEnrolByFieldValue(String field, Object value){
		
		try {
			return this.createEnrolsFromResultSet((new GeneralDAO()).findByFieldValue(table, field, value));
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return null;
		
	}
	

	public List<Enrol> findEnrolByFieldValue(List<String> fields, List<Object> values) {

		try {
			return this.createEnrolsFromResultSet((new GeneralDAO()).findByFieldValue(table, fields, values));
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return null;
	}

	public void updateEnrol(int enrolId, String field, Object value) {
		
		(new GeneralDAO()).updateFieldValue(table, enrolId, field, value);
		
	}

	public void deleteEnrolsByFieldValue(String field, Object value) {
		
		(new GeneralDAO()).deleteByFieldValue(table, field, value);


	}

}
