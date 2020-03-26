// Package name
package tests;

// System imports
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import java.awt.Component;
import javax.swing.JComponent;
import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;
import javax.swing.JSeparator;

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
	 * Ensure the initialization of the mouse menu and its menu items
	 */
	@Test
	public void mouseMenuInitializations() {
		GUIView gui = new GUIView();
		JComponent mouseMenu = gui.getComponent("Mouse Menu");
		assertTrue("Main mouse menu exists", mouseMenu != null);
		assertTrue("Main mouse menu is popup menu", mouseMenu instanceof JPopupMenu);
		
		Component[] mouseMenuChildren = mouseMenu.getComponents();
		assertTrue("Main mouse menu not empty", mouseMenuChildren.length != 0);
		assertEquals("Main mouse menu number of items", 4, mouseMenuChildren.length);
		assertEquals("Main mouse menu first child class add", "Add Class" , ((JMenuItem)mouseMenuChildren[0]).getName());
		assertTrue("Main mouse menu second child separator", mouseMenuChildren[1] instanceof JSeparator);
		assertEquals("Main mouse menu third child save", "Save to File" , ((JMenuItem)mouseMenuChildren[2]).getName());
		assertEquals("Main mouse menu first child load", "Load File" , ((JMenuItem)mouseMenuChildren[3]).getName());
	}
	
	/**
	 * Test the addition of classes to the GUI
	 */
	@Test
	public void addClass() {
		GUIView gui = new GUIView();
	}
}
