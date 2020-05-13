package application;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.fxml.Initializable;

public class SignUpController implements Initializable{
	App app = new App();
	ObservableList<String> Gender = FXCollections.observableArrayList("Male","Female");
	
	@FXML
	private ComboBox<String> gender;
	@FXML
	private DatePicker date;
	@FXML
	private TextField emailField;
	@FXML
	private TextField passwordField;
	@FXML
	private TextField firstnameField;
	@FXML
	private TextField lastnameField;
	@FXML
	private Label status;
	

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		gender.setItems(Gender);
	}
	
	public void SignUp(ActionEvent event) {
		
		Contact contact = new Contact();
		((Contact)contact).setEmail(emailField.getText());
		((Contact)contact).setPassword(passwordField.getText());
		((Contact)contact).setFirstName(firstnameField.getText());
		((Contact)contact).setLastName(lastnameField.getText());
		((Contact)contact).setGender(gender.getValue());
		if(date.getValue()!=null)
		((Contact)contact).setBirthdate(date.getValue().toString());
		
		boolean lab = app.signup(contact);
		if(lab) {
			status.setText("Successful sign up");
			try {
				goToMainScene(event);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}else {
			if(app.getFailedCase()==1) {
				status.setText("Empty field(s)");
			}else if(app.getFailedCase()==2) {
				status.setText("Invalid email");
			}else if(app.getFailedCase()==3) {
				status.setText("Existing email");
			}
	    }
      }
	
	public void backToSignInPage (ActionEvent event) throws IOException {
		Parent root = FXMLLoader.load(getClass().getResource("signIn.fxml"));
		Scene signInPage = new Scene(root);
		Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
		window.setScene(signInPage);
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
