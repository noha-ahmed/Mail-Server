package application;

import java.io.File;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import MyDataStructures.LinkedBasedQueue;
import MyDataStructures.SLinkedList;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.FileChooser;

public class ComposeController implements Initializable{
	
	@FXML
	private TextField Receivers;
	@FXML
	private TextField Subject;
	@FXML
	private TextArea Text;
	@FXML
	private Button addAttachment;
	@FXML
	private Button send;
	@FXML
	private ComboBox<String> priority;
	@FXML
	private Label status;
	@FXML
	private Label senderLbl;
	@FXML
	private Button addReceiver;
	@FXML
	private ListView<String> receiversList;
	@FXML
	private ListView<String> attachmentList;
	private ObservableList<String> receivers=FXCollections.observableArrayList();
	Mail mail = new Mail();
	boolean newEmail ;
	
	ObservableList<String> list = FXCollections.observableArrayList("Default","1","2","3","4");
	
	App app = new App();
	SLinkedList attachment = new SLinkedList();
	
	public void initData(App app , boolean isNewEmail , EmailInfo email) {
		this.newEmail = isNewEmail;
		mail.setNewEmail(isNewEmail);
		this.app = app;
		senderLbl.setText(app.getCurrentUser().getEmail());
		if(!newEmail) {
			Subject.setText(email.getSubject());
			senderLbl.setText(email.getSender());
			priority.setValue(String.valueOf(email.getPriority()));
			LinkedBasedQueue rec = email.getReceiver();
			int size = rec.size();
			String r;
			for(int i=0 ; i<size ; i++) {
				r = (String)rec.dequeue();
				receivers.add(r);
				rec.enqueue(r);
			}
			receiversList.setItems(receivers);
			String txtBody = Mail.loadEmailBody(email.getFile());
			Text.setText(txtBody);
		}
	}
	
	public void addReceivers(ActionEvent event) {
		if(!Receivers.getText().contentEquals(""))
		receiversList.getItems().add(Receivers.getText());
		Receivers.setText("");
	}
	
	public void addAttachment(ActionEvent event) {
		FileChooser attach = new FileChooser();
		List<File> selectedFiles = attach.showOpenMultipleDialog(null);
		if(selectedFiles!=null) {
			for(int i=0; i<selectedFiles.size(); i++) {
				Attachement node = new Attachement();
				node.setName(selectedFiles.get(i).getName());
				node.setPath(selectedFiles.get(i).getAbsolutePath());
				attachment.add(node);
				attachmentList.getItems().add(selectedFiles.get(i).getName());
			}
		}
	}
	
	public void Send(ActionEvent event) {
		
		mail.setSender(app.getCurrentUser().getEmail());
		ObservableList<String> receive = receiversList.getItems();
		for(int i=0; i<receive.size(); i++) {
			mail.setReciever(receive.get(i));
		}
		mail.setSubject(Subject.getText());
		if(priority.getValue()!=null && priority.getValue()!="Default") {
			mail.index.setPriority(Integer.parseInt(priority.getValue()));
		}
		mail.setEmailBody(Text.getText());
		if(!attachment.isEmpty()) {
			mail.index.setAttachement(attachment);
		}
		mail.setSend(true);
	//	button.setNewEmail(true);
		if(app.compose(mail)) {
			status.setText("Mail Sent Successfully");
		}else {
			status.setText("Receiver Doesn't Exist");
		}
	}
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		// TODO Auto-generated method stub
		priority.setItems(list);
	}
	
	public void saveToDraft(ActionEvent event) {
		mail.setSender(app.getCurrentUser().getEmail());
		ObservableList<String> receive = receiversList.getItems();
		for(int i=0; i<receive.size(); i++) {
			mail.setReciever(receive.get(i));
		}
		mail.setSubject(Subject.getText());
		if(priority.getValue()!=null && priority.getValue()!="Default") {
			mail.index.setPriority(Integer.parseInt(priority.getValue()));
		}
		mail.setEmailBody(Text.getText());
		if(!attachment.isEmpty()) {
			mail.index.setAttachement(attachment);
		}
		mail.setSend(false);
	//	button.setNewEmail(true);
		if(app.compose(mail)) {
			status.setText("Saved to Draft");
		}else {
			status.setText("Receiver Doesn't Exist");
		}
	}
}
