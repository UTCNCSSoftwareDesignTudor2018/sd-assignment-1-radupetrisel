package pl;

import java.io.File;

import bll.StudentBL;
import bll.TeacherBL;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.Background;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class Login {

	private Stage window;
	private Scene loginScene;
	private TextField username;
	private PasswordField password;
	private CheckBox isTeacher;
	private Button login;
	private Label err;
	
	public Login(Stage window, double width, double height) {
		
		this.window = window;
		this.window.setTitle("Login");
		
		VBox box = new VBox();
		box.setAlignment(Pos.CENTER);
		box.setSpacing(10);
		
		loginScene = new Scene(box, width, height);
		
		username = new TextField();
		username.setPromptText("username");
		username.setMaxSize(width / 2, 30);
		
		password = new PasswordField();
		password.setPromptText("password");
		password.setMaxSize(width / 2, 30);
		
		isTeacher = new CheckBox();
		isTeacher.setText("I am a teacher");
		
		login = new Button("Login");
		login.setOnAction(e -> handleLogin());
			
		box.getChildren().addAll(username, password, isTeacher, login);
		
		loginScene.setOnKeyPressed(e -> {
			
			if (e.getCode().equals(KeyCode.ENTER)) handleLogin();
			
		});
		
		err = new Label("Invalid username/password");
		err.setTextFill(Color.RED);
		err.setBackground(Background.EMPTY);
		err.setVisible(false);
		
		box.getChildren().add(err);
		
		this.window.setScene(loginScene);
	}
	
	private void handleLogin() {
		
		int id = 0;
		
		if (isTeacher.isSelected()) {
			
			id = (new TeacherBL()).login(username.getText(), password.getText());
			
			if (id > 0) {
				
				new TeacherWindow(this.window, id);
			}
			
		}
		else {
			
			id = (new StudentBL()).login(username.getText(), password.getText());
			
			if (id > 0) {
				
				new StudentWindow(this.window, id);
				
			}
			
		}
		
		if (id < 0) {
			
			this.err.setVisible(true);
			
		}
		
	}
	
}
