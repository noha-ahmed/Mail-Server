package application;

import java.io.File;

import MyDataStructures.DLinkedList;

public class UserContact {
	private String name;
	private DLinkedList emails ;
	
	public UserContact(){
		emails = new DLinkedList();
	}
	
	public void setName (String name) {
		this.name = name;
	}
	
	public String getName () {
		return this.name;
	}
	
	public void addEmail( String email ) {
		this.emails.add(email);
	}
	
	public DLinkedList getEmails () {
		return this.emails;
	}
	
	public static DLinkedList loadContacts(File userContactsFile) {
		DLinkedList userContacts = ObjectsIO.loadObjects(userContactsFile);
		return userContacts;	
	}
	
	public  void addContact(File userContactsFile){
		ObjectsIO.saveObject(this,userContactsFile);
	}
	
	public void deleteEmail(String email) {
		for (int i=0 ; i<emails.size() ; i++) {
			if(emails.get(i).equals(email)) {
				emails.remove(i);
				break;
			}
		}
	}

}
