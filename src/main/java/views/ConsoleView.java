package views;

// System imports
import java.util.Enumeration;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;

// Local imports
import controller.CommandController;
import controller.UMLController;
import core.ErrorHandler;
import core.UMLFileIO;
import model.UMLClass;
import model.UMLClassManager;
import observe.Observable;

public class ConsoleView extends View {
	private Scanner scanner;
	private UMLController controller;
	private UMLFileIO fileIO;
	
	// All valid commands
	private LinkedHashMap<String, String> validCommands;
	
	/**
	 * Initialize required variables for console
	 */
	public ConsoleView() {
		scanner = new Scanner(System.in);
		
		fileIO = new UMLFileIO();
		
		controller = new CommandController(new UMLClassManager());
		controller.addObserver(this);
		
		validCommands = new LinkedHashMap<String, String>();
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
			// after first argument is add check what is being added
			if (args.length < 3)
			//all adds require at least 3 arguments
			{
				return 102;
			}
			if(args[1].equals("class"))
			{
				//add class requires only one more argument, any more or less is rejected
				if (args.length == 3) {
				String className = args[2];
				result = controller.addClass(className);
				if (result == 0)
				{
					//successfully added class
				System.out.println("Added class \'" + className + "\'.");
				}
				}
				else {
					//if failed the arguments are invalid 
					return 102;
				}
				
			}
			// the add field method
			else if (args[1].equals("field"))
			{
				if(args.length == 5) {
					//takes exactly five arguments; add field classname, type, fieldname
					result = controller.addField(args[2], args[3], args[4]);
					//adds fieldname (if valid) to given exisiting class
					System.out.println("Added field \'" + args[4] + "\' to class \'" + args[2] + "\' of type \'" + args[3] + "\'.");
				}
				else {
					//invalid arguments
					return 102;
				}
			}
			//add method...method
			else if (args[1].equals("method"))
			{
				
				if(args.length >= 5) {
					//takes at least six arguments of add method class1 returnType methodName (x amount of params)
					//combine all params into a paramlist to pass to controller
					String paramlist = ""; 
					int i = 5;
					while ( i < args.length)
					{
						paramlist = paramlist.concat(args[i] + " ");
						++i;
					}
					// Remove trailing whitespace
					paramlist = paramlist.trim();
					
					result = controller.addMethod(args[2], args[3], args[4], paramlist);
					//adds valid methodName to given exisiting class
					System.out.println("Added method \'" + args[4] + "\' which accepts \'"+ paramlist + "\' with returnType: \'" + args[3] + "\' to class \'" + args[2] + "\'.");
				}
				else {
					//invalid amount of arguments
					return 102;
				}
			}
			//add relationship method
			else if (args[1].equals("relationship"))
			{
				if(args.length == 5) {
					//takes exactly 5 arguments in order of add relationship class1 type class2
					result = controller.addRelationship(args[2], args[3],  args[4]);
					System.out.println("Added " + args[3] +  " relationship from \'" + args[2] + "\' to \'" + args[4] + "\'.");
				}
				else {
					//invalid number arguments
					return 102;
				}
			}
		
			}

			//command split for all remove methods
		else if (args[0].equals("remove")){
			//remove requires at least 2 parameters
			if (args.length < 2)
			{
				return 102;
			}
			//remove class method
			else if (args[1].equals("class"))
			{
				if(args.length == 3) {
					// Pull exactly 3 args; remove, class, className
					String className = args[2];
					
					result = controller.removeClass(className);
					if(result == 0)
						System.out.println("Removed class \'" + className + "\'.");
				} 
				else {
					//invalid arg count
					return 102;
				}
			}
			//remove field method
			else if (args[1].equals("field"))
			{
				if(args.length == 4) {
					//takes exactly 4 arguments; remove, field, classname, fieldname
					result = controller.removeField(args[2], args[3]);
					System.out.println("Removed field \'" + args[3] + "\' from class \'" + args[2] + "\'.");
				}
				else {
					//invalid argument amount
					return 102;
				}
			}
			//remove method...method
			else if (args[1].equals("method"))
			{
				if(args.length >= 5) {
					//takes at least 5 arguments; remove, method, classname, methodname, paramName(s)
					//take all params after args[3] and concat into a param list
					String paramlist = ""; 
					int i = 4;
					while ( i < args.length)
					{
						paramlist = paramlist.concat(args[i] + " ");
						++i;
					}
					paramlist = paramlist.trim();
					result = controller.removeMethod(args[2], args[3], paramlist);
					System.out.println("Removed method \'" + args[3] + "\' ( " + paramlist + " )" + " from class \'" + args[2] + "\'.");
				}
				else {
					//invalid amount of parameters
					return 102;
				}
			}
			//remove relationship method
			else if (args[1].equals("relationship"))
			{
				if(args.length == 5) {
					//takes exaclty 5 arguments; remove, relationship, classname1, type, classname2
					result = controller.removeRelationship(args[2], args[3], args[4]);
					System.out.println("Removed " + args[3] + " relationship between \'" + args[4] + "\' and \'" + args[2] + "\'.");
				}
				else {
					//invalid amount of params
					return 102;
				}
			}

		}
		//split for edit command
		else if (args[0].equals("edit"))
		{
			if(args.length < 3)
			{
				//all edits take at least 3 params
				return 102;
			}
			//edit class method
			else if (args[1].equals("class"))
			{
				if(args.length == 4) {
					//takes 4 args; edit, class, className, newName
					String className = args[2];
					String newName = args[3];
					
					result = ((CommandController) controller).editClass(className, newName);
					if(result == 0)
						System.out.println("Changed class \'" + className + "\' to \'" + newName + "\'.");
				}
				else {
					// invalid param amount
					return 102;
				}
			}
			//edit field method
			else if (args[1].equals("field"))
			{
				if (args.length == 5) {
					//takes 5 arguments; edit field classname oldfield newfield
					String className = args[2];
					String oldName = args[3];
					String newName = args[4];
						
					result = ((CommandController) controller).editField(className, oldName, newName);
					if(result == 0)
						System.out.println("Changed field \'" + oldName + "\' to \'" + newName + "\' in class \'" + className + "\'.");
				}
				else {
					//invalid param count
					return 102;
				}
			}
			//edit method..method 
			else if (args[1].equals("method"))
			{
				if(args.length >= 5) {
					// takes at least 5 arguments; edit, method, classname, oldname, newName, params
					String className = args[2];
					String oldName = args[3];
					String newName = args[4];
					String paramlist = ""; 
					int i = 5;
					while ( i < args.length)
					{
						paramlist = paramlist.concat(args[i] + " ");
						++i;
					}
					paramlist = paramlist.trim();
					result = ((CommandController) controller).editMethod(className, oldName, newName, paramlist);
					if(result == 0)
						System.out.println("Changed method \'" + oldName + "\' from \'" + className + "\' to \'" + newName + "\'.");
				}
				else return 102;
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
		
		
		//Split for list
		else if(args[0].equals("list")) {
			// Expects minimum of 2 args and maximum of 3 args.
			if(args.length == 2) {
				if(args[1].equals("classes")) {
					String[] listBox = controller.listClasses();
					for(int i = 0; i < listBox.length; i++) {
						System.out.println(listBox[i]);
					}
				}
				else if(args[1].equals("relationships")) {
					controller.listRelationships();
				}
			} else if(args.length == 3) {
				//Expect arg[2] to be class name
				String className = args[2];
				if(args[1].equals("classes")) {
					String[] classBox = controller.listClasses(className);
					for(int i = 0; i < classBox.length; i++) {
						System.out.println(classBox[i]);
					}
				}
				else if(args[1].equals("relationships")) {
					controller.listRelationships(className);
				}
			} else{
				return 102;
			}
		}
		//split for help
		else if(args[0].equals("help")) {
			//Expect maximum of 2 args
			if(args.length == 1) {
				printHelp();
				return 0;
			}
			else if(args.length == 2) {
				if (validCommands.containsKey(args[1]))
				{
					commandHelp(args[1]);
					return 0;
				}
				else {
					System.out.println("Command does not exist, try \"help\" for a list of avaliable commands");
				}
			}
			return 102;
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
		System.out.println();
		String desc = validCommands.get(command);
		System.out.printf("%-40s", desc);
	}
	/**
	 * Fills validCommands with the command name and a description
	 * Format is as follows
	 * 		validCommands.put(name, list_of_description_lines);
	 * 		name = the name of the command
	 * 		list_description_lines = an array of strings where each item is a new line to print out as a description
	 */
	private void populateValidCommands() {
		validCommands.put("help", "help: Prints out a list of valid commands with descriptions for each command.  Typing help <command> describes a specific command.");
		validCommands.put("add", "add: Can add class with add <class_name>.  Also adds fields and methods to specified class in the form add <field/method> <class_name> <type/return_type> <field/method_name> <parameter_list>(for methods)." + System.lineSeparator()
		+ "     Add relationship in the form add <relationship> <class_name1> <relationship_type> <class_name2>.");
		validCommands.put("edit", "edit: Edit classes with edit class <old_class_name> <new_class_name>.  Edit fields and methods with edit <field/method> <source_class> <old_name> <new_name> <parameter_list>(for method).");
		validCommands.put("remove", "remove: Can remove class with remove <class_name>.  Also removes fields and methods from specified class in the form remove <field/method> <class_name> <field/method_name> <parameter_list>(for methods)." + System.lineSeparator()
		+ "\tRemove relationship in the form remove <relationship> <class_name1> <relationship_type> <class_name2>.");
		validCommands.put("exit", "exit: Quit the program.");
		validCommands.put("quit", "quit: Quit the program.");
		validCommands.put("save", "save: Save the current state of the UML diagram.  If a file has not been set it will prompt the user.");
		validCommands.put("load", "load <file_path>: Load the given file into the UML editor.");
		validCommands.put("list", "list: Can list all classes with list classes or specific class with list classes <class_name>.  These lists take the form of boxes with the class name and its associated attributes inside." + System.lineSeparator()
		+ "      List all relationships with list relationships or list relationships <class_name>.  Takes the form boxes for each class containing their associated attributes with a delimiter for relationship type between classes.");
	}
	
	/**
	 * Get the list of valid commands
	 * @return validCommands
	 */
	public LinkedHashMap<String, String> getValidCommands() {
		return validCommands;
	}
	
	/**
	 * Print out the a list of commands with descriptions
	 */
	private void printHelp() {
		System.out.println();
		System.out.println("VALID COMMANDS");
		System.out.println("---------------------------------");
		
		// For every key in dictionary print corresponding description
		for(Map.Entry<String, String> entry : validCommands.entrySet()) {
			System.out.println(entry.getValue());
		}
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
