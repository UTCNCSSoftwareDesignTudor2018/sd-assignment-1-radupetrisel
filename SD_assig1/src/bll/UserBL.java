package bll;

import dao.StudentDAO;
import dao.User;
import dao.UserDAO;

public abstract class UserBL {
	
	protected UserDAO dao;
	
	public abstract User findById(int id);
	
	public void updateEmail(int userId, String newEmail) {

		dao.updateUserFieldValue(userId, "email", newEmail);

	}

	public void updateAddress(int userId, String newAddress) {

		dao.updateUserFieldValue(userId, "address", newAddress);

	}

	public void updatePassword(int userId, String newPassword) {

		dao.updateUserFieldValue(userId, "password", newPassword);

	}
	
	public void deleteStudent(int userId) {
		
		(new StudentDAO()).updateUserFieldValue(userId, "isDeleted", true);	
	}
	
	public abstract int login(String username, String password);
	

}
