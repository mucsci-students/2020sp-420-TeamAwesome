// Package name
package tests;

// System imports
import org.junit.Test;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

// Local imports
import main.UMLEditor;

/**
 * Class to run JUnit tests on the Envioronment
 * 
 * @author Ryan
 */
public class EnvironmentTests {
	/*
	 * Create environment and don't do anything to it
	 */
	@Test
	public void baseInstance() {
		UMLEditor editor = new UMLEditor();
		assertTrue("Console not null", editor.getConsole() != null);
		assertTrue("Commands not null", editor.getValidCommands() != null);
		assertTrue("Commands not empty", editor.getValidCommands().length != 0);
		assertTrue("Scanner initialized", editor.getConsole().getScanner() != null);
		assertTrue("Close console", editor.getConsole().closeScanner());
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
		
		UMLEditor editor = new UMLEditor();
		
		// Invalid command checks
		editor.execCommand("jibberish");
		assertEquals("Unrecognized Command", "Command \'jibberish\' was not recognized.", scrubOut(newErr.toString()));
		newErr.reset();
		
		editor.execCommand("more junk");
		assertEquals("Unrecognized Command 2", "Command \'more junk\' was not recognized.", scrubOut(newErr.toString()));
		newErr.reset();
		
		// Empty input checks
		editor.execCommand("");
		assertEquals("Blank input", "", scrubOut(newOut.toString()));
		newOut.reset();
		
		editor.execCommand("           ");
		assertEquals("Blank input 2", "", scrubOut(newOut.toString()));
		newOut.reset();
		
		editor.execCommand("                     ");
		assertEquals("Blank input 3", "", scrubOut(newOut.toString()));
		newOut.reset();
		
		// Test add/remove class input
		editor.execCommand("add-class");
		assertEquals("add-class no args", "Did not get expected number of arguments.", scrubOut(newErr.toString()));
		newErr.reset();
		
		// Only check to make sure there are no errors so that this test
		//	is independent from whether add/remove is working or not
		editor.execCommand("add-class my_class");
		assertEquals("add-class normal", "", scrubOut(newErr.toString()));
		newErr.reset();
		newOut.reset();
		
		editor.execCommand("remove-class");
		assertEquals("remove-class no args", "Did not get expected number of arguments.", scrubOut(newErr.toString()));
		newErr.reset();
		
		editor.execCommand("remove-class my_class");
		assertEquals("remove-class normal", "", scrubOut(newErr.toString()));
		
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
