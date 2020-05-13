package application;

import java.io.File;
import java.io.Serializable;

import MyDataStructures.DLinkedList;
import MyDataStructures.SLinkedList;


import java.io.File;
import java.io.Serializable;
import MyDataStructures.SLinkedList;

public class Contact implements IContact , Serializable{
	private static final long serialVersionUID = 1L;
	private String firstName;
	private String lastName;
	private String email;
	private String password;
	private String gender;
	private String birthDate;
	
	public void setBirthdate(String birthDate) {
		this.birthDate = birthDate;
	}
	
	public String getBirthDate() {
		return this.birthDate;
	}
	
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	
	public String getFirstName () {
		return this.firstName;
	}
	
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	
	public String getLastName() {
		return this.lastName;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}
	
	public String getGender() {
		return this.gender;
	}
	
	public void setEmail(String email) {
		this.email = email;
	}
	
	public String getEmail () {
		return this.email;
	}
	
	public void setPassword(String password) {
		this.password = password;
	}
	
	public String getPassword() {
		return this.password;
	}
	
	
	public static Contact getContact(String email , File contactsFile) {
		Contact contact = null;
		Contact currentContact;
		DLinkedList contacts = ObjectsIO.loadObjects(contactsFile);
		for(int i = 0 ; i<contacts.size() ; i++) {
			currentContact = (Contact) contacts.get(i);
			if( currentContact.email.equals(email)) {
				contact = currentContact;
				break;
			}
		}
		return contact;
	}
	
	public static void saveContact(Contact user, File contactsFile ) {
		 ObjectsIO.saveObject(user, contactsFile);
	}
	
	public static boolean contactExists(Contact contact, File contactsFile) {
		return getContact(contact.getEmail(),contactsFile) != null;
	}
	
	public static boolean userExists(String email) {
		return getContact(email,new File("System\\users.txt"))!=null;
	}
	
	
	public static boolean checkEmailValidation(String email) {
		String regex = "^[A-Za-z0-9_.-]+@yahoo.com$";
		if(email.matches(regex)) {
			return true;
		}
		return false;
	}
}