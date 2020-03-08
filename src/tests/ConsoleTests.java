// Package name
package tests;

// System imports
import org.junit.Test;

import static org.junit.Assert.assertEquals;
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
	/*
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
		
		// Invalid command checks
		int result = editor.execCommand("jibberish");
		assertEquals("Unrecognized Command", 104, result);
		newErr.reset();
		
		result = editor.execCommand("more junk");
		assertEquals("Unrecognized Command 2", 104, result);
		newErr.reset();
		
		// Empty input checks
		result = editor.execCommand("");
		assertEquals("Blank input", 101, result);
		newOut.reset();
		
		result = editor.execCommand("           ");
		assertEquals("Blank input 2", 101, result);
		newOut.reset();
		
		result = editor.execCommand("                     ");
		assertEquals("Blank input 3", 101, result);
		newOut.reset();
		
		// Test add/remove class input
		result = editor.execCommand("add-class");
		assertEquals("add-class no args", 102, result);
		newErr.reset();
		
		// Only check to make sure there are no errors so that this test
		//	is independent from whether add/remove is working or not
		result = editor.execCommand("add-class my_class");
		assertEquals("add-class normal", "", scrubOut(newErr.toString()));
		assertEquals("add-class normal return code", 0, result);
		newErr.reset();
		newOut.reset();
		
		result = editor.execCommand("remove-class");
		assertEquals("remove-class no args", 102, result);
		newErr.reset();
		
		result = editor.execCommand("remove-class my_class");
		assertEquals("remove-class normal", "", scrubOut(newErr.toString()));
		assertEquals("remove-class normal return code", result, 0);
		
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
