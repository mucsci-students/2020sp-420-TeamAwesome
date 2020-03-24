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
	
	/**
	 * Initialize required variables for console
	 */
	public ConsoleView() {
		scanner = new Scanner(System.in);
		
		fileIO = new UMLFileIO();
		
		controller = new CommandController(new UMLClassManager());
		controller.addObserver(this);
		
		validCommands = new Hashtable<String, String[]>();
		populateValidCommands();
	}
	
	/**
	 * Start input loop
	 */
	public void start() {
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
			
			// Close the scanner
			closeConsole();
			
			System.exit(0);
		}

		//split for all add methods
		else if(args[0].equals("add")){
			if (args.length < 3)
			{
				return 102;
			}
			if(args[1].equals("class"))
			{
				if (args.length == 3) {
				String className = args[2];
				result = controller.addClass(className);
				if (result == 0)
				{
				System.out.println("Added class \'" + className + "\'.'");
				}
				}
				else {
					return 102;
				}
				
			}
			else if (args[1].equals("field"))
			{
				if(args.length == 4) {
					result = controller.addField(args[2], args[3]);
					System.out.println("Added field \'" + args[3] + "\' to class \'" + args[2] + "\'.");
				}
				else {
					return 102;
				}
			}
			else if (args[1].equals("method"))
			{
				if(args.length == 4) {
					result = controller.addMethod(args[2], args[3]);
					System.out.println("Added method \'" + args[3] + "\' to class \'" + args[2] + "\'.");
				}
				else {
					return 102;
				}
			}
			else if (args[1].equals("relationship"))
			{
				if(args.length == 4) {
					result = controller.addRelationship(args[2], args[3]);
					System.out.println("Added relationship between class \'" + args[3] + "\' and class \'" + args[2] + "\'.");
				}
				else {
					return 102;
				}
			}
			else return 102;
			}

		else if (args[0].equals("remove")){
			if (args.length < 2)
			{
				return 102;
			}
			else if (args[1].equals("class"))
			{
				if(args.length == 3) {
					// Pull args
					String className = args[2];
					
					result = controller.removeClass(className);
					if(result == 0)
						System.out.println("Removed class \'" + className + "\'.");
				} 
				else {
					return 102;
				}
			}
			else if (args[1].equals("field"))
			{
				if(args.length == 4) {
					result = controller.removeField(args[2], args[3]);
					System.out.println("Removed field \'" + args[3] + "\' to class \'" + args[2] + "\'.");
				}
				else {
					return 102;
				}
			}
			else if (args[1].equals("method"))
			{
				if(args.length == 4) {
					result = controller.removeMethod(args[2], args[3]);
					System.out.println("Removed method \'" + args[3] + "\' to class \'" + args[2] + "\'.");
				}
				else {
					return 102;
				}
			}
			else if (args[1].equals("relationship"))
			{
				if(args.length == 4) {
					result = controller.removeRelationship(args[2], args[3]);
					System.out.println("Removed relationship between class \'" + args[3] + "\' and class \'" + args[2] + "\'.");
				}
				else {
					return 102;
				}
			}

		}

		else if (args[0].equals("edit"))
		{
			if(args.length < 3)
			{
				return 102;
			}
			else if (args[1].equals("class"))
			{
				if(args.length == 4) {
					String className = args[2];
					String newName = args[3];
					
					result = ((CommandController) controller).editClass(className, newName);
					if(result == 0)
						System.out.println("Changed class \'" + className + "\' to \'" + newName + "\'.");
				}
				else {
					return 102;
				}
			}
			else if (args[1].equals("field"))
			{
				if (args.length == 5) {
					
					String className = args[2];
					String oldName = args[3];
					String newName = args[4];
						
					result = ((CommandController) controller).editField(className,oldName, newName);
					if(result == 0)
						System.out.println("Changed field \'" + oldName + "\' to \'" + newName + "\' in class \'" + className + "\'.");
					else {
						return 102;
					}
				}
			}
			else if (args[1].equals("method"))
			{
				if(args.length == 5) {
					String className = args[2];
					String oldName = args[3];
					String newName = args[4];
					
					result = ((CommandController) controller).editMethod(className, oldName, newName);
					if(result == 0)
						System.out.println("Changed method \'" + oldName + "\' to \'" + newName + "\' in class \'" + className + "\'.");
				}
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
		else if(args[0].equals("help") && args.length == 1) {
			printHelp();
		}
		else if (args[0].equals("help") && args.length == 2 && !args[1].isEmpty())
		{
			commandHelp(args[1]);
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
	
	private void commandHelp(String command) {
		if (!validCommands.contains(command)){
			System.out.println("Command does not exist, try \"help\" for a list of avaliable commands");
		}
		else {
			System.out.println();
			String desc = validCommands.get(command)[0];
			System.out.printf("%25s: %-40s\n", command, desc);
		}
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
		validCommands.put("add", new String[]{"Base command, accepts a Class, Method, relationship, or Field; for more help try 'help add <parameter>'"});
		validCommands.put("add class <class_name>", new String[]{"Add the given class name"});
		validCommands.put("edit class <class_name>", new String[]{"Edit the given existing class to the new given class name"});
		validCommands.put("remove class <class_name>", new String[]{"Add the given class name"});
		validCommands.put("exit", new String[]{"Quit the program"});
		validCommands.put("quit", new String[]{"Quit the program"});
		validCommands.put("save", new String[]{"Save the current state of the UML diagram", "if a file has not been set it will prompt the user."});
		validCommands.put("load <file_path>", new String[] {"Load the given file into the UML editor"});
		validCommands.put("list classes", new String[]{"List all of the created classes"});
		validCommands.put("add field", new String[]{"Takes a exisiting className and new fieldName to add the field into the class"});
		validCommands.put("edit field <class_name>", new String[]{"Takes an existing field in an existing class and change it to the given new name"});
		validCommands.put("add method", new String[]{"Takes a exisiting className and new methodName to add the method into the class"});
		validCommands.put("edit field <class_name>", new String[]{"Takes an existing method in an existing class and change it to the given new name"});
		validCommands.put("remove field", new String[]{"Takes a exisiting className and existing fieldName to remove the field from the class"});
		validCommands.put("remove method", new String[]{"Takes a exisiting className and existing methodName to remove the method from the class"});
		validCommands.put("add relationship", new String[]{"Takes two exisiting classNames and creates a relationship between the two"});
		validCommands.put("remove relationship", new String[]{"Takes two exisiting classNames and removes an exisiting relationship between the two"});
		validCommands.put("list fields", new String[]{"Takes an exisiting class and lists all fields assoicatied with the class"});
		validCommands.put("list methods", new String[]{"Takes an exisiting class and lists all methods assoicatied with the class"});
		validCommands.put("list relationships", new String[]{"Takes an exisiting class and lists all relationships assoicatied with the class"});
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
	
	/**
	 * Get scanner instance
	 * @return - scanner
	 */
	public Scanner getScanner() {
		return scanner;
	}
	
	/**
	 * Close the input scanner
	 * @return - return code
	 */
	public int closeConsole() {
		scanner.close();
		
		// If scanner has been successfully closed an error should be thrown
		try {
			scanner.nextLine();
			return 100;
		} catch(IllegalStateException ise) {
			return 0;
		}
	}
}
