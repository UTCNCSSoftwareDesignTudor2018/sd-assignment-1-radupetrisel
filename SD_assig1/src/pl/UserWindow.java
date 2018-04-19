package pl;

import java.awt.Dimension;
import java.awt.Toolkit;

import bll.UserBL;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;

public abstract class UserWindow {
	
	protected Stage window;
	private BorderPane layout;
	private Scene mainScene;
	protected int userId;
	private UserBL userBL;
	private VBox profileBox;

	public UserWindow(Stage window, int userId, UserBL userBL) {
		
		this.userId = userId;
		this.userBL = userBL;
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();

		this.layout = new BorderPane();

		this.mainScene = new Scene(this.layout, screenSize.getWidth() / 2, screenSize.getHeight() / 2);
		window.setScene(this.mainScene);
		window.centerOnScreen();

		VBox left = new VBox();
		Button updatePassword = new Button("Update password");
		Button updateAddress = new Button("Update address");
		Button updateEmail = new Button("Update email");
		Button viewProfile = new Button("View profile");
		Button logout = new Button("Logout");

		left.setSpacing(20);
		left.setAlignment(Pos.CENTER);
		left.getChildren().addAll(viewProfile, updatePassword, updateAddress, updateEmail);
		this.layout.setLeft(left);

		VBox bottom = new VBox();
		bottom.setAlignment(Pos.BOTTOM_RIGHT);
		bottom.getChildren().add(logout);
		this.setBottom(bottom);
		
		viewProfile.setOnAction(e -> initProfileBox());
		updatePassword.setOnAction(e -> updatePassword());
		updateAddress.setOnAction(e -> updateAddress());
		updateEmail.setOnAction(e -> updateEmail());
		logout.setOnAction(e -> new Login(window, 250, 300));

		this.initProfileBox();
		this.displayHelloMessage();
		
		this.window = window;
	}

	private void updateEmail() {
		
		HBox box = new HBox();
		box.setAlignment(Pos.CENTER);
		box.setSpacing(10);
		
		Label newEmailLabel = new Label("New email: ");
		TextField newEmailField = new TextField();
		Text success = new Text("Success");
		success.setFill(Color.GREEN);
		success.setVisible(false);
		
		box.setOnKeyPressed(e -> {
			
			if (e.getCode().equals(KeyCode.ENTER)) {
				
				userBL.updateEmail(userId, newEmailField.getText());
				success.setVisible(true);		
			}
		});
		
		box.getChildren().addAll(newEmailLabel, newEmailField, success);
		this.setCenter(box);
		
	}

	private void updateAddress() {

		HBox box = new HBox();
		box.setAlignment(Pos.CENTER);
		box.setSpacing(10);
		
		Label newAddressLabel = new Label("New address: ");
		TextField newAddressField = new TextField();
		Text success = new Text("Success");
		success.setFill(Color.GREEN);
		success.setVisible(false);
		
		box.setOnKeyPressed(e -> {
			
			if (e.getCode().equals(KeyCode.ENTER)) {
				
				userBL.updateAddress(userId, newAddressField.getText());
				success.setVisible(true);		
			}
		});
		
		box.getChildren().addAll(newAddressLabel, newAddressField, success);
		this.setCenter(box);
		
	}

	private void initProfileBox() {
		
		profileBox = new VBox();
		profileBox.setAlignment(Pos.CENTER);
		profileBox.setSpacing(7);
		
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
		
		Text firstNameText = new Text(userBL.findById(userId).getFirstName());
		Text lastNameText = new Text(userBL.findById(userId).getLastName());
		Text cnpText = new Text(userBL.findById(userId).getCnp());
		Text addressText = new Text(userBL.findById(userId).getAddress());
		Text emailText = new Text(userBL.findById(userId).getEmail());
		Text phoneNumberText = new Text(userBL.findById(userId).getPhoneNumber());
		
		firstNameBox.getChildren().addAll(firstNameLabel, firstNameText);
		lastNameBox.getChildren().addAll(lastNameLabel, lastNameText);
		cnpBox.getChildren().addAll(cnpLabel, cnpText);
		addressBox.getChildren().addAll(addressLabel, addressText);
		emailBox.getChildren().addAll(emailLabel, emailText);
		phoneNumberBox.getChildren().addAll(phoneNumberLabel, phoneNumberText);
		
		
		profileBox.getChildren().addAll(firstNameBox, lastNameBox, cnpBox, addressBox, emailBox, phoneNumberBox);
		this.setCenter(profileBox);
	}

	private void updatePassword() {

		VBox box = new VBox();
		box.setAlignment(Pos.CENTER);
		box.setSpacing(20);

		PasswordField oldPassword = new PasswordField();
		oldPassword.setPromptText("Old password");
		PasswordField newPassword = new PasswordField();
		newPassword.setPromptText("New password");
		PasswordField confirmPassword = new PasswordField();
		confirmPassword.setPromptText("Confirm new password");
		Button update = new Button("Update");

		box.setPrefWidth(120);

		oldPassword.setMaxWidth(Region.USE_PREF_SIZE);
		newPassword.setMaxWidth(Region.USE_PREF_SIZE);
		confirmPassword.setMaxWidth(Region.USE_PREF_SIZE);

		box.getChildren().addAll(oldPassword, newPassword, confirmPassword, update);

		oldPassword.focusedProperty().addListener(c -> {

			String password = userBL.findById(userId).getPassword();

			if (!oldPassword.isFocused()) {

				if (!oldPassword.getText().equals(password))
					oldPassword.setStyle("-fx-border-color: red ;" + " -fx-border-width: 1px ; ");
				else
					oldPassword.setStyle(" -fx-border-color: lime ; -fx-border-width: 2px;");

			}

			else
				oldPassword.setStyle(null);

		});

		confirmPassword.setOnKeyReleased(e -> {

			if (confirmPassword.getText().equals(newPassword.getText())) {

				confirmPassword.setStyle("-fx-border-color: lime; -fx-border-width: 2px;");
				newPassword.setStyle("-fx-border-color: lime; -fx-border-width: 2px;");
			} else {

				confirmPassword.setStyle("-fx-border-color: red; -fx-border-width: 1px;");
				newPassword.setStyle("-fx-border-color: red; -fx-border-width: 1px;");
			}

		});

		update.setOnAction(e -> handlePasswordUpdate(userBL.findById(userId).getPassword(), oldPassword.getText(),
				newPassword.getText(), confirmPassword.getText()));

		box.setOnKeyPressed(e -> {
			if (e.getCode().equals(KeyCode.ENTER))
				handlePasswordUpdate(userBL.findById(userId).getPassword(), oldPassword.getText(),
						newPassword.getText(), confirmPassword.getText());
		});
		this.layout.setCenter(box);

	}

	private void handlePasswordUpdate(String oldPassword, String oldPass, String newPass, String confirmPass) {

		if (oldPassword.equals(oldPass) && newPass.equals(confirmPass)) {

			userBL.updatePassword(userId, newPass);

			Stage success = new Stage();
			success.centerOnScreen();

			VBox successBox = new VBox();
			successBox.setAlignment(Pos.CENTER);
			successBox.setSpacing(10);

			Scene successScene = new Scene(successBox, 200, 150);
			Text successText = new Text("Password updated successfully!");
			Button ok = new Button("Ok");

			successBox.getChildren().addAll(successText, ok);

			ok.setOnAction(eok -> {

				success.close();
				layout.setCenter(profileBox);
			});

			success.setScene(successScene);
			success.setTitle("Success");
			success.initModality(Modality.APPLICATION_MODAL);
			success.showAndWait();
		}

	}

	private void displayHelloMessage() {

		Text hello = new Text("Hello " + userBL.findById(userId).getFirstName() + "!");
		hello.setFont(Font.font("Times New Roman", 25));
		hello.setSmooth(true);

		VBox helloBox = new VBox();
		helloBox.getChildren().add(hello);
		helloBox.setAlignment(Pos.CENTER);

		this.layout.setTop(helloBox);
	}

	protected void setLeft(Node left) {
		this.layout.setLeft(left);
	}

	protected void setRight(Node right) {
		this.layout.setRight(right);
	}

	protected void setTop(Node top) {
		this.layout.setTop(top);
	}

	protected void setBottom(Node bottom) {
		this.layout.setBottom(bottom);
	}

	protected void setCenter(Node center) {
		this.layout.setCenter(center);
	}

}
