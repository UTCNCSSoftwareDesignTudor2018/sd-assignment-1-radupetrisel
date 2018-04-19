package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBDriver {

	private static Connection connection = null;
	private static String user = "root";
	private static String pass = "asdf";

	private static void createConnection() {

		try {

			Class.forName("com.mysql.jdbc.Driver").newInstance();

			connection = DriverManager.getConnection(
					"jdbc:mysql://localhost:3306?" + "user=" + user + "&password=" + pass + "&useSSL=false");

		} catch (InstantiationException | IllegalAccessException | ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		}
	}

	public static void populateDatabase() {

		StudentDAO sd = new StudentDAO();
		TeacherDAO td = new TeacherDAO();
		CourseDAO cd = new CourseDAO();
		EnrolDAO ed = new EnrolDAO();

		sd.createUser("Radu", "Petrisel", "1960920125844", "0747673818", "radupetrisel@gmail.com", "Azuga 3", "asdf");
		sd.createUser("Diana", "Danila", "2951221125635", "0752030023", "lucia_diana1995@yahoo.com", "Izlazului 2",
				"ddiana");
		sd.createUser("Peter", "Zavaczki", "1970131134856", "0742737506", "bloopeti@gmail.com", "Intrelacuri 3",
				"zpeter");

		td.createUser("Tudor", "Vlad", "1012345678911", "0712345678", "tudor.vlad@cs.utcluj.ro", "teacher_address_1",
				"tvlad");
		td.createUser("Emil", "Chifu", "1123456789123", "0264401449", "emil.chifu@cs.utcluj.ro", "Baritiu 28",
				"echifu");
		td.createUser("Florin", "Oniga", "1987654321987", "0264401457", "forin.oniga@cs.utcluj.ro", "Baritiu 26",
				"foniga");

		cd.createCourse("Software Design", 1);
		cd.createCourse("Formal Languages and Translators", 2);
		cd.createCourse("Image Processing", 3);

		ed.createEnrol(1, 2, 0);

	}

	public static void initDatabase() {

		try {

			getConnection().prepareStatement("CREATE DATABASE asgn1").executeUpdate();
			getConnection().prepareStatement("CREATE TABLE `asgn1`.`students` ("
					+ "`idstudents` INT NOT NULL AUTO_INCREMENT," + " `firstName` VARCHAR(45) NOT NULL,"
					+ "  `lastName` VARCHAR(45) NOT NULL," + "  `cnp` VARCHAR(13) NOT NULL,"
					+ "  `phoneNumber` VARCHAR(10) NOT NULL," + "  `email` VARCHAR(45) NOT NULL,"
					+ "  `address` VARCHAR(45) NOT NULL," + "  `password` VARCHAR(45) NOT NULL, "
					+ "  PRIMARY KEY (`idstudents`)," + "  UNIQUE INDEX `cnp_UNIQUE` (`cnp` ASC));").executeUpdate(); // students
																														// table

			getConnection()
					.prepareStatement("CREATE TABLE `asgn1`.`teachers` ( "
							+ " `idteachers` INT NOT NULL AUTO_INCREMENT," + " `firstName` VARCHAR(45) NOT NULL,"
							+ "  `lastName` VARCHAR(45) NOT NULL," + "  `cnp` VARCHAR(13) NOT NULL,"
							+ "  `phoneNumber` VARCHAR(10) NOT NULL," + "  `email` VARCHAR(45) NOT NULL,"
							+ "  `address` VARCHAR(45) NOT NULL," + "  `password` VARCHAR(45) NOT NULL, "
							+ "  PRIMARY KEY (`idteachers`)," + "  UNIQUE INDEX `cnp_UNIQUE` (`cnp` ASC));")
					.executeUpdate(); // teachers table

			getConnection().prepareStatement("CREATE TABLE `asgn1`.`courses` ("
					+ "  `idcourses` INT NOT NULL AUTO_INCREMENT," + "  `name` VARCHAR(45) NOT NULL,"
					+ "  `teacherID` INT NULL," + "  PRIMARY KEY (`idcourses`), "
					+ "  INDEX `teacher_idx` (`teacherID` ASC)," + "  CONSTRAINT `teacher`"
					+ "    FOREIGN KEY (`teacherID`)" + "    REFERENCES `asgn1`.`teachers` (`idteachers`)"
					+ "   ON DELETE NO ACTION" + "    ON UPDATE NO ACTION);").executeUpdate(); // courses

			getConnection()
					.prepareStatement("CREATE TABLE `asgn1`.`enrols` (" + "  `idenrols` INT NOT NULL AUTO_INCREMENT,"
							+ "  `studentId` INT NOT NULL," + "  `courseId` INT NOT NULL," + "  `grade` INT NULL,"
							+ "  PRIMARY KEY (`idenrols`)," + "  INDEX `course_idx` (`courseId` ASC),"
							+ "INDEX `student_idx` (`studentId` ASC)," + "  CONSTRAINT `student`"
							+ "    FOREIGN KEY (`studentId`)" + "    REFERENCES `asgn1`.`students` (`idstudents`)"
							+ "    ON DELETE NO ACTION" + "    ON UPDATE NO ACTION," + "  CONSTRAINT `course`"
							+ "    FOREIGN KEY (`courseId`)" + "    REFERENCES `asgn1`.`courses` (`idcourses`)"
							+ "    ON DELETE NO ACTION" + "    ON UPDATE NO ACTION);")
					.executeUpdate(); // student_to_course table

			getConnection()
					.prepareStatement("ALTER TABLE `asgn1`.`courses` " + "ADD UNIQUE INDEX `name_UNIQUE` (`name` ASC);")
					.executeUpdate();

			getConnection()
					.prepareStatement(
							"ALTER TABLE `asgn1`.`students`" + "ADD UNIQUE INDEX `email_UNIQUE` (`email` ASC);")
					.executeUpdate();

			getConnection()
					.prepareStatement(
							"ALTER TABLE `asgn1`.`teachers`" + "ADD UNIQUE INDEX `email_UNIQUE` (`email` ASC);")
					.executeUpdate();

			getConnection().prepareStatement("ALTER TABLE `asgn1`.`students`"
					+ " ADD COLUMN `isDeleted` TINYINT NOT NULL DEFAULT 0 AFTER `password`;").executeUpdate();

			getConnection()
					.prepareStatement("ALTER TABLE `asgn1`.`teachers` "
							+ "ADD COLUMN `isDeleted` TINYINT NOT NULL DEFAULT 0 AFTER `password`;")
					.executeUpdate();

		} catch (SQLException e) {
			e.printStackTrace();
		}

	}

	public static Connection getConnection() {

		if (connection == null)
			createConnection();

		return connection;
	}

}
