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
		
		// Test MenuItems' initialization
		Component[] mouseMenuChildren = mouseMenu.getComponents();
		assertTrue("Main mouse menu not empty", mouseMenuChildren.length != 0);
		assertEquals("Main mouse menu number of items", 4, mouseMenuChildren.length);
		assertEquals("Main mouse menu first child class add", "Add Class" , ((JMenuItem)mouseMenuChildren[0]).getName());
		assertTrue("Main mouse menu second child separator", mouseMenuChildren[1] instanceof JSeparator);
		assertEquals("Main mouse menu third child save", "Save to File" , ((JMenuItem)mouseMenuChildren[2]).getName());
		assertEquals("Main mouse menu fourth child load", "Load File" , ((JMenuItem)mouseMenuChildren[3]).getName());
	}
	
	/**
	 * Ensure the initialization of the class mouse menu and its items
	 */
	@Test
	public void classMenuInitialization() {
		GUIView gui = new GUIView();
		JComponent classMenu = gui.getComponent("Class Menu");
		assertTrue("Class menu exists", classMenu != null);
		assertTrue("Class menu is popup menu", classMenu instanceof JPopupMenu);

		// Test MenuItems' initialization
		Component[] classMenuChildren = classMenu.getComponents();
		assertTrue("Class menu not empty", classMenuChildren.length != 0);
		assertEquals("Class menu number of items", 13, classMenuChildren.length);
		assertEquals("Class menu first child", "Remove Class" , ((JMenuItem)classMenuChildren[0]).getName());
		assertEquals("Class menu second child", "Edit Class Name", ((JMenuItem)classMenuChildren[1]).getName());
		assertTrue("Class menu third child separator", classMenuChildren[2] instanceof JSeparator);
		assertEquals("Class menu fourth child", "Add Field" , ((JMenuItem)classMenuChildren[3]).getName());
		assertEquals("Class menu child 5", "Remove Field" , ((JMenuItem)classMenuChildren[4]).getName());
		assertEquals("Class menu child 6", "Edit Field Name" , ((JMenuItem)classMenuChildren[5]).getName());
		assertTrue("Class menu child 7 separator", classMenuChildren[6] instanceof JSeparator);
		assertEquals("Class menu child 8", "Add Method" , ((JMenuItem)classMenuChildren[7]).getName());
		assertEquals("Class menu child 9", "Remove Method" , ((JMenuItem)classMenuChildren[8]).getName());
		assertEquals("Class menu child 10", "Edit Method Name" , ((JMenuItem)classMenuChildren[9]).getName());
		assertTrue("Class menu child 11 separator", classMenuChildren[10] instanceof JSeparator);
		assertEquals("Class menu child 12", "Add Relationship" , ((JMenuItem)classMenuChildren[11]).getName());
		assertEquals("Class menu child 13", "Remove Relationship" , ((JMenuItem)classMenuChildren[12]).getName());
	}
	
	/**
	 * Test the addition of classes to the GUI
	 */
	@Test
	public void addClass() {
		GUIView gui = new GUIView();
	}
}
