package resources;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Scanner;

public class UMLFileIO {
	
	private File saveFile;
	
	public UMLFileIO() {}
	
	public void setFile (String filePath) throws IOException {
		saveFile = new File (filePath); // creates a reference
		if(saveFile.exists() == false){
			saveFile.createNewFile();
		}
		
	}
	public void writeToFile (String text) throws IOException {
		FileWriter writer = new FileWriter(saveFile);
		PrintWriter printer = new PrintWriter(writer);
		printer.print(text); 
		printer.close(); // lets the operator know you are done with it. 
	}
	
	public String readFile() throws FileNotFoundException  {
		Scanner sc = new Scanner(saveFile);
		String result = "";
		while (sc.hasNextLine()) {
			result += sc.nextLine();
		}
		sc.close();
		return result;

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
}
