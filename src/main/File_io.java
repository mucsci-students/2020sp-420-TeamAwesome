package main;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Scanner;

public class File_io {
	
	private File saveFile;
	
	public File_io() {}
	
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
	

}
