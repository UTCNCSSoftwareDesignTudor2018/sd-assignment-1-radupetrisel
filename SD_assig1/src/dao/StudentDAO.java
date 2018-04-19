package dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class StudentDAO extends UserDAO {

	public StudentDAO() {
		super();
		this.table = "students";
	}

	@Override
	protected List<? extends User> createUserFromResultSet(ResultSet rs) throws SQLException {
		
		List<Student> students = new ArrayList<Student>();
		while (rs.next()) {

			Student s = new Student();
			// id, firstName, lastName, cnp, phoneNumber, email, dob, address
			s.setId(rs.getInt("id" + table));
			s.setFirstName(rs.getString("firstName"));
			s.setLastName(rs.getString("lastName"));
			s.setCnp(rs.getString("cnp"));
			s.setPhoneNumber(rs.getString("phoneNumber"));
			s.setEmail(rs.getString("email"));
			s.setAddress(rs.getString("address"));
			s.setPassword(rs.getString("password"));
			s.setDeleted(rs.getBoolean("isDeleted"));
			students.add(s);
		}

		return students;
	}

}
