package application;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import MyDataStructures.DLinkedList;

public class ObjectsIO {
	public static void saveObject ( Object object , File file) {
		FileOutputStream fos = null;
		ObjectOutputStream oos = null;
		DLinkedList objects = null;
		objects = loadObjects(file);
		objects.add(object);
		try {
			fos = new FileOutputStream(file);
			oos = new ObjectOutputStream(fos); 
			oos.writeObject(objects);
			fos.close();
			oos.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static DLinkedList loadObjects(File file) {
		FileInputStream fis = null;
		ObjectInputStream ois = null;
		DLinkedList objects = new DLinkedList();
		if(file.length() == 0) {
			return objects;
		}
		try {
			fis = new FileInputStream(file);
			ois = new ObjectInputStream(fis);
			objects = (DLinkedList) ois.readObject(); 
			fis.close();
			ois.close();
			
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		return objects;
	}
	
	public static void saveObjects(DLinkedList objects, File file) {
		try {
			FileOutputStream fos = new FileOutputStream(file);
			ObjectOutputStream oos = new ObjectOutputStream(fos); 
			oos.writeObject(objects);
			fos.close();
			oos.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
