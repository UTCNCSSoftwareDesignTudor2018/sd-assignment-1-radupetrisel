package dao;

import static dao.DBDriver.getConnection;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class GeneralDAO {

	public ResultSet findByFieldValue(String table, List<String> fields, List<Object> values) {

		try {

			String statement_string = "SELECT * FROM asgn1." + table + " WHERE ";

			int i = 0;
			for (i = 0; i < fields.size() - 1; i++) {

				statement_string += fields.get(0) + "=? and ";

			}

			statement_string += fields.get(i) + "=?";

			PreparedStatement statement = getConnection().prepareStatement(statement_string);

			i = 1;

			for (Object o : values) {
				
				statement.setObject(i++, o);
			}

			return statement.executeQuery();
			

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return null;
	}

	public ResultSet findByFieldValue(String table, String field, Object value) {

		try {

			PreparedStatement statement = getConnection()
					.prepareStatement("SELECT * FROM asgn1." + table + " WHERE " + field + "=?");
			statement.setObject(1, value);

			return statement.executeQuery();

		} catch (SQLException e) {

			e.printStackTrace();
		}

		return null;
	}

	public void deleteByFieldValue(String table, String field, Object value) {

		PreparedStatement statement;

		try {

			statement = getConnection().prepareStatement("DELETE FROM asgn1." + table + " WHERE " + field + "=?;");
			statement.setObject(1, value);
			statement.executeUpdate();

		} catch (SQLException e) {
			e.printStackTrace();
		}

	}

	public void updateFieldValue(String table, int id, String field, Object value) {

		PreparedStatement statement;
		try {

			statement = getConnection()
					.prepareStatement("UPDATE asgn1." + table + " SET " + field + "=? WHERE asgn1." + table + "." + "id" + table + "=?;");
			statement.setObject(1, value);
			statement.setInt(2, id);
			statement.executeUpdate();

		} catch (SQLException e) {
			e.printStackTrace();
		}

	}

}
