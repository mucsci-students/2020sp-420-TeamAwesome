// Package name
package main;

import java.io.FileNotFoundException;
import java.io.IOException;
// System imports
import java.util.Enumeration;
import java.util.Hashtable;

// Local imports
import console.UMLConsole;
import resources.UMLFileIO;
import resources.UMLClassManager;

/**
 *  Main environment for UML Editor
 * @author Ryan
 *
 */
public class UMLEditor {
	// UML Console
	private static UMLConsole console;
	
	// All valid commands
	private Hashtable<String, String[]> validCommands;
	
	// Class manipulation handler
	private UMLClassManager classManager;
	
	// Saving and loading manager
	private UMLFileIO fileIO;
	
	/**
	 * Constructor for the UML Editor where the console
	 * and GUI will be initialized and run
	 */
	public UMLEditor() {
		// Initialize variables
		console = new UMLConsole();
		validCommands = new Hashtable<String, String[]>();
		classManager = new UMLClassManager();
		fileIO = new UMLFileIO();
		
		// Fill the list of valid commands with names and descriptions
		populateValidCommands();
	}
	
	/**
	 * Actually begin reading from the console.
	 * Separate from initialization so we can control when the console is started and
	 * allows for testing of commands.
	 */
	public void beginConsole() {
		// Continuously get console input until quit statement has been reached
		while(true) {
			// Get the input from console
			String input = console.getConsoleCommand();
			
			// Check if command is empty or only whitespace
			if(!input.isEmpty() && !input.replaceAll(" ", "").isEmpty()) {
				if(!execCommand(input))
					System.err.println("Failed to execute command.");
			}
		}
	}
	
	/**
	 * Execute the given command and report errors as necessary
	 * 
	 * @return boolean indicating if command was successfully executed
	 */
	public boolean execCommand(String command) {
		// Split command on white space
		// args[0] = name of the command
		// args[1...] = any arguments for the command
		String[] args = command.split(" ");
		
		// Make sure list of args is not empty and a command exists
		if(args.length == 0 || args[0].trim().isEmpty()) {
			return false;
		}
		
		// Check command, if it is a valid command then perform associated action
		//	Otherwise return false
		if(args[0].equals("exit") || args[0].equals("quit")) {
			System.out.println("Force quitting...");
			cleanup();
			System.out.println("Goodbye :)");
			System.exit(0);
		}
		else if(args[0].equals("add-class")) {
			// Make sure there is an argument for the type and class name
			if(args.length > 1) {
				// Pull args
				String className = args[1];
				
				if(classManager.addClass(className))
					System.out.println("Added class \'" + className + "\'.");
				else
					System.err.println("Error adding class.");
			}
			else {
				System.err.println("Did not get expected number of arguments.");
			}
		}
		else if(args[0].equals("remove-class")) {
			// Make sure there is an argument for the class name
			if(args.length > 1) {
				String className = args[1];
				
				if(classManager.removeClass(className))
					System.out.println("Removed class \'" + className + "\'.");
				else
					System.err.println("Error removing class.");
			} 
			else {
				System.err.println("Did not get expected number of arguments.");
			}
		}
		else if(args[0].equals("save")) {
			// Check if there is a save file. If not prompt for one
			if(!fileIO.fileSet()) {
				System.err.println("Save file not set.");
				System.err.flush();
				System.out.flush();
				System.out.print("Save file: ");
				String filePath = console.getScanner().nextLine();
				
				// Ensure file extension
				if(!filePath.endsWith(".json")) {
					filePath += ".json";
				}
				
				// Set fileIO path
				try {
					fileIO.setFile(filePath);
				} catch (IOException e) {
					// Inform user of error and return true because the command worked, the file saving didn't.
					System.err.println("Error setting file location. Please try again.");
					return true;
				}
			}
			
			// Write JSON to file
			try {
				fileIO.writeToFile(classManager.convertToJSON());
			} catch (IOException e) {
				System.err.println("Could not save file. Please try again.");
			}
		}
		else if(args[0].equals("load")) {
			// Expects args[1] to be the path to the file to load.
			// Make sure there is enough arguments
			if(args.length > 1) {
				// Grab file path
				String filePath = args[1];
				
				// Set the file for FileIO
				try {
					fileIO.setFile(filePath);
				} catch (IOException e) {
					System.err.println("Could not set the file location. Please try again.");
					return true;
				}
				
				// Make sure the file exists
				if(!fileIO.fileExists()) {
					System.err.println("File does not exists.");
					return true;
				}
				
				// Read the file in and pass it to the classManager for parsing.
				try {
					classManager.parseJSON(fileIO.readFile());
				} catch (FileNotFoundException e) {
					System.err.println("Error parsing JSON.");
					return true;
				}
			}
			else {
				System.err.println("Did not get expected number of arguments.");
			}
		}
		else if(args[0].equals("list-classes")) {
			System.out.println("Classes: " + classManager.listClasses());
		}
		else if(args[0].equals("help")) {
			printHelp();
		}
		else {
			System.err.println("Command \'" + command + "\' was not recognized.");
		}
		
		System.out.flush();
		System.err.flush();
		
		return true;
	}
	
	/**
	 * Print out the a list of commands with descriptions
	 */
	private void printHelp() {
		System.out.println();
		System.out.println("VALID COMMANDS");
		System.out.println("---------------------------------");
		
		// For every key in dictionary print corresponding description
		for(Enumeration<String> keys = validCommands.keys(); keys.hasMoreElements();) {
			// Get the name of the command
			String commName = (String)keys.nextElement();
			// Get the list of description lines
			String[] desc = validCommands.get(commName);
			// Print first line with command name
			System.out.printf("%-25s: %-40s\n", commName, desc[0]);
			// Print rest of the command descriptions
			for(int i = 1; i < desc.length; i++) {
				System.out.printf("%-25s  %-40s\n", "", desc[i]);
			}
		}
		System.out.println();
	}
	
	/**
	 * Perform any cleanup operations, like closing files, input readers, streams, etc.
	 */
	private void cleanup() {
		console.closeScanner();
	}
	
	/**
	 * Fills validCommands with the command name and a description
	 * Format is as follows
	 * 		validCommands.put(name, list_of_description_lines);
	 * 		name = the name of the command
	 * 		list_description_lines = an array of strings where each item is a new line to print out as a description
	 */
	private void populateValidCommands() {
		validCommands.put("help", new String[]{"Prints out a list of valid commands with descriptions"});
		validCommands.put("add-class <class_name>", new String[]{"add the given class name"});
		validCommands.put("remove-class <class_name>", new String[]{"add the given class name"});
		validCommands.put("exit", new String[]{"quit the program"});
		validCommands.put("quit", new String[]{"quit the program"});
		validCommands.put("save", new String[]{"save the current state of the UML diagram", "if a file has not been set it will prompt the user."});
		validCommands.put("load <file_path>", new String[] {"Load the given file into the UML editor"});
		validCommands.put("list-classes", new String[]{"list all of the created classes"});
	}
	
	/**
	 * Get the list of valid commands
	 * @return validCommands
	 */
	public String[] getValidCommands() {
		String[] commands = new String[validCommands.size()];
		
		// Convert Dictionary keys to array
		int count = 0;
		for(Enumeration<String> keys = validCommands.keys(); keys.hasMoreElements();) {
			commands[count++] = keys.nextElement();
		}
		
		return commands;
	}
	
	/**
	 * Get the UMLConsole instance
	 * @return console
	 */
	public UMLConsole getConsole() {
		return console;
	}
	
	public static void main(String[] args) {
		// Create editor instance
		UMLEditor editor = new UMLEditor();
		// Start console
		editor.beginConsole();
	}
}
