package dao;

import static dao.DBDriver.getConnection;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CourseDAO {

	private final String table = "courses";
	/*
	 * name - not null teacherId - < max(teacherIds)
	 */
	public void createCourse(String name, int teacherId) {

		assert name != null : "Name null.\n";

		try {
			assert getConnection()
					.prepareStatement(
							"SELECT * FROM asgn1.teachers " + "WHERE asgn1.teachers.idteachers = " + teacherId)
					.executeQuery() != null : "Invalid teacher";
			PreparedStatement statement = getConnection()
					.prepareStatement("INSERT INTO asgn1.courses (name, teacherId)" + "VALUES(?, ?);");

			statement.setString(1, name);
			statement.setInt(2, teacherId);

			statement.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}

	}

	private List<Course> createCoursesFromResultSet(ResultSet rs) throws SQLException {

		List<Course> courses = new ArrayList<Course>();

		while (rs.next()) {

			Course c = new Course();
			c.setId(rs.getInt("idcourses"));
			c.setName(rs.getString("name"));
			c.setTeacher((Teacher)(new TeacherDAO()).findUserByFieldValue("idteachers", rs.getInt("teacherID")).get(0));
			courses.add(c);
		}

		return courses;
	}
	
	public void updateCourseFieldValue(int courseId, String field, Object value) {
		
		(new GeneralDAO()).updateFieldValue(table, courseId, field, value);
		
	}
	
	public List<Course> findCourseByFieldValue(String field, Object value){
		
		try {
			return this.createCoursesFromResultSet((new GeneralDAO()).findByFieldValue(table, field, value));
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return null;
	}
	
	public List<Course> findCourseByFieldValue(List<String> fields, List<Object> values){
		
		try {
			return this.createCoursesFromResultSet((new GeneralDAO()).findByFieldValue(table, fields, values));
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return null;
	}
	
	public void deleteCourseByFieldValue(String field, Object value) {
		
		(new GeneralDAO()).deleteByFieldValue(table, field, value);
		
	}

}
