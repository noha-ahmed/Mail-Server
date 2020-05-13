package application;

import java.io.File;
import java.io.IOException;

import MyDataStructures.DLinkedList;
import MyDataStructures.ILinkedList;
import MyDataStructures.SLinkedList;


public class App implements IApp{
    DLinkedList currentEmails ;
	private Contact currentUser;
	private File usersFile ;
	private File userContactsFile;
	private int failedCase;
	private Folder currentUserFolder;
    Folder currentMailFolder;
	private File currentIndexFile;
	private int allowedPages;
	private DLinkedList userContacts;
    DLinkedList filteredEmails;
	
    public App () {
    	usersFile = new File("System\\users.txt");
		File systemFile = new File("System");
		if( !systemFile.exists() ) {
			systemFile.mkdir();
			Folder.createFile(usersFile.getPath());
			SerialNumbers.createSerialFile("System\\emailSerial.txt");
		}
	}
    
	
	public Folder setMailFolder(String mailsFolder) {
		Folder folder = new Folder(currentUserFolder.getPath(),mailsFolder);
		return folder;
	}
	
	public int getFailedCase() {
		return this.failedCase;
	}
	
	public int getAllowedPages() {
		return this.allowedPages;
	}
	
	public Contact getCurrentUser() {
		return currentUser;
		
	}
	
	@Override
	public boolean signin(String email, String password) {
		if(email.equals("") || password.equals("") ) {
			failedCase = 1; //empty field(s)
			return false;
		}
		if(!Contact.checkEmailValidation(email)) {
			failedCase = 2; // invalid email
		}
		Contact contact = Contact.getContact(email,usersFile);
		if(contact==null) {
			failedCase = 3; //email doesn't exist
			return false;
		}
		if(contact.getPassword().equals(password)) {
			currentUser = contact;
			currentUserFolder = new Folder("System\\"+currentUser.getEmail());
			userContactsFile = new File(currentUserFolder.getPath()+"\\userContacts.txt");
			return true;
		}else {
			failedCase = 4; //wrong password
			return false;
		}	
	}

	@Override
	public boolean signup(IContact contact) {
		/*if(((Contact)contact).getEmail().equals("") || ((Contact)contact).getPassword().equals("") || ((Contact)contact).getFirstName().equals("") || ((Contact)contact).getLastName().equals("") || ((Contact)contact).getGender()==null || ((Contact)contact).getBirthDate() == null ) {
			failedCase = 1; //empty field(s)
			return false;
		}*/
		String email = ((Contact)contact).getEmail();
		boolean validEmail = Contact.checkEmailValidation(email);
		if(!validEmail) {
			failedCase = 2; // invalid email 
			return false;
		}
		boolean usedEmail = Contact.userExists(email);
		if(usedEmail) {
			failedCase = 3; //existing email
			return false;
		}
		Contact.saveContact((Contact) contact, usersFile);
		currentUser = (Contact) contact;
		currentUserFolder = new Folder("System\\"+currentUser.getEmail());
		currentUserFolder.createUserFolder();;
		return true;
	}

	@Override
	public void setViewingOptions(IFolder folder, IFilter filter, ISort sort) {
		if (sort == null) {
			sort = new Sort("Date");
		}
		
		currentMailFolder = (Folder)folder;
		currentIndexFile = new File(currentMailFolder.getPath()+"\\Index.txt");
		currentEmails = ObjectsIO.loadObjects(currentIndexFile);
		((Sort)sort).Sorting(currentEmails);
		if(filter!= null) {
			currentEmails=((Filter)filter).Search(currentEmails);
		}
		setAllowedPages();
		
	}

	@Override
	public IMail[] listEmails(int page) {
		int from = (page-1)*10;
		int to = from+10;
		if(currentEmails.size()==0) {
			return null;
		}
		if(to > currentEmails.size()) {
			to = currentEmails.size();
		}
		EmailInfo [] emails = new EmailInfo [to-from];
		for(int i = 0 ; i < emails.length ; i++) {
			emails[i] = (EmailInfo) currentEmails.get(from+i);
		}
		return emails;
	}

	@Override
	public void deleteEmails(ILinkedList mails) {
		Folder trashFolder = new Folder(currentUserFolder.getPath(),"Trash");
		moveEmails(mails,trashFolder);
	}

	@Override
	public void moveEmails(ILinkedList mails, IFolder des) {
		File desIndexFile = new File(((Folder)des).getPath()+"\\Index.txt");
		DLinkedList desEmails = ObjectsIO.loadObjects(desIndexFile);
		if(desEmails == null)
			desEmails = new DLinkedList();
		for(int i=0; i<mails.size(); i++) {
			EmailInfo currentEmail = (EmailInfo) mails.get(i);
			String desPath = ((Folder)des).getPath()+"\\"+currentEmail.getSerialNumber();
			System.out.println(currentEmail.getFile().getPath());
			Folder.moveFolder(currentEmail.getFile(), new File(desPath));
			currentEmail.setFile(desPath);
			desEmails.add(currentEmail);
		}
		ModifyIndexFile(mails);
		setAllowedPages();
		ObjectsIO.saveObjects(desEmails, desIndexFile);
	}

	
	public boolean compose(IMail email) {
		if(((Mail)email).recieversExist() && !((Mail)email).index.getReceiver().isEmpty()) {
			if(((Mail)email).isNewEmail()) {
				((Mail)email).createMail();
			}else {
				((Mail)email).sendFromDraft();
			}
			return true;
		}
		return false;
	}
	
	

	public void ModifyIndexFile(ILinkedList mails) {
		DLinkedList folderEmails = ObjectsIO.loadObjects(currentIndexFile);
		EmailInfo e1;
		EmailInfo e2;
		for(int i=0; i<mails.size(); i++) {
			e1=(EmailInfo)mails.get(i);
			
			for(int j=0; j<folderEmails.size();j++) {
				e2=((EmailInfo)folderEmails.get(j));
				if(e1.getSerialNumber().equals(e2.getSerialNumber())) {
					folderEmails.remove(j);
					break;
				}
			}
		}
		ObjectsIO.saveObjects(folderEmails, currentIndexFile);
	}
	
	public boolean addNewMailsFolder(String folderName) {
		Folder newMailsFolder = new Folder(currentUserFolder.getPath(),folderName);
		return newMailsFolder.createMailsFolder();
	}
	
	public boolean renameMailsFolder(Folder mailsFolder , String newName) {
		File source = new File(mailsFolder.getPath());
		File dest = new File(currentUserFolder.getPath()+"\\Mails\\"+newName);
		return Folder.renameFolder(source, dest);
	}
	
	public boolean deleteMailsFolder(Folder mailsFolder) {
		File folderToBeDeleted = new File((mailsFolder.getPath()));
		return Folder.deleteFolder(folderToBeDeleted);
	}
	
	public String[] listMailsFolders() {
		File file = new File(currentUserFolder.getPath()+"\\Mails");
		return file.list();
	}
	
	public void setAllowedPages() {
		if(currentEmails.size()%10==0) {
			allowedPages = currentEmails.size()%10;
		}
		else {
			allowedPages = (currentEmails.size()/10)+1;
		}
	}
	
	
	
}