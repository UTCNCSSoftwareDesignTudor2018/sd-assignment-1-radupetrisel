package pl;

import bll.Grade;
import bll.StudentBL;
import bll.TeacherBL;
import javafx.collections.FXCollections;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class TeacherWindow extends UserWindow {

	public TeacherWindow(Stage window, int userId) {

		super(window, userId, new TeacherBL());
		window.setTitle("Teacher");

		VBox right = new VBox();
		right.setAlignment(Pos.CENTER);
		right.setSpacing(10);

		Button createStudent = new Button("Add student");
		Button deleteStudent = new Button("Delete student");
		Button gradeStudent = new Button("Mark student");
		Button viewStudent = new Button("View student");

		right.getChildren().addAll(createStudent, gradeStudent, viewStudent, deleteStudent);

		createStudent.setOnAction(e -> createStudent());
		deleteStudent.setOnAction(e -> deleteStudent());
		gradeStudent.setOnAction(e -> gradeStudent());
		viewStudent.setOnAction(e -> viewStudent());

		this.setRight(right);
	}

	private void createStudent() {

		HBox bigBox = new HBox();
		bigBox.setAlignment(Pos.CENTER);

		VBox labelColumn = new VBox();
		labelColumn.setAlignment(Pos.CENTER_RIGHT);
		labelColumn.setSpacing(20);

		VBox inputColumn = new VBox();
		inputColumn.setAlignment(Pos.CENTER_LEFT);
		inputColumn.setSpacing(10);

		bigBox.getChildren().addAll(labelColumn, inputColumn);

		Label firstNameLabel = new Label("First name: ");
		firstNameLabel.setFont(Font.font("Verdana", FontWeight.BOLD, 12));
		Label lastNameLabel = new Label("Last name: ");
		lastNameLabel.setFont(Font.font("Verdana", FontWeight.BOLD, 12));
		Label cnpLabel = new Label("CNP: ");
		cnpLabel.setFont(Font.font("Verdana", FontWeight.BOLD, 12));
		Label addressLabel = new Label("Address: ");
		addressLabel.setFont(Font.font("Verdana", FontWeight.BOLD, 12));
		Label emailLabel = new Label("Email: ");
		emailLabel.setFont(Font.font("Verdana", FontWeight.BOLD, 12));
		Label phoneNumberLabel = new Label("Phone: ");
		phoneNumberLabel.setFont(Font.font("Verdana", FontWeight.BOLD, 12));

		TextField firstNameField = new TextField();
		TextField lastNameField = new TextField();
		TextField cnpField = new TextField();
		TextField addressField = new TextField();
		TextField emailField = new TextField();
		TextField phoneNumberField = new TextField();

		Button create = new Button("Create");
		create.setAlignment(Pos.BOTTOM_RIGHT);

		labelColumn.getChildren().addAll(firstNameLabel, lastNameLabel, cnpLabel, addressLabel, emailLabel,
				phoneNumberLabel);

		inputColumn.getChildren().addAll(firstNameField, lastNameField, cnpField, addressField, emailField,
				phoneNumberField, create);

		create.setOnAction(e -> {
			(new TeacherBL()).createStudent(firstNameField.getText(), lastNameField.getText(), addressField.getText(),
					phoneNumberField.getText(), emailField.getText(), cnpField.getText());
			
			Stage alert = new Stage();
			alert.initModality(Modality.APPLICATION_MODAL);
			
			VBox alertBox = new VBox();
			alertBox.setAlignment(Pos.CENTER);
			alertBox.setSpacing(10);
			
			Scene alertScene = new Scene(alertBox, 150, 50);
			
			Text msg = new Text("Student created succesfully!");
			Button ok = new Button("Ok");
			
			alertBox.getChildren().addAll(msg, ok);
			
			ok.setOnAction(a -> alert.close());
			
			alert.setScene(alertScene);
			alert.showAndWait();			
		});
		
		this.setCenter(bigBox);
	}

	private void deleteStudent() {
		
		Stage delete = new Stage();
		delete.initModality(Modality.APPLICATION_MODAL);
		
		VBox box = new VBox();
		box.setAlignment(Pos.CENTER);
		
		Scene scene = new Scene(box, 200, 100);
		
		TextField firstName = new TextField();
		firstName.setPromptText("First name");
		
		TextField lastName = new TextField();
		lastName.setPromptText("Last name");
		
		Button del = new Button("Delete");
		
		box.getChildren().addAll(firstName, lastName, del);
		
		del.setOnAction(e -> {
			
			TeacherBL tbl = new TeacherBL();
			
			tbl.deleteStudent(tbl.findStudentByName(firstName.getText(), lastName.getText()));
			
			delete.close();
		});
		
		delete.setScene(scene);
		delete.showAndWait();
	}

	private void gradeStudent() {
		
		HBox box = new HBox();
		box.setAlignment(Pos.CENTER);
		box.setSpacing(10);
		
		TextField firstName = new TextField();
		firstName.setPromptText("First name");
		
		TextField lastName = new TextField();
		lastName.setPromptText("Last name");
		
		TextField course = new TextField();
		course.setPromptText("Course");
		
		TextField grade = new TextField();
		grade.setPromptText("Grade");
		
		box.getChildren().addAll(firstName, lastName, course, grade);
		
		box.setOnKeyPressed(e -> {
			
			if (e.getCode().equals(KeyCode.ENTER)) {
				
				TeacherBL tbl = new TeacherBL();
				tbl.giveMarkToStudent(course.getText(), tbl.findStudentByName(firstName.getText(), lastName.getText()), Integer.parseInt(grade.getText()));
				
				Stage alert = new Stage();
				alert.initModality(Modality.APPLICATION_MODAL);
				
				VBox vbox = new VBox();
				Scene alertS = new Scene(vbox, 100, 100);
				
				vbox.getChildren().add(new Text("Success"));
				
				Button ok = new Button("Ok");
				
				vbox.getChildren().add(ok);
				
				ok.setOnAction(a -> alert.close());
								
				alert.setScene(alertS);
				alert.showAndWait();
			}
			
		});
				
		this.setCenter(box);
	}

	private void viewStudent() {
		
		HBox initBox = new HBox();
		initBox.setAlignment(Pos.CENTER);
		initBox.setSpacing(7);
		
		TextField firstName = new TextField();
		firstName.setPromptText("First name");
		
		TextField lastName = new TextField();
		lastName.setPromptText("Last name");
		
		this.setCenter(initBox);
		initBox.setOnKeyPressed(e -> {
			
			if (e.getCode().equals(KeyCode.ENTER)) {
				showStudent((new TeacherBL()).findStudentByName(firstName.getText(), lastName.getText()));
			}
		});
		
		initBox.getChildren().addAll(firstName, lastName);
		
		this.setCenter(initBox);
		
	}
	
	private void showStudent(int id) {
		
		VBox bigBox = new VBox();
		bigBox.setAlignment(Pos.CENTER);
		bigBox.setSpacing(10);
		
		VBox column = new VBox();
		column.setAlignment(Pos.CENTER);
		
		HBox firstNameBox = new HBox();
		HBox lastNameBox = new HBox();
		HBox cnpBox = new HBox();
		HBox addressBox = new HBox();
		HBox emailBox = new HBox();
		HBox phoneNumberBox = new HBox();
		
		firstNameBox.setAlignment(Pos.CENTER);
		lastNameBox.setAlignment(Pos.CENTER);
		cnpBox.setAlignment(Pos.CENTER);
		addressBox.setAlignment(Pos.CENTER);
		emailBox.setAlignment(Pos.CENTER);
		phoneNumberBox.setAlignment(Pos.CENTER);
		
		
		Label firstNameLabel = new Label("First name: ");
		firstNameLabel.setFont(Font.font("Verdana", FontWeight.BOLD, 12));
		Label lastNameLabel = new Label("Last name: ");
		lastNameLabel.setFont(Font.font("Verdana", FontWeight.BOLD, 12));
		Label cnpLabel = new Label("CNP: ");
		cnpLabel.setFont(Font.font("Verdana", FontWeight.BOLD, 12));
		Label addressLabel = new Label("Address: ");
		addressLabel.setFont(Font.font("Verdana", FontWeight.BOLD, 12));
		Label emailLabel = new Label("Email: ");
		emailLabel.setFont(Font.font("Verdana", FontWeight.BOLD, 12));
		Label phoneNumberLabel = new Label("Phone: ");
		phoneNumberLabel.setFont(Font.font("Verdana", FontWeight.BOLD, 12));
		
		StudentBL sbl = new StudentBL();
		
		Text firstNameText = new Text(sbl.findById(id).getFirstName());
		Text lastNameText = new Text(sbl.findById(id).getLastName());
		Text cnpText = new Text(sbl.findById(id).getCnp());
		Text addressText = new Text(sbl.findById(id).getAddress());
		Text emailText = new Text(sbl.findById(id).getEmail());
		Text phoneNumberText = new Text(sbl.findById(id).getPhoneNumber());
		
		firstNameBox.getChildren().addAll(firstNameLabel, firstNameText);
		lastNameBox.getChildren().addAll(lastNameLabel, lastNameText);
		cnpBox.getChildren().addAll(cnpLabel, cnpText);
		addressBox.getChildren().addAll(addressLabel, addressText);
		emailBox.getChildren().addAll(emailLabel, emailText);
		phoneNumberBox.getChildren().addAll(phoneNumberLabel, phoneNumberText);
		
		column.getChildren().addAll(firstNameBox, lastNameBox, cnpBox, addressBox, emailBox, phoneNumberBox);
		
		bigBox.getChildren().add(column);
		this.setCenter(bigBox);
		
		TableView<Grade> table = new TableView<Grade>();
		
		TableColumn<Grade, String> course = new TableColumn<Grade, String>("Course");
		course.setCellValueFactory(new PropertyValueFactory<Grade, String>("courseName"));
		
		TableColumn<Grade, Integer> grade = new TableColumn<Grade, Integer>("Grade");
		grade.setCellValueFactory(new PropertyValueFactory<Grade, Integer>("grade"));
		
		table.getColumns().addAll(course, grade);
		
		table.setItems(FXCollections.observableArrayList(sbl.viewGrades(id)));
		
		bigBox.getChildren().add(table);		
	}
}
