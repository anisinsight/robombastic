package fr.dauphine.robombastic.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public final class FileUtils {
	private FileUtils(){
	}
	
	public static String readTextFromFile(String fileName){
		return readTextFromFile(new File(fileName));
	}
	
	public static String readTextFromFile(File file){
		String content = "";
		
		try {
			BufferedReader reader = new BufferedReader(new FileReader(file));
			while (true) {
				String line = reader.readLine();
				if (line == null)
					break;

				if (!content.equals(""))
					content += "\n";
				content += line;
			}
			reader.close();
		} catch (Exception e) {
			content = null;
			e.printStackTrace();
		}
		
		return content;
	}
	
	public static File createFileFromText(String fileName, String text) {
		return createFileFromText(new File(fileName), text);
	}
	
	public static File createFileFromText(File file, String text) {
		try {
			file.createNewFile();
			FileWriter writer = new FileWriter(file);
			writer.write(text);
			writer.close();
		} catch (Exception e) {
			e.printStackTrace();
		}

		return file;
	}
	
	public static void saveObjectInFile(Object object, File file){
		try{
			ObjectOutputStream writer = new ObjectOutputStream(new FileOutputStream(file));
			writer.writeObject(object);
			writer.flush();
			writer.close();
		}
		catch (Exception e){
			e.printStackTrace();
		}
	}
	
	public static Object readObjectInFile(File file){
		try{
			ObjectInputStream reader = new ObjectInputStream(new FileInputStream(file));
			Object object = reader.readObject();
			reader.close();
			
			return object;
		}
		catch (Exception e){
			e.printStackTrace();
		}
		
		return null;
	}
}
