package dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class TeacherDAO extends UserDAO {

	public TeacherDAO() {
		super();
		this.table = "teachers";
	}

	@Override
	protected List<? extends User> createUserFromResultSet(ResultSet rs) throws SQLException {
		
		List<Teacher> teachers = new ArrayList<Teacher>();
		while (rs.next()) {

			Teacher t = new Teacher();
			// id, firstName, lastName, cnp, phoneNumber, email, dob, address
			t.setId(rs.getInt("id" + table));
			t.setFirstName(rs.getString("firstName"));
			t.setLastName(rs.getString("lastName"));
			t.setCnp(rs.getString("cnp"));
			t.setPhoneNumber(rs.getString("phoneNumber"));
			t.setEmail(rs.getString("email"));
			t.setAddress(rs.getString("address"));
			t.setPassword(rs.getString("password"));
			t.setDeleted(rs.getBoolean("isDeleted"));
			teachers.add(t);
		}

		return teachers;
	}
	
}
