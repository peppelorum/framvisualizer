package data;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

/**
 * The class Filemanger handles the saving and loading to/from XML
 * It is not called direct, but through the FramNodeList class
 * 
 * @author Jonas Haraldsson
 *
 */
public class Filemanager {


	/**
	 * Serialize an object to file
	 * 
	 * @param nodeList List to be saved
	 * @param filename 
	 */
	protected static void saveFile(Object obj, String filename){
		
		FileOutputStream fos;
		ObjectOutputStream oos;
		try {
			fos = new FileOutputStream(filename);
			oos = new ObjectOutputStream(fos);
			oos.writeObject(obj);
			oos.close();
			fos.close();
		} catch (FileNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		return;
	}

	/**
	 * Load a serialized object from file
	 * 
	 * @param filename 
	 * @return FrameNodeList
	 */
	protected static Object loadFile(String filename){
		Object obj;
		
		FileInputStream fis;
		try {
			fis = new FileInputStream(filename);
			ObjectInputStream ois = new ObjectInputStream(fis);

			obj = ois.readObject();

			ois.close();
			fis.close();
			
			return obj;
			
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return null;
	}
}


