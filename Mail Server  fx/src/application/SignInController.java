package application;

import java.io.IOException;
import javafx.scene.Node;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class SignInController {
	App app = new App();
	@FXML
	private Label status;
	
	@FXML
	private TextField emailField;
	
	@FXML
	private TextField passwordField;
	
	public void login(ActionEvent event) {
		boolean lab = app.signin(emailField.getText(), passwordField.getText());
		if(lab) {
			status.setText("Successful log in");
			try {
				goToMainScene(event);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}else {
			if(app.getFailedCase()==1) {
				status.setText("Empty Field");
			}else if(app.getFailedCase()==2) {
				status.setText("Invalid email");
			}else if(app.getFailedCase()==3) {
				status.setText("Email doesn't exist");
			}else if(app.getFailedCase()==4) {
				status.setText("Wrong password");
			}
		}
	}
	
	public void signupPage(ActionEvent event) throws IOException {
		Parent root = FXMLLoader.load(getClass().getResource("/application/signUp.fxml"));
		Scene signupPage = new Scene(root);
		Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
		window.setScene(signupPage);
		window.show();
	}
	
	public void goToMainScene (ActionEvent event) throws IOException {
		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(getClass().getResource("mainScene.fxml"));
		Parent root = loader.load();
		MainSceneController mainSceneController = loader.getController();
		mainSceneController.initData(app);
		Scene scene = new Scene(root);
		scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
		Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();
		window.setScene(scene);
		window.show();
	}
}
