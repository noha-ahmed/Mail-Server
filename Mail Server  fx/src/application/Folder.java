package application;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

public class Folder implements IFolder{
	private String path;
	
	/**
	 * 
	 * constructor to create a mailsFolder
	 * @param userFolderPath
	 * @param mailsFolderName
	 */
	public Folder (String userFolderPath , String mailsFolderName) {
		path = userFolderPath + "\\Mails\\" + mailsFolderName;
	}
	
	/**
	 * constructor to create a userFolder
	 * @param userFolderPath
	 */
	
	public Folder(String userFolderPath) {
		path = userFolderPath;
	}
	
	public String getPath() {
		return this.path;
	}
	
	/**
	 * a method to create a new user folder containing 
	 * the basic mailsFolders
	 */
	public void createUserFolder() {
		createFolder(path+"\\Mails\\Inbox");
		createFolder(path+"\\Mails\\Trash");
		createFolder(path+"\\Mails\\Draft");
		createFolder(path+"\\Mails\\Sent");
		createFile(path+"\\userContacts.txt");
	}
	
	/**
	 * a method to create a new mailsFolder 
	 * created by the user
	 * @return true if the folder created successfully
	 * false if the name of the folder already exists
	 */
	public boolean createMailsFolder() {
		return createFolder(path);
	}
	
	
	public static boolean createFolder(String FolderPath) {
		File folder = new File(FolderPath);
		if(!folder.exists()) {
			folder.mkdirs();
			return true;
		}
		return false;
	}
	
	public static void createOwnFolder(String Email, String FolderName) {
		createFolder("System\\"+Email+"\\Mails\\"+FolderName);
	}
	
	public static void createFile(String filePath) {
		File file = new File(filePath);
		if(!file.exists()) {
			try {
				file.createNewFile();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	public static void copyFolder(File sourceFolder, File destinationFolder){
		try {
	        if (sourceFolder.isDirectory()) 
	        {
	            if (!destinationFolder.exists()) 
	            {
	                destinationFolder.mkdir();
	            }
	            String files[] = sourceFolder.list();

	            for (String file : files) 
	            {
	                File srcFile = new File(sourceFolder, file);
	                File destFile = new File(destinationFolder, file);
	                copyFolder(srcFile, destFile);
	            }
	        }
	        else
	        {
	            Files.copy(sourceFolder.toPath(), destinationFolder.toPath(), StandardCopyOption.REPLACE_EXISTING);
	        }
		}catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static void moveFolder(File source, File dest) {
		try {
			Files.move(source.toPath(), dest.toPath());	
		}catch(IOException e) {
			e.printStackTrace();
		}
	}
	
	public static boolean renameFolder(File source, File dest) {
		return source.renameTo(dest);
	}
	
	public static boolean deleteFolder(File folderToBeDeleted) {
		File[] contents = folderToBeDeleted.listFiles();
		if(contents!=null) {
			for( File file: contents) {
				deleteFolder(file);
			}
		}
		return folderToBeDeleted.delete();
	}
	
	
	
}
