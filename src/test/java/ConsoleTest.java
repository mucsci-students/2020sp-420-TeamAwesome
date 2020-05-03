// System imports
import org.junit.Test;

import controller.CommandController;
import controller.GUIController;
import model.UMLClassManager;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.PrintStream;

// Local imports
import views.ConsoleView;
import views.GUIView;
import views.components.testable.TestableFileChooser;
import views.components.testable.TestableMenuItem;

/**
 * Class to run JUnit tests on the Console
 * 
 * @author Ryan
 */
public class ConsoleTest {
	/**
	 * Create environment and don't do anything to it
	 */
	@Test
	public void baseInstance() {
		UMLClassManager model = new UMLClassManager();
		ConsoleView console = new ConsoleView(model, new CommandController(model));
		assertTrue("Console not null", console.getScanner() != null);
		assertTrue("Commands not null", console.getValidCommands() != null);
		assertTrue("Commands not empty", !(console.getValidCommands().isEmpty()));
	}
	
	/**
	 * Test empty inputs
	 */
	@Test
	public void emptyInput() {
		UMLClassManager model = new UMLClassManager();
		ConsoleView console = new ConsoleView(model, new CommandController(model));
		assertEquals("Empty string", 101, console.execCommand("", System.out));
		assertEquals("Empty string 2", 101, console.execCommand("   ", System.out));
		assertEquals("Empty string 3", 101, console.execCommand("\t", System.out));
		assertEquals("Empty string 4", 101, console.execCommand("\r", System.out));
	}
	
	/**
	 * Test invalid commands
	 */
	@Test
	public void invalidInput() {
		UMLClassManager model = new UMLClassManager();
		ConsoleView console = new ConsoleView(model, new CommandController(model));
		assertEquals("Invalid command 1", 104, console.execCommand("this isnt real", System.out));
		assertEquals("Invalid command 2", 104, console.execCommand("fake command", System.out));
		assertEquals("Invalid command 3", 104, console.execCommand("jibbersih", System.out));
	}
	
	/**
	 * Test the add command output
	 * NOTE SOME OUTPUT RELIES ON FUNCTIONING MODEL
	 */
	@Test
	public void addClassCommand() {
		// Create output stream for executing commands
		ByteArrayOutputStream bos = new ByteArrayOutputStream();
		PrintStream myout = new PrintStream(bos);

		UMLClassManager model = new UMLClassManager();
		ConsoleView console = new ConsoleView(model, new CommandController(model));
		
		// Add class with valid input
		assertEquals("add class valid return code", 0, console.execCommand("add class normal", myout));
		myout.flush();
		assertEquals("add class valid out stream", "Added class \'normal\'.", scrubOut(bos.toString()));
		bos.reset();
		
		// Add class with invalid input
		assertNotEquals("add class invalid return code", 0, console.execCommand("add class not*re(la", myout));
		assertEquals("add class invalid return code 2", 407, console.execCommand("add class 38&32H3", myout));
		
		// Add class with wrong number of args
		assertEquals("add class too many args", 102, console.execCommand("add class myclass another", myout));
		assertEquals("add class too many args 2", 102, console.execCommand("add class myclass another woa", myout));
		assertEquals("add class too few args", 102, console.execCommand("add class", myout));
		
		// Add with no specifier
		assertEquals("add with no specifier", 102, console.execCommand("add", myout));
		
		// Close the print stream
		myout.close();
	}
	
	/**
	 * Test the add field output
	 * NOTE SOME OUTPUT RELIES ON FUNCTIONING MODEL
	 */
	@Test
	public void addFieldCommand() {
		// Create output stream for executing commands
		ByteArrayOutputStream bos = new ByteArrayOutputStream();
		PrintStream myout = new PrintStream(bos);
		
		// Setup console and model
		UMLClassManager model = new UMLClassManager();
		ConsoleView console = new ConsoleView(model, new CommandController(model));
		model.addClass("myclass");
		
		// Add field with valid input
		assertEquals("add field valid return code", 0, console.execCommand("add field myclass mytype myfield", myout));
		myout.flush();
		assertEquals("add field valid out stream", "Added field \'myfield\' to class \'myclass\' of type \'mytype\'.", scrubOut(bos.toString()));
		bos.reset();
		
		// Add field with invalid input
		assertEquals("add field invalid return code", 203, console.execCommand("add field myclass not*re(la fielda", myout));
		assertEquals("add field invalid return code 2", 203, console.execCommand("add field myclass 38&32H3 fieldt", myout));
		
		// Add field with wrong number of args
		assertEquals("add field too many args", 102, console.execCommand("add field myclass another another field", myout));
		assertEquals("add field too many args 2", 102, console.execCommand("add field myclass another crazy crazy field", myout));
		assertEquals("add field too few args", 102, console.execCommand("add field myclass", myout));
		assertEquals("add field too few args", 102, console.execCommand("add field", myout));
		
		// Add with no specifier
		assertEquals("add with no specifier", 102, console.execCommand("add", myout));
		
		// Close the print stream
		myout.close();
	}
	
	/**
	 * Test the add method output
	 * NOTE SOME OUTPUT RELIES ON FUNCTIONING MODEL
	 */
	@Test
	public void addMethodCommand() {
		// Create output stream for executing commands
		ByteArrayOutputStream bos = new ByteArrayOutputStream();
		PrintStream myout = new PrintStream(bos);
		
		// Setup console and model
		UMLClassManager model = new UMLClassManager();
		ConsoleView console = new ConsoleView(model, new CommandController(model));
		model.addClass("myclass");
		
		// Add method with valid input
		assertEquals("add method valid return code", 0, console.execCommand("add method myclass int mymethod param", myout));
		myout.flush();
		assertEquals("add method valid out stream", "Added method \'mymethod\' which accepts \'param\' with returnType: \'int\' to class \'myclass\'.", scrubOut(bos.toString()));
		bos.reset();
		
		// Add method with invalid input
		assertEquals("add method invalid return code", 408, console.execCommand("add method myclass int not*re(la", myout));
		assertEquals("add method invalid return code 2", 203, console.execCommand("add method myclass 38&32H3 realname params", myout));
		
		// Add method with wrong number of args
		assertEquals("add method too few args", 102, console.execCommand("add method myclass", myout));
		assertEquals("add method too few args", 102, console.execCommand("add method", myout));
		
		// Add with no specifier
		assertEquals("add with no specifier", 102, console.execCommand("add", myout));
		
		// Close the print stream
		myout.close();
	}
	
	/**
	 * Test the add relationship output
	 * NOTE SOME OUTPUT RELIES ON FUNCTIONING MODEL
	 */
	@Test
	public void addRelationshipCommand() {
		// Create output stream for executing commands
		ByteArrayOutputStream bos = new ByteArrayOutputStream();
		PrintStream myout = new PrintStream(bos);
		
		// Setup console and model
		UMLClassManager model = new UMLClassManager();
		ConsoleView console = new ConsoleView(model, new CommandController(model));
		model.addClass("myclass");
		model.addClass("another");
		model.addClass("third");
		
		// Add relationships with valid input
		assertEquals("add relationship valid return code", 0, console.execCommand("add relationship myclass aggregation another", myout));
		myout.flush();
		assertEquals("add relationship valid output", "Added aggregation relationship from \'myclass\' to \'another\'.", scrubOut(bos.toString()));
		bos.reset();
		assertEquals("add relationship 2 valid return code", 0, console.execCommand("add relationship another composition third", myout));
		myout.flush();
		assertEquals("add relationship 2 valid output", "Added composition relationship from \'another\' to \'third\'.", scrubOut(bos.toString()));
		bos.reset();
		
		// Add relationships with invalid input
		// ERROR CODE WILL CHANGE ONCE THE FUNCTIONALITY HAS BEEN WRITTEN AND A RETURN CODE HAS BEEN ASSIGNED
		assertEquals("add relationship class not exist return code", 107, console.execCommand("add relationship notarealclass aggregation another", myout));
		assertEquals("add relationship class not exist return code", 107, console.execCommand("add relationship notarealclass aggregation another", myout));
		assertEquals("add relationship type not exist return code", 202, console.execCommand("add relationship myclass notreal another", myout));
		
		// Add relationships with bad arg count
		assertEquals("Add relationship with too many args", 102, console.execCommand("add relationship myclass aggregation another morestuff yay", myout));
		assertEquals("Add relationship with too many args 2", 102, console.execCommand("add relationship myclass inheritance another third", myout));
		assertEquals("Add relationship with too few args", 102, console.execCommand("add relationship myclass aggregation", myout));
		assertEquals("Add relationship with too few args 2", 102, console.execCommand("add relationship myclass", myout));
		assertEquals("Add relationship no args", 102, console.execCommand("add relationship", myout));
		
		// Add with no specifier
		assertEquals("Add with no specifier", 102, console.execCommand("add", myout));
		
		// Close the print stream
		myout.close();
	}
	
	/**
	 * Test the remove command output
	 * NOTE SOME OUTPUT RELIES ON FUNCTIONING MODEL
	 */
	@Test
	public void removeClassCommand() {
		// Create output stream for executing commands
		ByteArrayOutputStream bos = new ByteArrayOutputStream();
		PrintStream myout = new PrintStream(bos);
		
		// Setup console and model
		UMLClassManager model = new UMLClassManager();
		ConsoleView console = new ConsoleView(model, new CommandController(model));
		model.addClass("normal");
		model.addClass("myclass");
		
		// Remove class with valid input
		assertEquals("remove class valid return code", 0, console.execCommand("remove class normal", myout));
		myout.flush();
		assertEquals("remove class valid out stream", "Removed class \'normal\'.", scrubOut(bos.toString()));
		bos.reset();
		
		// remove class with invalid input
		assertEquals("remove class invalid return code", 201, console.execCommand("remove class not*re(la", myout));
		assertEquals("remove class invalid return code 2", 201, console.execCommand("remove class 38&32H3", myout));
		
		// remove class with wrong number of args
		assertEquals("remove class too many args", 102, console.execCommand("remove class myclass another", myout));
		assertEquals("remove class too many args 2", 102, console.execCommand("remove class myclass another woa", myout));
		assertEquals("remove class too few args", 102, console.execCommand("remove class", myout));
		
		// remove with no specifier
		assertEquals("remove with no specifier", 102, console.execCommand("remove", myout));
		
		// Close the print stream
		myout.close();
	}
	
	/**
	 * Test the remove field output
	 * NOTE SOME OUTPUT RELIES ON FUNCTIONING MODEL
	 */
	@Test
	public void removeFieldCommand() {
		// Create output stream for executing commands
		ByteArrayOutputStream bos = new ByteArrayOutputStream();
		PrintStream myout = new PrintStream(bos);
		
		// Setup console and model
		UMLClassManager model = new UMLClassManager();
		ConsoleView console = new ConsoleView(model, new CommandController(model));
		model.addClass("myclass");
		model.addFields("myclass", "type", "myfield");
		
		// Remove field with valid input
		assertEquals("remove field valid return code", 0, console.execCommand("remove field myclass myfield", myout));
		myout.flush();
		assertEquals("remove field valid out stream", "Removed field \'myfield\' from class \'myclass\'.", scrubOut(bos.toString()));
		bos.reset();
		
		// Remove field with invalid input
		assertEquals("remove field invalid return code", 405, console.execCommand("remove field myclass not*re(la", myout));
		assertEquals("remove field invalid return code 2", 405, console.execCommand("remove field myclass 38&32H3", myout));
		
		// Remove field with wrong number of args
		assertEquals("remove field too many args", 102, console.execCommand("remove field myclass another field", myout));
		assertEquals("remove field too many args 2", 102, console.execCommand("remove field myclass another crazy field", myout));
		assertEquals("remove field too few args", 102, console.execCommand("remove field myclass", myout));
		assertEquals("remove field too few args", 102, console.execCommand("remove field", myout));
		
		// Remove with no specifier
		assertEquals("add with no specifier", 102, console.execCommand("remove", myout));
		
		// Close the print stream
		myout.close();
	}
	
	/**
	 * Test the remove method output
	 * NOTE SOME OUTPUT RELIES ON FUNCTIONING MODEL
	 */
	@Test
	public void removeMethodCommand() {
		// Create output stream for executing commands
		ByteArrayOutputStream bos = new ByteArrayOutputStream();
		PrintStream myout = new PrintStream(bos);
		
		// Setup console and model
		UMLClassManager model = new UMLClassManager();
		ConsoleView console = new ConsoleView(model, new CommandController(model));
		model.addClass("myclass");
		model.addMethods("myclass", "int", "mymethod", "param");
		
		// Remove method with valid input
		assertEquals("remove method valid return code", 0, console.execCommand("remove method myclass mymethod param", myout));
		myout.flush();
		assertEquals("remove method valid out stream", "Removed method \'mymethod\' ( param ) from class \'myclass\'.", scrubOut(bos.toString()));
		bos.reset();
		
		// Remove method with invalid input
		assertEquals("remove method invalid return code", 406, console.execCommand("remove method myclass int not*re(la", myout));
		assertEquals("remove method invalid return code 2", 403, console.execCommand("remove method notReal int mymethod", myout));
		
		// Remove method with wrong number of args
		assertEquals("remove method too few args", 102, console.execCommand("remove method myclass", myout));
		assertEquals("remove method too few args", 102, console.execCommand("remove method", myout));
		
		// Remove with no specifier
		assertEquals("add with no specifier", 102, console.execCommand("remove", myout));

		// Close the print stream
		myout.close();
	}
	
	/**
	 * Test the remove relationship output
	 * NOTE SOME OUTPUT RELIES ON FUNCTIONING MODEL
	 */
	@Test
	public void removeRelationshipCommand() {
		// Create output stream for executing commands
		ByteArrayOutputStream bos = new ByteArrayOutputStream();
		PrintStream myout = new PrintStream(bos);
		
		// Setup console and model
		UMLClassManager model = new UMLClassManager();
		ConsoleView console = new ConsoleView(model, new CommandController(model));
		model.addClass("myclass");
		model.addClass("another");
		model.addClass("third");
		model.addRelationship("myclass", "aggregation", "another");
		model.addRelationship("another", "composition", "third");
		
		// Remove relationships with valid input
		assertEquals("remove relationship valid return code", 0, console.execCommand("remove relationship myclass aggregation another", myout));
		myout.flush();
		assertEquals("remove relationship valid output", "Removed aggregation relationship between \'another\' and \'myclass\'.", scrubOut(bos.toString()));
		bos.reset();
		assertEquals("remove relationship 2 valid return code", 0, console.execCommand("remove relationship another composition third", myout));
		myout.flush();
		assertEquals("remove relationship 2 valid output", "Removed composition relationship between \'third\' and \'another\'.", scrubOut(bos.toString()));
		bos.reset();
		
		// Remove relationships with invalid input
		// ERROR CODE WILL CHANGE ONCE THE FUNCTIONALITY HAS BEEN WRITTEN AND A RETURN CODE HAS BEEN ASSIGNED
		assertEquals("remove relationship class not exist return code", 107, console.execCommand("remove relationship notarealclass aggregation another", myout));
		assertEquals("remove relationship class not exist return code", 107, console.execCommand("remove relationship another aggregation notarealclass", myout));
		assertEquals("remove relationship type not exist return code", 108, console.execCommand("remove relationship myclass notreal another", myout));
		
		// Remove relationships with bad arg count
		assertEquals("remove relationship with too many args", 102, console.execCommand("remove relationship myclass aggregation another morestuff yay", myout));
		assertEquals("remove relationship with too many args 2", 102, console.execCommand("remove relationship myclass inheritance another third", myout));
		assertEquals("remove relationship with too few args", 102, console.execCommand("remove relationship myclass aggregation", myout));
		assertEquals("remove relationship with too few args 2", 102, console.execCommand("remove relationship myclass", myout));
		assertEquals("remove relationship no args", 102, console.execCommand("remove relationship", myout));
		
		// remove with no specifier
		assertEquals("remove", 102, console.execCommand("remove", myout));
		
		// Close the print stream
		myout.close();
	}
	
	/**
	 * Test the edit class output
	 * NOTE SOME OUTPUT RELIES ON FUNCTIONING MODEL
	 */
	@Test
	public void editClassCommand() {
		// Create output stream for executing commands
		ByteArrayOutputStream bos = new ByteArrayOutputStream();
		PrintStream myout = new PrintStream(bos);
		
		// Setup console and model
		UMLClassManager model = new UMLClassManager();
		ConsoleView console = new ConsoleView(model, new CommandController(model));
		model.addClass("myclass");
		
		// Edit class name with valid input
		assertEquals("edit class valid return code", 0, console.execCommand("edit class myclass newclass", myout));
		myout.flush();
		assertEquals("edit class valid output", "Changed class \'myclass\' to \'newclass\'.", scrubOut(bos.toString()));
		bos.reset();
		assertEquals("edit class valid return code 2", 0, console.execCommand("edit class newclass myclass", myout));
		myout.flush();
		assertEquals("edit class valid output 2", "Changed class \'newclass\' to \'myclass\'.", scrubOut(bos.toString()));
		bos.reset();
		
		// Edit class name with invalid input
		assertEquals("edit class invalid return code", 407, console.execCommand("edit class myclass 1&*29_newclass", myout));
		assertEquals("edit class invalid return code 2", 407, console.execCommand("edit class myclass _____newclass", myout));
		
		// Edit class name with invalid argument count
		assertEquals("edit class too many args 1", 102, console.execCommand("edit class myclass newclass another", myout));
		assertEquals("edit class too many args 2", 102, console.execCommand("edit class myclass newclass second third", myout));
		assertEquals("edit class too few args", 102, console.execCommand("edit class myclass", myout));
		
		// remove with no specifier
		assertEquals("edit", 102, console.execCommand("edit", myout));
		
		// Close the print stream
		myout.close();
	}
	
	/**
	 * Test the edit field output
	 * NOTE SOME OUTPUT RELIES ON FUNCTIONING MODEL
	 */
	@Test
	public void editFieldCommand() {
		// Create output stream for executing commands
		ByteArrayOutputStream bos = new ByteArrayOutputStream();
		PrintStream myout = new PrintStream(bos);
		
		// Setup console and model
		UMLClassManager model = new UMLClassManager();
		ConsoleView console = new ConsoleView(model, new CommandController(model));
		model.addClass("myclass");
		model.addFields("myclass", "int", "myfield");
		
		// Edit field name with valid input
		assertEquals("edit field valid return code", 0, console.execCommand("edit field myclass myfield mynewfield", myout));
		myout.flush();
		assertEquals("edit field valid output", "Changed field \'myfield\' to \'mynewfield\' in class \'myclass\'.", scrubOut(bos.toString()));
		bos.reset();
		assertEquals("edit field valid return code 2", 0, console.execCommand("edit field myclass mynewfield myfield", myout));
		myout.flush();
		assertEquals("edit field valid output 2", "Changed field \'mynewfield\' to \'myfield\' in class \'myclass\'.", scrubOut(bos.toString()));
		bos.reset();
		
		// Edit field name with invalid input
		assertEquals("edit field invalid return code", 409, console.execCommand("edit field myclass myfield 1%%%*asd29_newfield", myout));
		assertEquals("edit field invalid return code 2", 409, console.execCommand("edit field myclass myfield __%___newfield", myout));
		
		// Edit field name with invalid argument count
		assertEquals("edit field too many args 1", 102, console.execCommand("edit field myclass myfield newfield another", myout));
		assertEquals("edit field too many args 2", 102, console.execCommand("edit field myclass myfield newfield second third", myout));
		assertEquals("edit field too few args", 102, console.execCommand("edit field myclass", myout));
		assertEquals("edit field too few args 2", 102, console.execCommand("edit field myclass myfield", myout));
		
		// remove with no specifier
		assertEquals("edit", 102, console.execCommand("edit", myout));
		
		// Close the print stream
		myout.close();
	}
	
	@Test
	public void editRelationshipCommand() {
		// Create output stream for executing commands
		ByteArrayOutputStream bos = new ByteArrayOutputStream();
		PrintStream myout = new PrintStream(bos);
		
		// Setup console and model
		UMLClassManager model = new UMLClassManager();
		ConsoleView console = new ConsoleView(model, new CommandController(model));
		model.addClass("myclass");
		model.addClass("another");
		model.addClass("third");
		model.addRelationship("myclass", "aggregation", "another");
		model.addRelationship("another", "composition", "third");
				
		//Edit relationships with valid input 
		assertEquals("edit relationship with valid return code", 0, console.execCommand("edit relationship myclass aggregation another composition", myout));
		myout.flush();
		assertEquals("edit relationship with valid return message", "Changed relationship from class \'myclass\' to class \'another\' of type \'aggregation\' to type \'composition\'.", scrubOut(bos.toString()));
		bos.reset();
		assertEquals("edit relationship with valid return code again", 0, console.execCommand("edit relationship another composition third realization", myout));
		myout.flush();
		assertEquals("edit relationship with valid return message", "Changed relationship from class \'another\' to class \'third\' of type \'composition\' to type \'realization\'.", scrubOut(bos.toString()));
		bos.reset();
		
		//Edit relationships with invalid input
		assertEquals("edit relationship with no exisiting relationship", 108, console.execCommand("edit relationship myclass inheritance another composition", myout));
		myout.flush();
		
		
	}
	/**
	 * Test the edit method output
	 * NOTE SOME OUTPUT RELIES ON FUNCTIONING MODEL
	 */
	@Test
	public void editMethodCommand() {
		// Create output stream for executing commands
		ByteArrayOutputStream bos = new ByteArrayOutputStream();
		PrintStream myout = new PrintStream(bos);
		
		// Setup console and model
		UMLClassManager model = new UMLClassManager();
		ConsoleView console = new ConsoleView(model, new CommandController(model));
		model.addClass("myclass");
		model.addMethods("myclass", "int", "mymethod", "param");
		
		// Edit method name with valid input
		assertEquals("edit method valid return code", 0, console.execCommand("edit method myclass mymethod mynewmethod param", myout));
		myout.flush();
		assertEquals("edit method valid output", "Changed method \'mymethod\' from \'myclass\' to \'mynewmethod\'.", scrubOut(bos.toString()));
		bos.reset();
		assertEquals("edit method valid return code 2", 0, console.execCommand("edit method myclass mynewmethod mymethod param", myout));
		myout.flush();
		assertEquals("edit method valid output 2", "Changed method \'mynewmethod\' from \'myclass\' to \'mymethod\'.", scrubOut(bos.toString()));
		bos.reset();
		
		// Edit method name with invalid input
		assertEquals("edit method invalid return code", 408, console.execCommand("edit method myclass mymethod 1%%%*asd29_newmethod", myout));
		assertEquals("edit method invalid return code 2", 408, console.execCommand("edit method myclass mymethod __%___newmethod", myout));
		
		// Edit method name with invalid argument count
		assertEquals("edit method too few args", 102, console.execCommand("edit method myclass", myout));
		assertEquals("edit method too few args 2", 102, console.execCommand("edit method myclass mymethod", myout));
		
		// remove with no specifier
		assertEquals("edit", 102, console.execCommand("edit", myout));
		
		// Close the print stream
		myout.close();
	}
	
	/**
	 * Test list command outputs
	 */
	@Test
	public void listCommand() {
		// Create output stream for executing commands
		ByteArrayOutputStream bos = new ByteArrayOutputStream();
		PrintStream myout = new PrintStream(bos);
		
		// Setup console and model
		UMLClassManager model = new UMLClassManager();
		ConsoleView console = new ConsoleView(model, new CommandController(model));
		
		// List classes with valid input
		assertEquals("list classes valid return code", 0, console.execCommand("list classes", myout));
		myout.flush();
		assertEquals("list classes valid output", "There are no classes to display." + System.lineSeparator(), bos.toString());
		bos.reset();
		model.addClass("myclass");
		assertEquals("list classes valid return code 2", 0, console.execCommand("list classes myclass", myout));
		myout.flush();
		assertEquals("list classes valid output 2", "+-------+" + System.lineSeparator() + "|myclass|"+ System.lineSeparator() +"+-------+" + System.lineSeparator(), bos.toString());
		bos.reset();
		model.addClass("myclass2");
		assertEquals("list classes valid return code 3", 0, console.execCommand("list classes", myout));
		myout.flush();
		assertEquals("list classes valid output 3", "+-------+" + System.lineSeparator() + "|myclass|" + System.lineSeparator() + "+-------+"+ System.lineSeparator()
		+ System.lineSeparator() + "+--------+" + System.lineSeparator() + "|myclass2|" + System.lineSeparator() + "+--------+" + System.lineSeparator() + System.lineSeparator(), bos.toString());
		bos.reset();
		
		// List classes with invalid input
		assertEquals("list classes invalid return code", 109, console.execCommand("list classes class", myout));
		myout.flush();
		bos.reset();
		
		// List relationships with valid input
		assertEquals("list relationships valid return code", 0, console.execCommand("list relationships", myout));
		myout.flush();
		assertEquals("list relationships valid output", "There are no relationships to display.", bos.toString());
		bos.reset();
		model.addRelationship("myclass", "aggregation", "myclass2");
		assertEquals("list relationships valid return code 2", 0, console.execCommand("list relationships", myout));
		myout.flush();
		assertEquals("list relationships valid output 2", "+-------+"  + System.lineSeparator() + "|myclass|" + System.lineSeparator() + "+-------+" + System.lineSeparator() + System.lineSeparator()
		+ " |" + System.lineSeparator()	+ " |" + System.lineSeparator()	+ " |" + System.lineSeparator()	+ "< >" + System.lineSeparator()
		+ System.lineSeparator() +	"+--------+" + System.lineSeparator() +	"|myclass2|" + System.lineSeparator() +	"+--------+"
		+ System.lineSeparator() + System.lineSeparator(), bos.toString());
		bos.reset();
		model.addClass("class3");
		model.addRelationship("myclass", "composition", "myclass3");
		assertEquals("list relationships valid return code 3", 0, console.execCommand("list relationships myclass", myout));
		myout.flush();
		assertEquals("list relationships valid output 3", "+-------+"  + System.lineSeparator() + "|myclass|" + System.lineSeparator() + "+-------+" + System.lineSeparator() + System.lineSeparator()
		+ " |" + System.lineSeparator()	+ " |" + System.lineSeparator()	+ " |" + System.lineSeparator()	+ "< >" + System.lineSeparator()
		+ System.lineSeparator() +	"+--------+" + System.lineSeparator() +	"|myclass2|" + System.lineSeparator() +	"+--------+"
		+ System.lineSeparator() + System.lineSeparator(), bos.toString());
		bos.reset();
		
		//recursive relationship
		model.addClass("recursive");
		model.addFields("recursive", "double", "fives");
		model.addMethods("recursive", "boolean", "m", "isset");
		model.addRelationship("recursive", "aggregation", "recursive");
		assertEquals("list relationships valid return code 4", 0, console.execCommand("list relationships recursive", myout));
		myout.flush();
		assertEquals("recursive connects to self","+----------------+" + System.lineSeparator() + "|   recursive    |" + System.lineSeparator()
		+ "|  double fives  |" + System.lineSeparator() + "|boolean m(isset)|" + System.lineSeparator()
		+ "+----------------+" + System.lineSeparator() + System.lineSeparator() + " |" + System.lineSeparator() + " |" + System.lineSeparator()
		+ " |" + System.lineSeparator() + "< >" + System.lineSeparator() + System.lineSeparator()
		+ "+----------------+" + System.lineSeparator() + "|   recursive    |" + System.lineSeparator()
		+ "|  double fives  |" + System.lineSeparator() + "|boolean m(isset)|" + System.lineSeparator()
		+ "+----------------+" + System.lineSeparator() + System.lineSeparator(), bos.toString());
		bos.reset();
		
		// List relationships with invalid input
		assertEquals("list relationships invalid return code", 109, console.execCommand("list relationships class", myout));

		// List with invalid argument count
		assertEquals("List too few args", 102, console.execCommand("list", myout));
		
		// Close the print stream
		myout.close();
	}
	
	/**
	 * Test help command output
	 */
	@Test
	public void helpCommand() {
		// Create output stream for executing commands
		ByteArrayOutputStream bos = new ByteArrayOutputStream();
		PrintStream myout = new PrintStream(bos);
		
		// Setup console and model
		UMLClassManager model = new UMLClassManager();
		ConsoleView console = new ConsoleView(model, new CommandController(model));
		model.addClass("myclass");
		model.addMethods("myclass", "int", "mymethod", "param");
		
		// Help command with valid input
		assertEquals("help valid return code", 0, console.execCommand("help", myout));
		myout.flush();
		assertEquals("help valid output", System.lineSeparator() + "VALID COMMANDS"+System.lineSeparator() + "---------------------------------" + System.lineSeparator()
		+ "help: Prints out a list of valid commands with descriptions for each command." + System.lineSeparator() + System.lineSeparator()
		+ "Typing help <command> describes a specific command." + System.lineSeparator() + System.lineSeparator()
		+ "add: Can add class with:" + System.lineSeparator() + System.lineSeparator()
		+ "add <class_name>." + System.lineSeparator() + System.lineSeparator()
		+ "Also adds fields and methods to specified class in the form:" + System.lineSeparator() + System.lineSeparator()
		+ "add <field/method> <class_name> <type/return_type> <field/method_name> <parameter_list>(for methods)." + System.lineSeparator() + System.lineSeparator()
		+ "Add relationship in the form:" + System.lineSeparator() + System.lineSeparator()
		+ "add <relationship> <class_name1> <relationship_type> <class_name2>." + System.lineSeparator() + System.lineSeparator()
		+ "edit: Edit classes with:" + System.lineSeparator() + System.lineSeparator()
		+ "edit class <old_class_name> <new_class_name>." + System.lineSeparator() + System.lineSeparator()
		+ "Edit fields and methods with:" + System.lineSeparator() + System.lineSeparator()
		+ "edit <field/method> <source_class> <old_name> <new_name> <parameter_list>(for method)." + System.lineSeparator() + System.lineSeparator()
		+ "remove: Can remove class with:" + System.lineSeparator() + System.lineSeparator()
		+ "remove <class_name>." + System.lineSeparator() + System.lineSeparator()
		+ "Also removes fields and methods from specified class in the form:" + System.lineSeparator() + System.lineSeparator()
		+ "remove <field/method> <class_name> <field/method_name> <parameter_list>(for methods)." + System.lineSeparator() + System.lineSeparator()
		+ "Remove relationships in the form:" + System.lineSeparator() + System.lineSeparator()
		+ "remove <relationship> <class_name1> <relationship_type> <class_name2>." + System.lineSeparator() + System.lineSeparator()
		+ "exit: Quit the program." + System.lineSeparator() + System.lineSeparator()
		+ "quit: Quit the program." + System.lineSeparator() + System.lineSeparator()
		+ "save: Save the current state of the UML diagram.  If a file has not been set it will prompt the user." + System.lineSeparator() + System.lineSeparator()
		+ "load <file_path>: Load the given file into the UML editor." + System.lineSeparator() + System.lineSeparator()
		+ "list: Can list all classes with:" + System.lineSeparator() + System.lineSeparator()
		+ "list classes" + System.lineSeparator() + System.lineSeparator()
		+ "or specific class with:" + System.lineSeparator() + System.lineSeparator()
		+ "list classes <class_name>." + System.lineSeparator() + System.lineSeparator()
		+ "These lists take the form of boxes with the class name and its associated attributes inside." + System.lineSeparator() + System.lineSeparator() 
		+ "List all relationships with:" + System.lineSeparator() + System.lineSeparator()
		+ "list relationships" + System.lineSeparator() + System.lineSeparator()
		+ "or" + System.lineSeparator() + System.lineSeparator()
		+ "list relationships <class_name>." + System.lineSeparator() + System.lineSeparator()
		+ "Takes the form boxes for each class containing their associated attributes with a delimiter for relationship type between classes." + System.lineSeparator() + System.lineSeparator(), bos.toString());
		bos.reset();
		assertEquals("help valid return code 2", 0, console.execCommand("help add", myout));
		myout.flush();
		assertEquals("help valid output 2",  System.lineSeparator() + "add: Can add class with:" + System.lineSeparator() + System.lineSeparator()
		+ "add <class_name>." + System.lineSeparator()+ System.lineSeparator()
		+ "Also adds fields and methods to specified class in the form:" + System.lineSeparator()+ System.lineSeparator()
		+ "add <field/method> <class_name> <type/return_type> <field/method_name> <parameter_list>(for methods)." + System.lineSeparator()+ System.lineSeparator()
		+ "Add relationship in the form:" + System.lineSeparator()+ System.lineSeparator()
		+ "add <relationship> <class_name1> <relationship_type> <class_name2>." + System.lineSeparator()+ System.lineSeparator(), bos.toString());
		bos.reset();
		
		// Help with invalid input
		assertEquals("help invalid return code 2", 104, console.execCommand("h", myout));
		
		// Help with invalid argument count
		assertEquals("help too many args", 102, console.execCommand("help add class", myout));
		
		// Close the print stream
		myout.close();
	}
	
	/**
	 * Test the saving of the model for console
	 */
	@Test
	public void save() {
		// Create output stream for executing commands
		ByteArrayOutputStream bos = new ByteArrayOutputStream();
		PrintStream myout = new PrintStream(bos);
				
		UMLClassManager model = new UMLClassManager();
		ConsoleView console = new ConsoleView(model, new CommandController(model));
		
		// Add some stuff to the model just cause
		console.execCommand("add class myclass", myout);
		console.execCommand("add field myclass int myInt", myout);
		
		// Test save for console
		File expectedFile = new File("uml-output.json");
		expectedFile.deleteOnExit();
		console.execCommand("save uml-output.json", myout);
		// Make sure the file exists
		assertTrue("File created", expectedFile.exists());
	}
	
	/**
	 * Test the loading of the model for console
	 */
	@Test
	public void load() {
		// Create output stream for executing commands
		ByteArrayOutputStream bos = new ByteArrayOutputStream();
		PrintStream myout = new PrintStream(bos);
				
		UMLClassManager model = new UMLClassManager();
		ConsoleView console = new ConsoleView(model, new CommandController(model));
		
		// Add some stuff to the model
		console.execCommand("add class myclass", myout);
		console.execCommand("add field myclass int myInt", myout);
		
		// Save it so we have a file to load from
		File expectedFile = new File("uml-output.json");
		expectedFile.deleteOnExit();
		console.execCommand("save uml-output.json", myout);
		// Make sure the file exists
		assertTrue("File created", expectedFile.exists());
		
		// Create a second instance
		ByteArrayOutputStream bos2 = new ByteArrayOutputStream();
		PrintStream myout2 = new PrintStream(bos2);
				
		UMLClassManager model2 = new UMLClassManager();
		ConsoleView console2 = new ConsoleView(model2, new CommandController(model2));

		
		// Load from file
		console2.execCommand("load uml-output.json", myout2);
		// Check if it was applied
		assertTrue("Model has the loaded class", model2.getClass("myclass") != null);
		assertTrue("Model has the loaded class's field", model2.getClass("myclass").hasField("myInt"));
	}
	
	/**
	 * Helper function to scrub the output of System.out.println() for comparisons
	 * and testing of output.
	 * @param str - String to scrub
	 * @return scrubbed string
	 */
	private String scrubOut(String str) {
		return str.replaceAll("\n", "").replaceAll("\r", "");
	}
}
