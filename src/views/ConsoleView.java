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
				System.out.println("Added class \'" + className + "\'.'");
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
					//takes exactly five arguments; classname, type, fieldname
					result = controller.addField(args[2], args[3], args[4]);
					//adds fieldname (if valid) to given exisiting class
					System.out.println("Added field \'" + args[4] + "\' to class \'" + args[2] + "of type \'" + args[3] "\'.");
				}
				else {
					//invalid arguments
					return 102;
				}
			}
			//add method...method
			else if (args[1].equals("method"))
			{
				
				if(args.length >= 6) {
					//takes at least six arguments of add method class1 returnType methodName (x amount of params)
					//combine all params into a paramlist to pass to controller
					String paramlist; 
					int i = 5;
					while ( i < args.length-1)
					{
						paramlist = paramlist.concat(args[i] + " ");
						++i;
					}
					result = controller.addMethod(args[2], args[3], args[4], paramlist);
					//adds valid methodName to given exisiting class
					System.out.println("Added method \'" + args[4] + "\' to class " + args[2] + " \' which accepts" + paramlist +  " \'and returns a \'" + args[3] + "\'.");
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
					System.out.println("Added relationship between class \'" + args[4] + "\' and class \'" + args[2] + "\'" + "of type" + args[3] + "\.");
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
					System.out.println("Removed field \'" + args[3] + "\' to class \'" + args[2] + "\'.");
				}
				else {
					//invalid arugment amount
					return 102;
				}
			}
			//remove method...method
			else if (args[1].equals("method"))
			{
				if(args.length >= 5) {
					//takes at least 5 arguments; remove, method, classname, methodname, paramName(s)
					//take all params after args[3] and concat into a param list
					String paramlist; 
					int i = 4;
					while ( i < args.length-1)
					{
						paramlist = paramlist.concat(args[i] + " ");
						++i;
					}
					result = controller.removeMethod(args[2], args[3], paramlist);
					System.out.println("Removed method \'" + args[3] + "\' from class \'" + args[2] + "\'.");
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
					System.out.println("Removed relationship between class \'" + args[4] + "\' and class \'" + args[2] + "of type \'" + args[3] + "\'.");
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
						
					result = ((CommandController) controller).editField(className,oldName, newName);
					if(result == 0)
						System.out.println("Changed field \'" + oldName + "\' to \'" + newName + "\' in class \'" + className + "\'.");
					else {
						//invalid param count
						return 102;
					}
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
					String paramlist; 
					int i = 5;
					while ( i < args.length-1)
					{
						paramlist = paramlist.concat(args[i] + " ");
						++i;
					}
					result = ((CommandController) controller).editMethod(className, oldName, newName, paramlist);
					if(result == 0)
						System.out.println("Changed method \'" + oldName + "\' to \'" + newName + "\' in class \'" + className + "\'.");
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
