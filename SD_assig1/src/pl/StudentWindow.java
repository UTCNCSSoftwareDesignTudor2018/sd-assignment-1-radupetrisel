package pl;

import bll.Grade;
import bll.StudentBL;
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
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class StudentWindow extends UserWindow {

	public StudentWindow(Stage window, int userId) {
		
		super(window, userId, new StudentBL());
		window.setTitle("Student");
		
		VBox right = new VBox();
		right.setAlignment(Pos.CENTER_LEFT);
		right.setSpacing(10);
		
		Button enrol = new Button("Enrol");
		Button viewGrades = new Button("View grades");
		Button deleteProfile = new Button("Delete profile");
		
		right.getChildren().addAll(enrol, viewGrades, deleteProfile);
		this.setRight(right);
		
		enrol.setOnAction(e -> enrol());
		viewGrades.setOnAction(e -> viewGrades());
		deleteProfile.setOnAction(e -> deleteProfile());
	}

	private void deleteProfile() {
		
		Stage alert = new Stage();
		alert.initModality(Modality.APPLICATION_MODAL);
		alert.centerOnScreen();
		
		VBox box = new VBox();
		Scene scene = new Scene(box, 100, 100);
		box.setAlignment(Pos.CENTER);
		box.setSpacing(10);
		
		Text alertText = new Text("Are you sure?");
		Button yes = new Button("Yes");
		Button no = new Button("No");
		
		HBox textBox = new HBox();
		textBox.setAlignment(Pos.CENTER);
		
		HBox buttonBox = new HBox();
		buttonBox.setAlignment(Pos.CENTER);
		
		box.getChildren().addAll(textBox, buttonBox);
		
		textBox.getChildren().add(alertText);
		buttonBox.getChildren().addAll(yes, no);
		
		yes.setOnAction(e -> {
			(new StudentBL()).deleteStudent(userId);
			alert.close();
			new Login(this.window, 250, 300);
		});
		
		no.setOnAction(e -> alert.close());
		
		alert.setScene(scene);
		alert.showAndWait();
	}

	private void viewGrades() {
		
		TableView<Grade> grades = new TableView<Grade>();
		
		grades.setEditable(false);
		
		TableColumn<Grade, String> course = new TableColumn<Grade, String>("Course");
		TableColumn<Grade, Integer> grade = new TableColumn<Grade, Integer>("Grade");
		
		course.setCellValueFactory(new PropertyValueFactory<Grade, String>("courseName"));
		grade.setCellValueFactory(new PropertyValueFactory<Grade, Integer>("grade"));
		
		grades.setItems(FXCollections.observableArrayList((new StudentBL()).viewGrades(userId)));
		
		grades.getColumns().addAll(course, grade);
		
		this.setCenter(grades);	
	}

	private void enrol() {
		
		HBox box = new HBox();
		box.setAlignment(Pos.CENTER);
		box.setSpacing(10);
		
		Label courseNameLabel = new Label("Course ");
		TextField courseName = new TextField();
		Text success = new Text("Success");
		success.setFill(Color.GREEN);
		success.setVisible(false);
		
		box.setOnKeyPressed(e -> {
			
			if (e.getCode().equals(KeyCode.ENTER)) {
				
				(new StudentBL()).enrol(userId, courseName.getText());
				success.setVisible(true);
			}
		});
		
		box.getChildren().addAll(courseNameLabel, courseName, success);
		this.setCenter(box);

	}

}
