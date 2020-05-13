package application;

import java.io.File;
import java.io.Serializable;
import java.util.Date;


import MyDataStructures.LinkedBasedQueue;
import MyDataStructures.SLinkedList;
import javafx.scene.shape.Path;
public class EmailInfo implements Serializable , IMail{
	private static final long serialVersionUID = 1L;
	private String sender;
	private LinkedBasedQueue receiver = new LinkedBasedQueue();
	private String subject;
	private Date date;
	private File file;
	private String serialNumber;
	private int priority=5;
	private SLinkedList attachements = new SLinkedList();
	
	public void setAttachement(SLinkedList attaches) {
		this.attachements = attaches;
	}
	
	public SLinkedList getAttachement() {
		return this.attachements;
	}
	
	public void setPriority(int prior) {
		this.priority = prior;
	}
	
	public int getPriority() {
		return this.priority;
	}
	
	public void setSender(String sender) {
		this.sender = sender;
	}
	
	public String getSender () {
		return this.sender;
	}
	
	public void setReceiver(String receiver) {
		this.receiver.enqueue(receiver);
	}
	
	public LinkedBasedQueue getReceiver () {
		return this.receiver;
	}
	
	public void setSubject(String subject) {
		this.subject = subject;
	}
	
	public String getSubject () {
		return this.subject;
	}
	
	public void setDate() {
		this.date = new Date();
	}
	
	public void setDate(Date date) {
		this.date = date;
	}
	
	public Date getDate() {
		return date;
	}
	
	public void setFile(String path) {
		this.file = new File(path);
	}
	
	public File getFile() {
		return file;
	}
	
	public void setSerialNumber(String serialNumber) {
		this.serialNumber = serialNumber;
	}
	
	public String getSerialNumber() {
		return this.serialNumber;
	}
}