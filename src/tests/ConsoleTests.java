// Package name
package tests;

// System imports
import org.junit.Test;

import main.ErrorHandler;

import static org.junit.Assert.assertEquals;
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
public class ConsoleTests {
	/**
	 * Create environment and don't do anything to it
	 */
	@Test
	public void baseInstance() {
		ConsoleView console = new ConsoleView();
		assertTrue("Console not null", console.getScanner() != null);
		assertTrue("Commands not null", console.getValidCommands() != null);
		assertTrue("Commands not empty", console.getValidCommands().length != 0);
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
		assertEquals("add field valid return code", 0, console.execCommand("add field myclass myfield"));
		assertEquals("add field valid error stream", "", scrubOut(newErr.toString()));
		assertEquals("add field valid out stream", "Added field \'myfield\' to class \'myclass\'.", scrubOut(newOut.toString()));
		
		// Add field with invalid input
		assertNotEquals("add field invalid return code", 0, console.execCommand("add field myclass not*re(la"));
		assertEquals("add field invalid return code 2", 407, console.execCommand("add field myclass 38&32H3"));
		
		// Add field with wrong number of args
		assertEquals("add field too many args", 102, console.execCommand("add field myclass another field"));
		assertEquals("add field too many args 2", 102, console.execCommand("add field myclass another crazy field"));
		assertEquals("add field too few args", 102, console.execCommand("add field myclass"));
		assertEquals("add field too few args", 102, console.execCommand("add field"));
		
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
		assertEquals("add method valid return code", 0, console.execCommand("add method myclass mymethod"));
		assertEquals("add method valid error stream", "", scrubOut(newErr.toString()));
		assertEquals("add method valid out stream", "Added method \'mymethod\' to class \'myclass\'.", scrubOut(newOut.toString()));
		
		// Add method with invalid input
		assertNotEquals("add method invalid return code", 0, console.execCommand("add method myclass not*re(la"));
		assertEquals("add method invalid return code 2", 407, console.execCommand("add method myclass 38&32H3"));
		
		// Add method with wrong number of args
		assertEquals("add method too many args", 102, console.execCommand("add method myclass another method"));
		assertEquals("add method too many args 2", 102, console.execCommand("add method myclass another crazy method"));
		assertEquals("add method too few args", 102, console.execCommand("add method myclass"));
		assertEquals("add method too few args", 102, console.execCommand("add method"));
		
		// Add with no specifier
		assertEquals("add with no specifier", 102, console.execCommand("add"));
		
		// Reset the old output streams
		System.setOut(oldOut);
		System.setErr(oldErr);
	}
	
	/*
	 * Check command execution
	 */
	@Test
	public void checkInput() {
		// Set System.out/err to catch output text to test
		final ByteArrayOutputStream newOut = new ByteArrayOutputStream();
		final ByteArrayOutputStream newErr = new ByteArrayOutputStream();
		final PrintStream oldOut = System.out;
		final PrintStream oldErr = System.out;
		System.setOut(new PrintStream(newOut));
		System.setErr(new PrintStream(newErr));
		
		ConsoleView editor = new ConsoleView();
		int result;
		
		result = editor.execCommand("remove-class");
		assertEquals("remove-class no args", 102, result);
		newErr.reset();
		
		result = editor.execCommand("remove-class myclass");
		assertEquals("remove-class normal", "", scrubOut(newErr.toString()));
		assertEquals("remove-class normal return code", 0, result);
		
		// Can't check remove-class with normal args in this test because
		//	it relies on remove and add working which should be in a separate test
		
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
