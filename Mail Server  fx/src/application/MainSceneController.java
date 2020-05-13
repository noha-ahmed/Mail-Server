package application;

import java.io.IOException;
import java.net.URL;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.ResourceBundle;

import MyDataStructures.DLinkedList;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.fxml.Initializable;

public class MainSceneController implements Initializable{
	public App app;
	@FXML
	private ListView<String> mailsFoldersView;
	@FXML 
	private Label test;
	@FXML
	private Label nameLbl;
	@FXML
	private Label emailLbl;
	@FXML
	private TableView<ObsEmailInfo> emailsTable;
	@FXML
	private Label pageLbl;
	@FXML
	private ComboBox<String> sortBy;
	@FXML
	private ComboBox<String> filterBy;
	@FXML
	private TextField searchTxtField;
	@FXML
	private DatePicker datePicker;
	@FXML
	private ComboBox<String> moveTo;
	@FXML
	private Label mailsFolderLbl;
	@FXML
	private TextField folderNameTxtField;
	@FXML
	private Label errorLbl;

	private ObservableList<ObsEmailInfo> shownEmails ;
	private ObservableList<String> attributes = FXCollections.observableArrayList("Date","Subject","Sender","Priority");
	private ObservableList<String> foldersNames;
	private Folder currentFolder;
	private Sort currentSort = null;
	private Filter currentFilter = null;
	private int currentPage = 1;
	private EmailInfo[] emails;
	
	
	@Override
	public void initialize(URL location, ResourceBundle resources){
		// TODO Auto-generated method stub
		
		TableColumn<ObsEmailInfo, String> select = new TableColumn<ObsEmailInfo,String>("select");
		select.setCellValueFactory(new PropertyValueFactory<ObsEmailInfo,String>("select"));
		select.setPrefWidth(50);
		TableColumn<ObsEmailInfo, String> subject = new TableColumn<ObsEmailInfo,String>("Subject");
		subject.setCellValueFactory(new PropertyValueFactory<ObsEmailInfo,String>("subject"));
		subject.setPrefWidth(137);
		TableColumn<ObsEmailInfo, String> sender = new TableColumn<ObsEmailInfo,String>("Sender");
		sender.setCellValueFactory(new PropertyValueFactory<ObsEmailInfo,String>("sender"));
		sender.setPrefWidth(137);
		TableColumn<ObsEmailInfo,String> date = new TableColumn<ObsEmailInfo,String>("Date");
		date.setCellValueFactory(new PropertyValueFactory<ObsEmailInfo,String>("date"));
		date.setPrefWidth(137);
		emailsTable.getColumns().addAll(select,subject,sender,date);
		emailsTable.setItems(shownEmails);
		emailsTable.setPlaceholder(new Label("No emails to show"));
	//	sortBy.setItems(attributes);
	//	filterBy.setItems(attributes);
	}

	
	public void initData (App app) {
		this.app = app;
		foldersNames = FXCollections.observableArrayList();
		String[] folders = app.listMailsFolders();
		for (String folder : folders) {
			foldersNames.add(folder);
		} 
		mailsFoldersView.setItems(foldersNames);
		nameLbl.setText(app.getCurrentUser().getFirstName()+" " + app.getCurrentUser().getLastName());
		emailLbl.setText(app.getCurrentUser().getEmail());
		currentFolder = app.setMailFolder("Inbox");
		app.setViewingOptions(currentFolder,null,null);
		showEmails(currentPage);
		sortBy.setItems(attributes);
		filterBy.setItems(attributes);
		moveTo.setItems(foldersNames);
		mailsFolderLbl.setText("Inbox");
		
	}

	
	
	public void signOut (ActionEvent event) throws IOException {
		Parent root = FXMLLoader.load(getClass().getResource("signIn.fxml"));
		Scene scene = new Scene(root);
		scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
		Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();
		window.setScene(scene);
		window.show();
	}
	
	public void selectMailsFolder(MouseEvent event) {
		String selectedFolder = mailsFoldersView.getSelectionModel().getSelectedItem();
		currentFolder = app.setMailFolder(selectedFolder);
		currentFilter = null;
		currentSort = null;
		resetChoices();
		app.setViewingOptions(currentFolder, currentFilter , currentSort);
		currentPage = 1;
		pageLbl.setText(String.valueOf(currentPage));
		showEmails(currentPage);
		mailsFolderLbl.setText(selectedFolder);
	}
	
	public void showEmails(int page) {
		shownEmails = FXCollections.observableArrayList();
		emails = (EmailInfo[]) app.listEmails(page);
		EmailInfo currentEmail;
		if( emails != null) {
			for(int i=0 ; i<emails.length ; i++) {
				currentEmail = emails[i];
				shownEmails.add(new ObsEmailInfo(currentEmail.getSubject(),currentEmail.getSender(),currentEmail.getDate().toString()));
			}
		}
		emailsTable.setItems(shownEmails);
	}
	
	public void nextPage(ActionEvent event) {
		currentPage = Integer.parseInt(pageLbl.getText());
		if(  currentPage < app.getAllowedPages() ) {
			currentPage++;
			showEmails(currentPage);
			pageLbl.setText(String.valueOf(currentPage));
		}
	}
	
	public void prevPage(ActionEvent event) {
	    currentPage = Integer.parseInt(pageLbl.getText());
		if(  currentPage > 1 ) {
			currentPage--;
			showEmails(currentPage);
			pageLbl.setText(String.valueOf(currentPage));
		}
	}
	
	public void applySort(ActionEvent event) {
		currentSort = new Sort(sortBy.getSelectionModel().getSelectedItem());
		app.setViewingOptions(currentFolder, currentFilter, currentSort);
		showEmails(currentPage);
	}
	
	public void applySearch(ActionEvent event) {
		if( searchTxtField.getText() != "" && filterBy.getValue() != null && filterBy.getValue() != "Date" ) {
			currentFilter = new Filter(filterBy.getValue(),searchTxtField.getText());		
		}
		/*else if(datePicker.getValue() != null && filterBy.getValue() == "Date") {
	    	DateFormat Format = new SimpleDateFormat("yyyy-dd-MM");
			String date = Format.format(datePicker.getValue());
			currentFilter = new Filter(filterBy.getValue(),date); 
		}*/
		app.setViewingOptions(currentFolder, currentFilter, currentSort);
		showEmails(currentPage);
	}
	
	public void resetChoices() {
		sortBy.setValue(null);
		filterBy.setValue(null);
		datePicker.setValue(null);
		searchTxtField.setText("");
	}
	
	public void deleteEmails(ActionEvent event) {
		DLinkedList deletedEmails = getSelectedEmails();
		app.deleteEmails(deletedEmails);
		app.setViewingOptions(currentFolder, currentFilter, currentSort);
		showEmails(currentPage);
	}
	
	public DLinkedList getSelectedEmails() {
		ObsEmailInfo bean;
		DLinkedList selectedEmails = new DLinkedList();
		for(int i=0 ; i < shownEmails.size() ; i++) {
			bean = shownEmails.get(i);
			if(bean.getSelect().isSelected()) {
				System.out.println(i);
				selectedEmails.add(emails[i]);
			}
		}
		return selectedEmails;	
	}
	
	public void moveEmails(ActionEvent event) {
		if(moveTo.getSelectionModel().getSelectedItem()!=null) {
			DLinkedList movedEmails = getSelectedEmails();
			Folder des = app.setMailFolder(moveTo.getSelectionModel().getSelectedItem());
			app.moveEmails(movedEmails, des);
			app.setViewingOptions(currentFolder, currentFilter, currentSort);
			showEmails(currentPage);
			moveTo.setValue(null);
		}
	

	}
	
	public void compose(ActionEvent event) throws IOException {
		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(getClass().getResource("ComposeScene.fxml"));
		Parent root = loader.load();
		ComposeController composeController = loader.getController();
		composeController.initData(app,true,null);
		Scene scene = new Scene(root);
		scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
		Stage window = new Stage();
		window.setScene(scene);
		window.show();
	}
	
	public void showEmail(MouseEvent event) throws IOException {
		int i = emailsTable.getSelectionModel().getSelectedIndex();
		EmailInfo currentEmail = emails[i];
	    if(mailsFolderLbl.getText().equals("Draft")) {
	    	FXMLLoader loader = new FXMLLoader();
			loader.setLocation(getClass().getResource("ComposeScene.fxml"));
			Parent root = loader.load();
			ComposeController composeController = loader.getController();
			composeController.initData(app,false,currentEmail);
			Scene scene = new Scene(root);
			scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			Stage window = new Stage();
			window.setScene(scene);
			window.show();
	    }
	    else {
	    	FXMLLoader loader = new FXMLLoader();
			loader.setLocation(getClass().getResource("viewEmail.fxml"));
			Parent root = loader.load();
			viewEmailController controller = loader.getController();
			controller.initData(currentEmail);
			Scene scene = new Scene(root);
			scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			Stage window = new Stage();
			window.setScene(scene);
			window.show();
	    }
		
	}
	
	public void addFolder(ActionEvent event) {
		if(!folderNameTxtField.getText().equals("")) {

			if(app.addNewMailsFolder(folderNameTxtField.getText())) {
				foldersNames = FXCollections.observableArrayList();
				String[] folders = app.listMailsFolders();
				for (String folder : folders) {
					foldersNames.add(folder);
				} 
				mailsFoldersView.setItems(foldersNames);
				moveTo.setItems(foldersNames);
				errorLbl.setText("");
			}
			else {
				  errorLbl.setText("Folder exists");
			}
		}
		else {
			errorLbl.setText("empty field");
		}
		
		folderNameTxtField.setText("");
	}
	
	public void refresh(ActionEvent event) {
		app.setViewingOptions(currentFolder, currentFilter, currentSort);
		showEmails(currentPage);
		resetChoices();
	}
	

	



	
}
