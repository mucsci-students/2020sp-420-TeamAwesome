package views;

// System imports
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Scanner;

// Local imports
import controller.CommandController;
import controller.UMLController;
import main.ErrorHandler;
import main.UMLFileIO;
import model.UMLClassManager;
import observe.Observable;

public class ConsoleView extends View {
	private Scanner scanner;
	private UMLController controller;
	private UMLFileIO fileIO;
	
	// All valid commands
	private Hashtable<String, String[]> validCommands;
	
	public ConsoleView() {
		scanner = new Scanner(System.in);
		
		fileIO = new UMLFileIO();
		
		controller = new CommandController(new UMLClassManager());
		controller.addObserver(this);
		
		validCommands = new Hashtable<String, String[]>();
		populateValidCommands();
		
		while(true) {
			String input = getCommand();
			
			// Check if command is empty or only whitespace
			if(!input.isEmpty() && !input.replaceAll(" ", "").isEmpty()) {
				int result = execCommand(input);
				
				// If the result did not execute successfully, print error.
				if(result != 0) {
					System.err.println("Failed to execute command. Got error: ");
					System.err.println(ErrorHandler.toString(result));
				}
			}
		}
	}
	
	/**
	 * Execute the given command and report errors as necessary
	 * 
	 * @return boolean indicating if command was successfully executed
	 */
	public int execCommand(String command) {
		// Split command on white space
		// args[0] = name of the command
		// args[1...] = any arguments for the command
		String[] args = command.split(" ");
		
		// Result of given command
		// Should be updated for each command
		int result = 0;
		
		// Make sure list of args is not empty and a command exists
		if(args.length == 0 || args[0].trim().isEmpty()) {
			return 101;
		}
		
		// Check command, if it is a valid command then perform associated action
		//	Otherwise return false
		if(args[0].equals("exit") || args[0].equals("quit")) {
			System.out.println("Force quitting...");
			System.out.println("Goodbye :)");
			System.exit(0);
		}
		else if(args[0].equals("add-class")) {
			// Make sure there is an argument for just the class name
			if(args.length == 2) {
				// Pull args
				String className = args[1];
				
				result = controller.addClass(className);
				if(result == 0)
					System.out.println("Added class \'" + className + "\'.");
			}
			else {
				return 102;
			}
		}
		else if(args[0].equals("remove-class")) {
			// Make sure there is only an argument for the class name
			if(args.length == 2) {
				// Pull args
				String className = args[1];
				
				result = controller.removeClass(className);
				if(result == 0)
					System.out.println("Removed class \'" + className + "\'.");
			} 
			else {
				return 102;
			}
		}
		else if(args[0].equals("save")) {
			String filePath = "";
			
			if(args.length == 2) {
				// If user specified a file path, set the file to that path.
				filePath = args[1];
			}
			else if(args.length < 2) {
				// If the user did not specify a save file check to see if one is already saved.
				// If there is no file saved, prompt for one.
				if(!fileIO.fileSet()) {
					System.err.println("Save file not set.");
					System.err.flush();
					System.out.flush();
					System.out.print("Save file: ");
					filePath = scanner.nextLine();
				}
			}
			else {
				// If there are more than 2 arguments indicate an error for too many arguments.
				return 102;
			}
						
			if(!fileIO.fileSet()) {
				// Check to see if the file is set, if not make sure that filePath is not empty
				// Otherwise make sure the file ends with a .json and save
				if(filePath.replaceAll(" ", "").isEmpty()) {
					return 103;
				}
				
				// Ensure file extension
				if(!filePath.endsWith(".json")) {
					filePath += ".json";
				}
				
				// Set fileIO path
				result = fileIO.setFile(filePath);
				// Force return to prevent execution of next function
				if(result != 0)
					return result;
			}
			
			// Write JSON to file
			result = fileIO.writeToFile(controller.getModel().convertToJSON());
		}
		else if(args[0].equals("load")) {
			// Expects args[1] to be the path to the file to load.
			// Make sure there is enough arguments
			if(args.length > 1) {
				// Grab file path
				String filePath = args[1];
				
				// Set the file for FileIO
				result = fileIO.setFile(filePath);
				if(result != 0)
					return result;
				
				// Make sure the file exists
				if(!fileIO.fileExists()) {
					return 105;
				}
				
				// Read the file in and pass it to the classManager for parsing.
				result = controller.getModel().parseJSON((String)fileIO.readFile()[0]);
			}
			else {
				return 102;
			}
		}
		else if(args[0].equals("add-field")) {
			// Expects args[1]=className and args[2]=fieldName
			if(args.length == 3) {
				result = controller.addField(args[1], args[2]);
			}
			else {
				return 102;
			}
		}
		else if(args[0].equals("add-method")) {
			// Expects args[1]=className and args[2]=methodName
			if(args.length == 3) {
				result = controller.addMethod(args[1], args[2]);
			}
			else {
				return 102;
			}
		}
		else if(args[0].equals("remove-field")) {
			// Expects args[1]=className and args[2]=fieldName
			if(args.length == 3) {
				result = controller.removeField(args[1], args[2]);
			}
			else {
				return 102;
			}
		}
		else if(args[0].equals("remove-method")) {
			// Expects args[1]=className and args[2]=methodName
			if(args.length == 3) {
				result = controller.removeMethod(args[1], args[2]);
			}
			else {
				return 102;
			}
		}
		else if(args[0].equals("add-relationship")) {
			// Expects args[1]=className1 and args[2]=className2
			if(args.length == 3) {
				result = controller.addRelationship(args[1], args[2]);
			}
			else {
				return 102;
			}
		}
		else if(args[0].equals("remove-relationship")) {
			// Expects args[1]=className1 and args[2]=className2
			if(args.length == 3) {
				result = controller.removeRelationship(args[1], args[2]);
			}
			else {
				return 102;
			}
		}
		else if(args[0].equals("list-fields")) {
			// Expects args[1]=className
			if(args.length == 2) {
				String className = args[1];
				
				Object[] objResult = controller.getModel().listFields(className);
				result = (int)objResult[1];
				
				if(result == 0)
					System.out.println(className + ": " + objResult[0]);
			} else
				return 102;
		}
		else if(args[0].equals("list-methods")) {
			// Expects args[1]=className
			if(args.length == 2) {
				String className = args[1];
				
				Object[] objResult = controller.getModel().listMethods(className);
				result = (int)objResult[1];
				
				if(result == 0)
					System.out.println(className + ": " + objResult[0]);
			} else
				return 102;
		}
		else if(args[0].equals("list-relationships")) {
			// Expects args[1]=className
			if(args.length == 2) {
				String className = args[1];
				
				Object[] objResult = controller.getModel().listRelationships(className);
				result = (int)objResult[1];
				
				if(result == 0)
					System.out.println(className + ": " + objResult[0]);
			} else
				return 102;
		}
		else if(args[0].equals("list-classes")) {
			System.out.println("Classes: " + controller.getModel().listClasses());
		}
		else if(args[0].equals("help")) {
			printHelp();
		}
		else {
			return 104;
		}
		
		System.out.flush();
		System.err.flush();
		
		return result;
	}
	
	private String getCommand() {
		// Preface to indicate waiting for input
		System.out.print("editor> ");
		// Get input from user
		String str = scanner.nextLine();
		// Return user-entered string
		return str;
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

	@Override
	public void updated(Observable src, String tag, Object data) {}
}
