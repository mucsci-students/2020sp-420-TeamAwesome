// Package name
package console;

// System imports
import java.util.Scanner;

//Local imports

/**
 * Console input handler for UML Editor
 * @author Ryan
 *
 */
public class UMLConsole {
	// Create scanner for reading in console input
	private Scanner scanner;
	
	/**
	 * Construct a UMLConsole
	 */
	public UMLConsole() {
		// Initialize scanner to read System.in InputStream
		scanner = new Scanner(System.in);
	}
	
	/**
	 * Open scanner input stream to allow user to enter text
	 * 
	 * @return String that user entered
	 */
	public String getConsoleCommand() {
		// Preface to indicate waiting for input
		System.out.print("editor> ");
		// Get input from user
		String str = scanner.nextLine();
		// Return user-entered string
		return str;
	}
	
	/**
	 * 
	 * @return The scanner with an InputStream for System.in
	 */
	public Scanner getScanner() {
		return scanner;
	}
	
	/**
	 * Close the scanner
	 */
	public void closeScanner() {
		scanner.close();
	}
}
