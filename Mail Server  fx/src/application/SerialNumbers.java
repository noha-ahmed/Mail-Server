package application;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

public class SerialNumbers {
	public static void createSerialFile(String path) {
		Folder.createFile(path);
		updateSerialFile(path,0);
	}
	
	public static void updateSerialFile(String path , int serialNum) {
		File serialFile = new File(path);
		try {
			FileOutputStream fos = new FileOutputStream(serialFile);
			fos.write(serialNum);
			fos.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static String generateSerialNumber(String path) {
		int serialNumber = 0;
		try {
			FileInputStream fis = new FileInputStream(new File(path));
			serialNumber = fis.read();
			fis.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		updateSerialFile(path,serialNumber+1);
		return Integer.toString(serialNumber);	
	}

}
