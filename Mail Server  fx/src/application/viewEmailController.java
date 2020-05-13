package application;

import java.awt.Desktop;
import java.io.File;
import java.io.IOException;

import MyDataStructures.DLinkedList;
import MyDataStructures.LinkedBasedQueue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.input.MouseEvent;

public class viewEmailController {
	
	@FXML
	private Label subjectLbl;
	@FXML
	private Label senderLbl;
	@FXML
	private Label dateLbl;
	@FXML
	private Label priorityLbl;
	@FXML
	private Label textBodyLbl;
	@FXML
	private ListView<String> receiversListView;
	@FXML
	private ListView<String> attachmentList;
	
	private ObservableList<String> receivers=FXCollections.observableArrayList();
	
	
	private EmailInfo email;
	
	public void initData(EmailInfo email) {
		this.email = email;
		subjectLbl.setText(email.getSubject());
		senderLbl.setText(email.getSender());
		priorityLbl.setText(String.valueOf(email.getPriority()));
		LinkedBasedQueue rec = email.getReceiver();
		int size = rec.size();
		String r;
		for(int i=0 ; i<size ; i++) {
			r = (String)rec.dequeue();
			receivers.add(r);
			rec.enqueue(r);
		}
		for(int i=0; i<email.getAttachement().size(); i++) {
			attachmentList.getItems().add(((Attachement)email.getAttachement().get(i)).getName());
		}
		receiversListView.setItems(receivers);
		String txtBody = Mail.loadEmailBody(email.getFile());
		textBodyLbl.setText(txtBody);
	}

	public void selectattach(MouseEvent event) throws IOException {
		int i = attachmentList.getSelectionModel().getSelectedIndex();
		Desktop at = Desktop.getDesktop();
		at.open(new File(((Attachement)email.getAttachement().get(i)).getPath()));
	}
}
