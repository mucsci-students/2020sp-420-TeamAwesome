// System imports
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

// Local imports
import views.ConsoleView;

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
		ConsoleView console = new ConsoleView();
		assertTrue("Console not null", console.getScanner() != null);
		assertTrue("Commands not null", console.getValidCommands() != null);
		assertTrue("Commands not empty", !(console.getValidCommands().isEmpty()));
		assertTrue("Close console", console.closeConsole() == 0);
	}
	
	/**
	 * Test empty inputs
	 */
	@Test
	public void emptyInput() {
		ConsoleView console = new ConsoleView();
		assertEquals("Empty string", 101, console.execCommand(""));
		assertEquals("Empty string 2", 101, console.execCommand("   "));
		assertEquals("Empty string 3", 101, console.execCommand("\t"));
		assertEquals("Empty string 4", 101, console.execCommand("\r"));
	}
	
	/**
	 * Test invalid commands
	 */
	@Test
	public void invalidInput() {
		ConsoleView console = new ConsoleView();
		assertEquals("Invalid command 1", 104, console.execCommand("this isnt real"));
		assertEquals("Invalid command 2", 104, console.execCommand("fake command"));
		assertEquals("Invalid command 3", 104, console.execCommand("jibbersih"));
	}
	
	/**
	 * Test the add command output
	 * NOTE SOME OUTPUT RELIES ON FUNCTIONING MODEL
	 */
	@Test
	public void addClassCommand() {
		// Set System.out/err to catch output text to test
		final ByteArrayOutputStream newOut = new ByteArrayOutputStream();
		final ByteArrayOutputStream newErr = new ByteArrayOutputStream();
		final PrintStream oldOut = System.out;
		final PrintStream oldErr = System.out;
		System.setOut(new PrintStream(newOut));
		System.setErr(new PrintStream(newErr));
		
		ConsoleView console = new ConsoleView();
		
		// Add class with valid input
		assertEquals("add class valid return code", 0, console.execCommand("add class normal"));
		assertEquals("add class valid error stream", "", scrubOut(newErr.toString()));
		assertEquals("add class valid out stream", "Added class \'normal\'.", scrubOut(newOut.toString()));
		
		// Add class with invalid input
		assertNotEquals("add class invalid return code", 0, console.execCommand("add class not*re(la"));
		assertEquals("add class invalid return code 2", 407, console.execCommand("add class 38&32H3"));
		
		// Add class with wrong number of args
		assertEquals("add class too many args", 102, console.execCommand("add class myclass another"));
		assertEquals("add class too many args 2", 102, console.execCommand("add class myclass another woa"));
		assertEquals("add class too few args", 102, console.execCommand("add class"));
		
		// Add with no specifier
		assertEquals("add with no specifier", 102, console.execCommand("add"));
		
		// Reset the old output streams
		System.setOut(oldOut);
		System.setErr(oldErr);
	}
	
	/**
	 * Test the add field output
	 * NOTE SOME OUTPUT RELIES ON FUNCTIONING MODEL
	 */
	@Test
	public void addFieldCommand() {
		// Set System.out/err to catch output text to test
		final ByteArrayOutputStream newOut = new ByteArrayOutputStream();
		final ByteArrayOutputStream newErr = new ByteArrayOutputStream();
		final PrintStream oldOut = System.out;
		final PrintStream oldErr = System.out;
		System.setOut(new PrintStream(newOut));
		System.setErr(new PrintStream(newErr));
		
		ConsoleView console = new ConsoleView();
		
		// Add field with valid input
		console.execCommand("add class myclass");
		newOut.reset();
		newErr.reset();
		assertEquals("add field valid return code", 0, console.execCommand("add field myclass mytype myfield"));
		assertEquals("add field valid error stream", "", scrubOut(newErr.toString()));
		assertEquals("add field valid out stream", "Added field \'myfield\' to class \'myclass\' of type \'mytype\'.", scrubOut(newOut.toString()));
		newOut.reset();
		newErr.reset();
		
		// Add field with invalid input
		assertEquals("add field invalid return code", 203, console.execCommand("add field myclass not*re(la fielda"));
		assertEquals("add field invalid return code 2", 203, console.execCommand("add field myclass 38&32H3 fieldt"));
		
		// Add field with wrong number of args
		assertEquals("add field too many args", 102, console.execCommand("add field myclass another another field"));
		assertEquals("add field too many args 2", 102, console.execCommand("add field myclass another crazy crazy field"));
		assertEquals("add field too few args", 102, console.execCommand("add field myclass"));
		assertEquals("add field too few args", 102, console.execCommand("add field"));
		
		// Add with no specifier
		assertEquals("add with no specifier", 102, console.execCommand("add"));
		
		// Reset the old output streams
		System.setOut(oldOut);
		System.setErr(oldErr);
	}
	
	/**
	 * Test the add method output
	 * NOTE SOME OUTPUT RELIES ON FUNCTIONING MODEL
	 */
	@Test
	public void addMethodCommand() {
		// Set System.out/err to catch output text to test
		final ByteArrayOutputStream newOut = new ByteArrayOutputStream();
		final ByteArrayOutputStream newErr = new ByteArrayOutputStream();
		final PrintStream oldOut = System.out;
		final PrintStream oldErr = System.out;
		System.setOut(new PrintStream(newOut));
		System.setErr(new PrintStream(newErr));
		
		ConsoleView console = new ConsoleView();
		
		// Add method with valid input
		console.execCommand("add class myclass");
		newOut.reset();
		newErr.reset();
		assertEquals("add method valid return code", 0, console.execCommand("add method myclass int mymethod param"));
		assertEquals("add method valid error stream", "", scrubOut(newErr.toString()));
		assertEquals("add method valid out stream", "Added method \'mymethod\' which accepts \'param\' with returnType: \'int\' to class \'myclass\'.", scrubOut(newOut.toString()));
		
		// Add method with invalid input
		assertEquals("add method invalid return code", 408, console.execCommand("add method myclass int not*re(la"));
		assertEquals("add method invalid return code 2", 203, console.execCommand("add method myclass 38&32H3 realname params"));
		
		// Add method with wrong number of args
		assertEquals("add method too few args", 102, console.execCommand("add method myclass"));
		assertEquals("add method too few args", 102, console.execCommand("add method"));
		
		// Add with no specifier
		assertEquals("add with no specifier", 102, console.execCommand("add"));
		
		// Reset the old output streams
		System.setOut(oldOut);
		System.setErr(oldErr);
	}
	
	/**
	 * Test the add relationship output
	 * NOTE SOME OUTPUT RELIES ON FUNCTIONING MODEL
	 */
	@Test
	public void addRelationshipCommand() {
		// Set System.out/err to catch output text to test
		final ByteArrayOutputStream newOut = new ByteArrayOutputStream();
		final ByteArrayOutputStream newErr = new ByteArrayOutputStream();
		final PrintStream oldOut = System.out;
		final PrintStream oldErr = System.out;
		System.setOut(new PrintStream(newOut));
		System.setErr(new PrintStream(newErr));
		
		ConsoleView console = new ConsoleView();
		console.execCommand("add class myclass");
		console.execCommand("add class another");
		console.execCommand("add class third");
		newOut.reset();
		newErr.reset();
		
		// Add relationships with valid input
		assertEquals("add relationship valid return code", 0, console.execCommand("add relationship myclass aggregation another"));
		assertEquals("add relationship valid output", "Added aggregation relationship from \'myclass\' to \'another\'.", scrubOut(newOut.toString()));
		assertEquals("add relationship valid error stream", "", newErr.toString());
		newOut.reset();
		newErr.reset();
		assertEquals("add relationship 2 valid return code", 0, console.execCommand("add relationship another composition third"));
		assertEquals("add relationship 2 valid output", "Added composition relationship from \'another\' to \'third\'.", scrubOut(newOut.toString()));
		assertEquals("add relationship 2 valid error stream", "", newErr.toString());
		newOut.reset();
		newErr.reset();
		
		// Add relationships with invalid input
		// ERROR CODE WILL CHANGE ONCE THE FUNCTIONALITY HAS BEEN WRITTEN AND A RETURN CODE HAS BEEN ASSIGNED
		assertEquals("add relationship class not exist return code", 107, console.execCommand("add relationship notarealclass aggregation another"));
		assertEquals("add relationship class not exist return code", 107, console.execCommand("add relationship notarealclass aggregation another"));
		assertEquals("add relationship type not exist return code", 202, console.execCommand("add relationship myclass notreal another"));
		
		// Add relationships with bad arg count
		assertEquals("Add relationship with too many args", 102, console.execCommand("add relationship myclass aggregation another morestuff yay"));
		assertEquals("Add relationship with too many args 2", 102, console.execCommand("add relationship myclass inheritance another third"));
		assertEquals("Add relationship with too few args", 102, console.execCommand("add relationship myclass aggregation"));
		assertEquals("Add relationship with too few args 2", 102, console.execCommand("add relationship myclass"));
		assertEquals("Add relationship no args", 102, console.execCommand("add relationship"));
		
		// Add with no specifier
		assertEquals("Add with no specifier", 102, console.execCommand("add"));
		
		// Reset the old output streams
		System.setOut(oldOut);
		System.setErr(oldErr);
	}
	
	/**
	 * Test the remove command output
	 * NOTE SOME OUTPUT RELIES ON FUNCTIONING MODEL
	 */
	@Test
	public void removeClassCommand() {
		// Set System.out/err to catch output text to test
		final ByteArrayOutputStream newOut = new ByteArrayOutputStream();
		final ByteArrayOutputStream newErr = new ByteArrayOutputStream();
		final PrintStream oldOut = System.out;
		final PrintStream oldErr = System.out;
		System.setOut(new PrintStream(newOut));
		System.setErr(new PrintStream(newErr));
		
		ConsoleView console = new ConsoleView();
		
		// Remove class with valid input
		console.execCommand("add class normal");
		newOut.reset();
		newErr.reset();
		assertEquals("remove class valid return code", 0, console.execCommand("remove class normal"));
		assertEquals("remove class valid error stream", "", scrubOut(newErr.toString()));
		assertEquals("remove class valid out stream", "Removed class \'normal\'.", scrubOut(newOut.toString()));
		
		console.execCommand("add class myclass");
		
		// remove class with invalid input
		assertEquals("remove class invalid return code", 201, console.execCommand("remove class not*re(la"));
		assertEquals("remove class invalid return code 2", 201, console.execCommand("remove class 38&32H3"));
		
		// remove class with wrong number of args
		assertEquals("remove class too many args", 102, console.execCommand("remove class myclass another"));
		assertEquals("remove class too many args 2", 102, console.execCommand("remove class myclass another woa"));
		assertEquals("remove class too few args", 102, console.execCommand("remove class"));
		
		// remove with no specifier
		assertEquals("remove with no specifier", 102, console.execCommand("remove"));
		
		// Reset the old output streams
		System.setOut(oldOut);
		System.setErr(oldErr);
	}
	
	/**
	 * Test the remove field output
	 * NOTE SOME OUTPUT RELIES ON FUNCTIONING MODEL
	 */
	@Test
	public void removeFieldCommand() {
		// Set System.out/err to catch output text to test
		final ByteArrayOutputStream newOut = new ByteArrayOutputStream();
		final ByteArrayOutputStream newErr = new ByteArrayOutputStream();
		final PrintStream oldOut = System.out;
		final PrintStream oldErr = System.out;
		System.setOut(new PrintStream(newOut));
		System.setErr(new PrintStream(newErr));
		
		ConsoleView console = new ConsoleView();
		console.execCommand("add class myclass");
		console.execCommand("add field myclass typet myfield");
		newOut.reset();
		newErr.reset();
		
		// Remove field with valid input
		assertEquals("remove field valid return code", 0, console.execCommand("remove field myclass myfield"));
		assertEquals("remove field valid error stream", "", scrubOut(newErr.toString()));
		assertEquals("remove field valid out stream", "Removed field \'myfield\' from class \'myclass\'.", scrubOut(newOut.toString()));
		
		// Remove field with invalid input
		assertEquals("remove field invalid return code", 405, console.execCommand("remove field myclass not*re(la"));
		assertEquals("remove field invalid return code 2", 405, console.execCommand("remove field myclass 38&32H3"));
		
		// Remove field with wrong number of args
		assertEquals("remove field too many args", 102, console.execCommand("remove field myclass another field"));
		assertEquals("remove field too many args 2", 102, console.execCommand("remove field myclass another crazy field"));
		assertEquals("remove field too few args", 102, console.execCommand("remove field myclass"));
		assertEquals("remove field too few args", 102, console.execCommand("remove field"));
		
		// Remove with no specifier
		assertEquals("add with no specifier", 102, console.execCommand("remove"));
		
		// Reset the old output streams
		System.setOut(oldOut);
		System.setErr(oldErr);
	}
	
	/**
	 * Test the remove method output
	 * NOTE SOME OUTPUT RELIES ON FUNCTIONING MODEL
	 */
	@Test
	public void removeMethodCommand() {
		// Set System.out/err to catch output text to test
		final ByteArrayOutputStream newOut = new ByteArrayOutputStream();
		final ByteArrayOutputStream newErr = new ByteArrayOutputStream();
		final PrintStream oldOut = System.out;
		final PrintStream oldErr = System.out;
		System.setOut(new PrintStream(newOut));
		System.setErr(new PrintStream(newErr));
		
		ConsoleView console = new ConsoleView();
		console.execCommand("add class myclass");
		console.execCommand("add method myclass int mymethod param");
		newOut.reset();
		newErr.reset();
		
		// Remove method with valid input
		assertEquals("remove method valid return code", 0, console.execCommand("remove method myclass mymethod param"));
		assertEquals("remove method valid error stream", "", scrubOut(newErr.toString()));
		assertEquals("remove method valid out stream", "Removed method \'mymethod\' ( param ) from class \'myclass\'.", scrubOut(newOut.toString()));
		
		// Remove method with invalid input
		assertEquals("remove method invalid return code", 406, console.execCommand("remove method myclass int not*re(la"));
		assertEquals("remove method invalid return code 2", 403, console.execCommand("remove method notReal int mymethod"));
		
		// Remove method with wrong number of args
		assertEquals("remove method too few args", 102, console.execCommand("remove method myclass"));
		assertEquals("remove method too few args", 102, console.execCommand("remove method"));
		
		// Remove with no specifier
		assertEquals("add with no specifier", 102, console.execCommand("remove"));
		
		// Reset the old output streams
		System.setOut(oldOut);
		System.setErr(oldErr);
	}
	
	/**
	 * Test the remove relationship output
	 * NOTE SOME OUTPUT RELIES ON FUNCTIONING MODEL
	 */
	@Test
	public void removeRelationshipCommand() {
		// Set System.out/err to catch output text to test
		final ByteArrayOutputStream newOut = new ByteArrayOutputStream();
		final ByteArrayOutputStream newErr = new ByteArrayOutputStream();
		final PrintStream oldOut = System.out;
		final PrintStream oldErr = System.out;
		System.setOut(new PrintStream(newOut));
		System.setErr(new PrintStream(newErr));
		
		ConsoleView console = new ConsoleView();
		console.execCommand("add class myclass");
		console.execCommand("add class another");
		console.execCommand("add class third");
		console.execCommand("add relationship myclass aggregation another");
		console.execCommand("add relationship another composition third");
		newOut.reset();
		newErr.reset();
		
		// Remove relationships with valid input
		assertEquals("remove relationship valid return code", 0, console.execCommand("remove relationship myclass aggregation another"));
		assertEquals("remove relationship valid output", "Removed aggregation relationship between \'another\' and \'myclass\'.", scrubOut(newOut.toString()));
		assertEquals("remove relationship valid error stream", "", newErr.toString());
		newOut.reset();
		newErr.reset();
		assertEquals("remove relationship 2 valid return code", 0, console.execCommand("remove relationship another composition third"));
		assertEquals("remove relationship 2 valid output", "Removed composition relationship between \'third\' and \'another\'.", scrubOut(newOut.toString()));
		assertEquals("remove relationship 2 valid error stream", "", newErr.toString());
		newOut.reset();
		newErr.reset();
		
		// Remove relationships with invalid input
		// ERROR CODE WILL CHANGE ONCE THE FUNCTIONALITY HAS BEEN WRITTEN AND A RETURN CODE HAS BEEN ASSIGNED
		assertEquals("remove relationship class not exist return code", 107, console.execCommand("remove relationship notarealclass aggregation another"));
		assertEquals("remove relationship class not exist return code", 107, console.execCommand("remove relationship another aggregation notarealclass"));
		assertEquals("remove relationship type not exist return code", 108, console.execCommand("remove relationship myclass notreal another"));
		
		// Remove relationships with bad arg count
		assertEquals("remove relationship with too many args", 102, console.execCommand("remove relationship myclass aggregation another morestuff yay"));
		assertEquals("remove relationship with too many args 2", 102, console.execCommand("remove relationship myclass inheritance another third"));
		assertEquals("remove relationship with too few args", 102, console.execCommand("remove relationship myclass aggregation"));
		assertEquals("remove relationship with too few args 2", 102, console.execCommand("remove relationship myclass"));
		assertEquals("remove relationship no args", 102, console.execCommand("remove relationship"));
		
		// remove with no specifier
		assertEquals("remove", 102, console.execCommand("remove"));
		
		// Reset the old output streams
		System.setOut(oldOut);
		System.setErr(oldErr);
	}
	
	/**
	 * Test the edit class output
	 * NOTE SOME OUTPUT RELIES ON FUNCTIONING MODEL
	 */
	@Test
	public void editClassCommand() {
		// Set System.out/err to catch output text to test
		final ByteArrayOutputStream newOut = new ByteArrayOutputStream();
		final ByteArrayOutputStream newErr = new ByteArrayOutputStream();
		final PrintStream oldOut = System.out;
		final PrintStream oldErr = System.out;
		System.setOut(new PrintStream(newOut));
		System.setErr(new PrintStream(newErr));
		
		ConsoleView console = new ConsoleView();
		console.execCommand("add class myclass");
		newOut.reset();
		newErr.reset();
		
		// Edit class name with valid input
		assertEquals("edit class valid return code", 0, console.execCommand("edit class myclass newclass"));
		assertEquals("edit class valid output", "Changed class \'myclass\' to \'newclass\'.", scrubOut(newOut.toString()));
		assertEquals("edit class valid error stream", "", scrubOut(newErr.toString()));
		newOut.reset();
		newErr.reset();
		assertEquals("edit class valid return code 2", 0, console.execCommand("edit class newclass myclass"));
		assertEquals("edit class valid output 2", "Changed class \'newclass\' to \'myclass\'.", scrubOut(newOut.toString()));
		assertEquals("edit class valid error stream 2", "", scrubOut(newErr.toString()));
		
		// Edit class name with invalid input
		assertEquals("edit class invalid return code", 407, console.execCommand("edit class myclass 1&*29_newclass"));
		assertEquals("edit class invalid return code 2", 407, console.execCommand("edit class myclass _____newclass"));
		
		// Edit class name with invalid argument count
		assertEquals("edit class too many args 1", 102, console.execCommand("edit class myclass newclass another"));
		assertEquals("edit class too many args 2", 102, console.execCommand("edit class myclass newclass second third"));
		assertEquals("edit class too few args", 102, console.execCommand("edit class myclass"));
		
		// remove with no specifier
		assertEquals("edit", 102, console.execCommand("edit"));
		
		// Reset the old output streams
		System.setOut(oldOut);
		System.setErr(oldErr);
	}
	
	/**
	 * Test the edit field output
	 * NOTE SOME OUTPUT RELIES ON FUNCTIONING MODEL
	 */
	@Test
	public void editFieldCommand() {
		// Set System.out/err to catch output text to test
		final ByteArrayOutputStream newOut = new ByteArrayOutputStream();
		final ByteArrayOutputStream newErr = new ByteArrayOutputStream();
		final PrintStream oldOut = System.out;
		final PrintStream oldErr = System.out;
		System.setOut(new PrintStream(newOut));
		System.setErr(new PrintStream(newErr));
		
		ConsoleView console = new ConsoleView();
		console.execCommand("add class myclass");
		console.execCommand("add field myclass int myfield");
		newOut.reset();
		newErr.reset();
		
		// Edit field name with valid input
		assertEquals("edit field valid return code", 0, console.execCommand("edit field myclass myfield mynewfield"));
		assertEquals("edit field valid output", "Changed field \'myfield\' to \'mynewfield\' in class \'myclass\'.", scrubOut(newOut.toString()));
		assertEquals("edit field valid error stream", "", scrubOut(newErr.toString()));
		newOut.reset();
		newErr.reset();
		assertEquals("edit field valid return code 2", 0, console.execCommand("edit field myclass mynewfield myfield"));
		assertEquals("edit field valid output 2", "Changed field \'mynewfield\' to \'myfield\' in class \'myclass\'.", scrubOut(newOut.toString()));
		assertEquals("edit field valid error stream 2", "", scrubOut(newErr.toString()));
		
		// Edit field name with invalid input
		assertEquals("edit field invalid return code", 409, console.execCommand("edit field myclass myfield 1%%%*asd29_newfield"));
		assertEquals("edit field invalid return code 2", 409, console.execCommand("edit field myclass myfield __%___newfield"));
		
		// Edit field name with invalid argument count
		assertEquals("edit field too many args 1", 102, console.execCommand("edit field myclass myfield newfield another"));
		assertEquals("edit field too many args 2", 102, console.execCommand("edit field myclass myfield newfield second third"));
		assertEquals("edit field too few args", 102, console.execCommand("edit field myclass"));
		assertEquals("edit field too few args 2", 102, console.execCommand("edit field myclass myfield"));
		
		// remove with no specifier
		assertEquals("edit", 102, console.execCommand("edit"));
		
		// Reset the old output streams
		System.setOut(oldOut);
		System.setErr(oldErr);
	}
	
	/**
	 * Test the edit method output
	 * NOTE SOME OUTPUT RELIES ON FUNCTIONING MODEL
	 */
	@Test
	public void editMethodCommand() {
		// Set System.out/err to catch output text to test
		final ByteArrayOutputStream newOut = new ByteArrayOutputStream();
		final ByteArrayOutputStream newErr = new ByteArrayOutputStream();
		final PrintStream oldOut = System.out;
		final PrintStream oldErr = System.out;
		System.setOut(new PrintStream(newOut));
		System.setErr(new PrintStream(newErr));
		
		ConsoleView console = new ConsoleView();
		console.execCommand("add class myclass");
		console.execCommand("add method myclass int mymethod param");
		newOut.reset();
		newErr.reset();
		
		// Edit method name with valid input
		assertEquals("edit method valid return code", 0, console.execCommand("edit method myclass mymethod mynewmethod param"));
		assertEquals("edit method valid output", "Changed method \'mymethod\' from \'myclass\' to \'mynewmethod\'.", scrubOut(newOut.toString()));
		assertEquals("edit method valid error stream", "", scrubOut(newErr.toString()));
		newOut.reset();
		newErr.reset();
		assertEquals("edit method valid return code 2", 0, console.execCommand("edit method myclass mynewmethod mymethod param"));
		assertEquals("edit method valid output 2", "Changed method \'mynewmethod\' from \'myclass\' to \'mymethod\'.", scrubOut(newOut.toString()));
		assertEquals("edit method valid error stream 2", "", scrubOut(newErr.toString()));
		
		// Edit method name with invalid input
		assertEquals("edit method invalid return code", 408, console.execCommand("edit method myclass mymethod 1%%%*asd29_newmethod"));
		assertEquals("edit method invalid return code 2", 408, console.execCommand("edit method myclass mymethod __%___newmethod"));
		
		// Edit method name with invalid argument count
		assertEquals("edit method too few args", 102, console.execCommand("edit method myclass"));
		assertEquals("edit method too few args 2", 102, console.execCommand("edit method myclass mymethod"));
		
		// remove with no specifier
		assertEquals("edit", 102, console.execCommand("edit"));
		
		// Reset the old output streams
		System.setOut(oldOut);
		System.setErr(oldErr);
	}
	
	/**
	 * Test list command outputs
	 */
	@Test
	public void listCommand() {
		//list classes & relationships. 2-3 args
		// Set System.out/err to catch output text to test
		final ByteArrayOutputStream newOut = new ByteArrayOutputStream();
		final ByteArrayOutputStream newErr = new ByteArrayOutputStream();
		final PrintStream oldOut = System.out;
		final PrintStream oldErr = System.out;
		System.setOut(new PrintStream(newOut));
		System.setErr(new PrintStream(newErr));
		
		ConsoleView console = new ConsoleView();
		newOut.reset();
		newErr.reset();
		
		// List classes with valid input
		assertEquals("list classes valid return code", 0, console.execCommand("list classes"));
		assertEquals("list classes valid output", "", scrubOut(newOut.toString()));
		assertEquals("list classes valid error stream", "", scrubOut(newErr.toString()));
		newOut.reset();
		newErr.reset();
		console.execCommand("add class myclass");
		assertEquals("list classes valid return code 2", 0, console.execCommand("list classes myclass"));
		assertEquals("list classes valid output 2", "-----------\n| myclass |\n-----------", scrubOut(newOut.toString()));
		assertEquals("list classes valid error stream 2", "", scrubOut(newErr.toString()));
		newOut.reset();
		newErr.reset();
		console.execCommand("add class myclass2");
		assertEquals("list classes valid return code 3", 0, console.execCommand("list classes myclass myclass2"));
		assertEquals("list classes valid output 3", "-----------          ------------\n| myclass |          | myclass2 |\n-----------          ------------", scrubOut(newOut.toString()));
		assertEquals("list classes valid error stream 3", "", scrubOut(newErr.toString()));
		newOut.reset();
		newErr.reset();
		
		// List classes with invalid input
		assertEquals("list classes invalid return code", 109, console.execCommand("list classes class"));
		
		// List relationships with valid input
		assertEquals("list relationships valid return code", 0, console.execCommand("list relationships"));
		assertEquals("list relationships valid output", "", scrubOut(newOut.toString()));
		assertEquals("list relationships valid error stream", "", scrubOut(newErr.toString()));
		newOut.reset();
		newErr.reset();
		console.execCommand("add relationship myclass aggregation myclass2");
		assertEquals("list relationships valid return code 2", 0, console.execCommand("list relationships"));
		assertEquals("list relationships valid output 2", "-----------          ------------\n| myclass | ------<> | myclass2 |\n-----------          ------------", scrubOut(newOut.toString()));
		assertEquals("list relationships valid error stream 2", "", scrubOut(newErr.toString()));
		newOut.reset();
		newErr.reset();
		console.execCommand("add class class3");
		console.execCommand("add relationship myclass composition myclass3");
		assertEquals("list relationships valid return code 3", 0, console.execCommand("list relationships myclass"));
		assertEquals("list relationships valid output 3", "-----------          ------------\n| myclass | ------<> | myclass2 |\n-----------          "
		+ "------------\n-----------          ------------\n| myclass | -----<=> | myclass3 |\n-----------          ------------", scrubOut(newOut.toString()));
		assertEquals("list relationships valid error stream 3", "", scrubOut(newErr.toString()));
		
		// List relationships with invalid input
		assertEquals("list relationships invalid return code", 109, console.execCommand("list relationships class"));

		// List with invalid argument count
		assertEquals("List too few args", 102, console.execCommand("List"));
		
		// Reset the old output streams
		System.setOut(oldOut);
		System.setErr(oldErr);
	}
	
	/**
	 * Test help command output
	 */
	@Test
	public void helpCommand() {
		//1-2 args
		// Set System.out/err to catch output text to test
		final ByteArrayOutputStream newOut = new ByteArrayOutputStream();
		final ByteArrayOutputStream newErr = new ByteArrayOutputStream();
		final PrintStream oldOut = System.out;
		final PrintStream oldErr = System.out;
		System.setOut(new PrintStream(newOut));
		System.setErr(new PrintStream(newErr));
		
		ConsoleView console = new ConsoleView();
		console.execCommand("add class myclass");
		console.execCommand("add method myclass int mymethod param");
		newOut.reset();
		newErr.reset();
		
		// Help command with valid input
		assertEquals("help valid return code", 0, console.execCommand("help"));
		assertEquals("help valid output", System.lineSeparator() + "VALID COMMANDS"+System.lineSeparator() + "---------------------------------"+ System.lineSeparator()
		+ "help: Prints out a list of valid commands with descriptions for each command.  Typing help <command> describes a specific command."+ System.lineSeparator()
		+ "add: Can add class with add <class_name>.  Also adds fields and methods to specified class in the form add <field/method> <class_name> <type/return_type> <field/method_name> <parameter_list>(for methods)."+ System.lineSeparator()
		+ "     Add relationship in the form add <relationship> <class_name1> <relationship_type> <class_name2>."+ System.lineSeparator()
		+ "edit: Edit classes with edit class <old_class_name> <new_class_name>.  Edit fields and methods with edit <field/method> <source_class> <old_name> <new_name> <parameter_list>(for method)."+ System.lineSeparator()
		+ "remove: Can remove class with remove <class_name>.  Also removes fields and methods from specified class in the form remove <field/method> <class_name> <field/method_name> <parameter_list>(for methods)."+ System.lineSeparator()
		+ "\tRemove relationship in the form remove <relationship> <class_name1> <relationship_type> <class_name2>."+ System.lineSeparator()
		+ "exit: Quit the program."+ System.lineSeparator()
		+ "quit: Quit the program."+ System.lineSeparator()
		+ "save: Save the current state of the UML diagram.  If a file has not been set it will prompt the user."+ System.lineSeparator()
		+ "load <file_path>: Load the given file into the UML editor."+ System.lineSeparator()
		+ "list: Can list all classes with list classes or specific class with list classes <class_name>.  These lists take the form of boxes with the class name and its associated attributes inside."+ System.lineSeparator() 
		+ "      List all relationships with list relationships or list relationships <class_name>.  Takes the form boxes for each class containing their associated attributes with a delimiter for relationship type between classes."+ System.lineSeparator(), newOut.toString());
		assertEquals("help valid error stream", "", scrubOut(newErr.toString()));
		newOut.reset();
		newErr.reset();
		assertEquals("help valid return code 2", 0, console.execCommand("help add"));
		assertEquals("help valid output 2", System.lineSeparator() + "add: Can add class with add <class_name>.  Also adds fields and methods to specified class in the form add <field/method> <class_name> <type/return_type> <field/method_name> <parameter_list>(for methods)." + System.lineSeparator()
		+ "     Add relationship in the form add <relationship> <class_name1> <relationship_type> <class_name2>.", newOut.toString());
		assertEquals("help valid error stream 2", "", scrubOut(newErr.toString()));
		
		// Help with invalid input
		assertEquals("help invalid return code 2", 104, console.execCommand("h"));
		
		// Help with invalid argument count
		assertEquals("help too many args", 102, console.execCommand("help add class"));
		newOut.reset();
		newErr.reset();
		
		// Reset the old output streams
		System.setOut(oldOut);
		System.setErr(oldErr);
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
