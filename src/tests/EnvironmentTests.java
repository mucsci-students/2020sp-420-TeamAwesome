// Package name
package tests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

// System imports
import org.junit.Test;

// Local imports
import console.UMLConsole;
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
		
		editor.execCommand("jibberish");
		assertEquals("Unrecognized Command", "Command \'jibberish\' was not recognized.", scrubOut(newErr.toString()));
		newErr.reset();
		
		editor.execCommand("more junk");
		assertEquals("Unrecognized Command 2", "Command \'more junk\' was not recognized.", scrubOut(newErr.toString()));
		newErr.reset();
		
		// Reset the old output streams
		System.setOut(oldOut);
		System.setOut(oldErr);
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
