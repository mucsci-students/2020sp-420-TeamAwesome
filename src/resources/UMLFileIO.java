package resources;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Scanner;

/**
 * @author Ryan Mass
 * @author Benjamin
 */
public class UMLFileIO {
	
	private File saveFile;
	
	public UMLFileIO() {}
	 /**  
     * 
     */
	public int setFile (String filePath)  {
		saveFile = new File (filePath); // creates a reference
		if(saveFile.exists() == false){
			try {
				saveFile.createNewFile();
			} catch (IOException e) {
				return 301;
			}
		}	
		return 0;
	}
	 
	/**
	 * Writes text to file  
     * @param text text to write to file
     */
	public int writeToFile (String text)  {
		FileWriter writer;
		try {
			writer = new FileWriter(saveFile);
		} catch (IOException e) {
			return 302;
		}
		PrintWriter printer = new PrintWriter(writer);
		printer.print(text); 
		printer.close(); // Force close
		return 0;
	}
	 /**  
     *@return - an Object array of the format [readMessage, return code]
     */
	public Object[] readFile()  {
		Scanner sc;
		try {
			sc = new Scanner(saveFile);
		} catch (FileNotFoundException e) {
			return new Object[]{"", 303};
		}
		String result = "";
		while (sc.hasNextLine()) {
			result += sc.nextLine();
		}
		sc.close();
		return new Object[]{result, 0};

	}
	
	/**
	 * Check if the file to save to has been set
	 * @return - True if the file is set
	 */
	public boolean fileSet() {
		return saveFile != null;
	}
	
	/**
	 * Make sure set file exists
	 * @return - True if file exists
	 */
	public boolean fileExists() {
		return saveFile.exists();
	}
	
	/**
	 * @return saved File  
	 */
	public File getFile() {
		return saveFile; 
	}
}
