// Package name
package tests;

// System imports
import org.junit.Test;

import static org.junit.Assert.assertTrue;

// Local imports
import views.GUIView;

/**
 * Class to run JUnit tests on the GUI
 * 
 * @author Ryan
 *
 */
public class GUITests {
	/**
	 * Create the GUI environment and the environment is initialized
	 */
	@Test
	public void baseInitialization() {
		GUIView gui = new GUIView();
		assertTrue("Window not null", gui.getWindow() != null);
		assertTrue("Controller not null", gui.getController() != null);
		assertTrue("Diagram exists", gui.getDiagram() != null);
	}
	
	/**
	 * Test the addition of classes to the GUI
	 */
	@Test
	public void addClass() {
		GUIView gui = new GUIView();
	}
}
