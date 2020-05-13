package application;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.Serializable;
import java.nio.file.Files;
import java.nio.file.Paths;

import MyDataStructures.DLinkedList;
import MyDataStructures.LinkedBasedQueue;


import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.Serializable;
import MyDataStructures.LinkedBasedQueue;

public class Mail implements IMail, Serializable{
	private static final long serialVersionUID = 1L;
	private String emailBody;
	EmailInfo index;
	private boolean send;
	private boolean newEmail;
	
	public void setNewEmail(boolean newEmail) {
		this.newEmail = newEmail;
	}
	
	/**
	 * true if the mail is new
	 * @return true or false
	 */
	public boolean isNewEmail() {
		return this.newEmail;
	}
	
	public Mail(){
		index = new EmailInfo();
	}
	
	public void setSerialNumber() {
		String path = "System\\emailSerial.txt";
		index.setSerialNumber(SerialNumbers.generateSerialNumber(path));
	}
	
	public void setEmailBody(String emailBody) {
		this.emailBody = emailBody;
	}
	
	public String getEmailBody() {
		return this.emailBody;
	}
	
	public void setSend (boolean send) {
		this.send = send;
	}
	
	public void setSender(String sender) {
		index.setSender(sender);
	}
	
	public void setReciever(String receiver) {
		index.setReceiver(receiver);
	}
	
	public void setSubject(String subject) {
		index.setSubject(subject);
	}
	
	public void sendFromDraft() {
		if(send) {
			index.setDate();
			saveEmailBody();
			Folder.moveFolder(index.getFile(), new File("System\\"+index.getSender()+"\\Mails\\Sent\\"+index.getSerialNumber()));
			sendToReceivers();
			index.setFile("System\\"+index.getSender()+"\\Mails\\Sent\\"+index.getSerialNumber());
			/*if(!index.getAttachement().isEmpty()) {
				for(int i=0; i<index.getAttachement().size(); i++) {
					if(!new File(((Attachement)index.getAttachement().get(i)).getPath()).exists()){
						String des = index.getFile()+"\\Attachement\\"+((Attachement)index.getAttachement().get(i)).getName();
						((Attachement)index.getAttachement().get(i)).setPath(des);
					}
				}
			}*/
			saveIndex("System\\"+index.getSender()+"\\Mails\\Sent\\",index);
			DLinkedList draft = ObjectsIO.loadObjects(new File("System\\"+index.getSender()+"\\Mails\\Draft\\Index.txt"));
			for(int i=0;i<draft.size();i++) {
				String temp = ((EmailInfo)draft.get(i)).getSerialNumber();
				if(temp.equals(index.getSerialNumber())) {
					draft.remove(i);
					break;
				}
			}
		}else {
			saveEmailBody();
			DLinkedList draft = ObjectsIO.loadObjects(new File("System\\"+index.getSender()+"\\Mails\\Draft\\Index.txt"));
			for(int i=0;i<draft.size();i++) {
				String temp = ((EmailInfo)draft.get(i)).getSerialNumber();
				if(temp.equals(index.getSerialNumber())) {
					draft.set(i, index);
					break;
				}
			}
			/*if(!index.getAttachement().isEmpty()) {
			for(int i=0; i<index.getAttachement().size(); i++) {
				if(!new File(((Attachement)index.getAttachement().get(i)).getPath()).exists()){
					String des = index.getFile()+"\\Attachement\\"+((Attachement)index.getAttachement().get(i)).getName();
					((Attachement)index.getAttachement().get(i)).setPath(des);
				}
			}
			}*/
			saveIndex(index.getFile().toPath().toString(),index);
		}
	}
	
	public void createMail() {
		setSerialNumber();
		index.setDate();
		if(index.getSubject()== null) {
			index.setSubject("No Subject");
		}
		
		String indexPath ="System\\"+index.getSender()+"\\Mails\\" ;
		String emailPath;
		
		if(send) {
			indexPath += "Sent\\";
			emailPath = indexPath + index.getSerialNumber();
			Folder.createFolder(emailPath);
			Folder.createFolder(emailPath+"\\Attachement");
			setAttachementsInMail(emailPath);
			index.setFile(emailPath);
			saveIndex(indexPath, index);
			saveEmailBody();
			sendToReceivers();
		}else {
			indexPath += "Draft\\";
			emailPath = indexPath + index.getSerialNumber();
			Folder.createFolder(emailPath);
			Folder.createFolder(emailPath+"\\Attachement");
			setAttachementsInMail(emailPath);
			index.setFile(emailPath);
			saveIndex(indexPath,index);
			saveEmailBody();
		}
	}
	
	private void setAttachementsInMail(String emailPath) {
		if(!index.getAttachement().isEmpty()) {
		for(int i=0; i<index.getAttachement().size(); i++) {
			String des = emailPath+"\\Attachement\\"+((Attachement)index.getAttachement().get(i)).getName();
			Folder.copyFolder(new File(((Attachement)index.getAttachement().get(i)).getPath()), new File(des));
			((Attachement)index.getAttachement().get(i)).setPath(des);
		}
	}
	}
	
	private void saveEmailBody() {
		String emailBodyPath = index.getFile().toPath()+"\\emailBody.txt";
		File emailBodyFile = new File(emailBodyPath);
		try {
			FileOutputStream fos = new FileOutputStream(emailBodyFile);
			byte [] arr = emailBody.getBytes();
			fos.write(arr);
			fos.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	private void saveIndex(String path, EmailInfo index) {
		ObjectsIO.saveObject(index, new File(path+"Index.txt"));
	}
	
	private void sendToReceivers() {
		LinkedBasedQueue receivers = index.getReceiver();
		 while( !index.getReceiver().isEmpty()) {
			 String receiver = (String) receivers.dequeue();
			 String pathReceiver = "System\\"+receiver+"\\Mails\\Inbox\\";
			 Folder.createFolder(pathReceiver+index.getSerialNumber());
			 EmailInfo indexReceiver = new EmailInfo();
			 indexReceiver.setSender(index.getSender());
			 indexReceiver.setSubject(index.getSubject());
			 indexReceiver.setSerialNumber(index.getSerialNumber());
			 indexReceiver.setDate(index.getDate());
			 indexReceiver.setReceiver(receiver);
			 indexReceiver.setFile(pathReceiver+index.getSerialNumber());
			 Folder.copyFolder(index.getFile(), new File(indexReceiver.getFile().toString()));
			 saveIndex(pathReceiver,indexReceiver);
		 }
	}
	
	public boolean recieversExist() {
		boolean exist= true;
		LinkedBasedQueue recievers = new LinkedBasedQueue();
		String recieverEmail ;
		 while(!index.getReceiver().isEmpty()){
			recieverEmail = (String) index.getReceiver().dequeue();
			if(!Contact.userExists(recieverEmail)) {
				exist = false;
				break;
			}
			recievers.enqueue(recieverEmail);			
		}
		while(!recievers.isEmpty()) {
			index.setReceiver((String)recievers.dequeue());
		}
		return exist;
	}
	
	public static String loadEmailBody(File file) {
		String emailBody = "";	
		String path = file.getPath()+"\\emailBody.txt";
		try {
			emailBody = new String(Files.readAllBytes(Paths.get(path)));
		} catch (IOException e) {
			e.printStackTrace();
		}
		return emailBody;
	}
	
}