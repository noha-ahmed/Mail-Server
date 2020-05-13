package application;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import MyDataStructures.DLinkedList;

public class Main {

	public static void main(String[] args) throws IOException {
		
		
		App app = new App();
		
		Contact user1 = new Contact();
		user1.setEmail("noha@yahoo.com");
		user1.setPassword("1111");
		
		Contact user2 = new Contact();
		user2.setEmail("menna@yahoo.com");
		user2.setPassword("2222");
		
		Contact user3 = new Contact();
		user3.setEmail("mariem@yahoo.com");
		user3.setPassword("3333");
		
		Contact user4 = new Contact();
		user4.setEmail("yasmine@yahoo.com");
		user4.setPassword("4444");
		
		Contact user5 = new Contact();
		user5.setEmail("farah@yahoo.com");
		user5.setPassword("5555");
		
	/*	System.out.println(app.signup(user1));
		System.out.println(app.signup(user2));
		System.out.println(app.signup(user3));
		System.out.println(app.signup(user4));
	    System.out.println(app.signup(user5)); */
		System.out.println(app.signin(user1.getEmail(), user1.getPassword()));
		
		String [] s = app.listMailsFolders();
		
		for( String s1 : s) {
			System.out.println(s1);
		}
		
		Mail email1 = new Mail();
		email1.setEmailBody("email 5");
		email1.setSender(user2.getEmail());
		email1.setReciever(user2.getEmail());
		email1.setReciever(user3.getEmail());
		email1.setSubject("css");
	//	email1.index.setReceiver("farah@yahoo.com");
		email1.setSend(true);
	//	System.out.println(app.compose(email1));
		
		Mail email2 = new Mail();
		email2.setEmailBody("email 6");
		email2.setSender(user5.getEmail());
		email2.setReciever(user5.getEmail());
		email2.setReciever(user3.getEmail());
		email2.setSubject("maa");
		email2.setSend(true);
	//	System.out.println(app.compose(email2));
		
		Sort sort = new Sort("Date");
		Filter filter = new Filter("Subject","Maa");
		app.setViewingOptions(app.setMailFolder("Sent"),filter,sort);
		DLinkedList emails = app.currentEmails;
	//	((Sort)sort).iterativeQsort(emails);
	//	DLinkedList filteredEmails = filter.Search(emails);
	//	emails = filteredEmails;
		EmailInfo index;
		String emailBody;
		for(int i=0 ; i<emails.size();i++) {
			index = (EmailInfo) emails.get(i);
			System.out.println(index.getSerialNumber());
			emailBody = Mail.loadEmailBody(index.getFile());
			System.out.println(emailBody);
			System.out.println(index.getSubject());
			System.out.println(index.getSender());
			System.out.println(index.getDate()+"\n");
		}
		
		
	/*	app.signup(user1);
		app.signup(user2);
		app.signup(user3);
		app.signup(user4);  */
		
	/*	app.signin(user3.getEmail(), user3.getPassword());
		Folder folder = app.setMailFolder("Sent");
		app.setViewingOptions(folder, null, null);
		System.out.println(app.currentMailFolder.getPath());
		System.out.println(app.getAllowedPages());
	/*	EmailInfo index;
		String emailBody;
		IMail[] emailsArr = app.listEmails(3);
		EmailInfo index1 ;
		for(int i = 0 ; i<emailsArr.length ; i++) {
			index1 = (EmailInfo) emailsArr[i];
			System.out.println(index1.getSerialNumber());
			emailBody = Mail.loadEmailBody(index1.getFile());
			System.out.println(emailBody);		
		}
	
		
	/*	DLinkedList emails = app.currentEmails;
		for(int i=0 ; i< app.currentEmails.size();i++) {
			index = (EmailInfo) emails.get(i);
			System.out.println(index.getSerialNumber());
			emailBody = Mail.loadEmailBody(index.getFile());
			System.out.println(emailBody);
		}
		
		DLinkedList subEmails = new DLinkedList();
		subEmails.add(emails.get(0));
		subEmails.add(emails.get(1));
		subEmails.add(emails.get(2));
		app.deleteEmails(subEmails);
		for(int i=0 ; i< app.currentEmails.size();i++) {
			index = (EmailInfo) emails.get(i);
			System.out.println(index.getSerialNumber());
			emailBody = Mail.loadEmailBody(index.getFile());
			System.out.println(emailBody);
		} */
		
	//	System.out.println(app.addNewMailsFolder("noha"));
	//	Folder folder2 = app.setMailFolder("Inbox");
	//	System.out.println(app.deleteMailsFolder(folder2)); 
		
		
	/*	File file = new File("System\\noha@yahoo.com\\Mails\\Draft");
		boolean t = file.renameTo(new File("System\\\\noha@yahoo.com\\\\Mails\\Inbox"));
		app.signin("noha@yahoo.com", "1234");
		System.out.println(t);
		Folder folder = new Folder("System\\noha@yahoo.com","yaraab");
		System.out.println(folder.getPath()); 
		

		
		Folder folder1 = app.setMailFolder("Inbox");
		System.out.println(folder1.getPath());
		app.setViewingOptions(folder1, null, null);
		

		
		  */

	}

}
