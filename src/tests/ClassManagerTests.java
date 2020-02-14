// Package name
package tests;

import static org.junit.Assert.assertEquals;
// System imports
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertFalse;
import org.junit.Test;

import main.UMLEditor;
// Local imports
import resources.UMLClassManager;

/**
 * Class for running JUnit tests on ClassManager
 * 
 * @author Anthony
 */
public class ClassManagerTests {
	/*
	 * Create a new instance of ClassManager with an empty list 
	 */
	@Test
	public void baseInstance() {
	UMLClassManager manager = new UMLClassManager();
	assertTrue("List is empty", manager.empty());
	}
	
	/*
	 * Creates a new manager and adds classes
	 */
	@Test
	public void addTest() {
		UMLClassManager manager = new UMLClassManager();
		manager.addClass("a");
		manager.addClass("n");
		manager.addClass("t");
		assertEquals("List is populated", manager.listClasses(), "[a, n, t]");
		manager.addClass("t");
		assertFalse("Class already exists", manager.addClass("t"));
	}
	
	/*
	 * Creates a new manager, adds classes, and removes them
	 */
	@Test
	public void removeTest() {
		UMLClassManager manager = new UMLClassManager();
		assertFalse("There is no size", manager.removeClass("a"));
		manager.addClass("a");
		manager.addClass("n");
		manager.addClass("t");
		manager.removeClass("a");
		manager.removeClass("n");
		manager.removeClass("t");
		assertEquals("List is empty", manager.listClasses(), "[]");
		manager.removeClass("c");
		assertFalse("Class does not exist", manager.removeClass("c"));
		manager.addClass("c");
		assertTrue("class removed", manager.removeClass("c"));
	}
	
	/*
	 * Creates list of classes then converts to json then parses back to String
	 */
	@Test
	public void convertParseTest() {
		UMLClassManager manager = new UMLClassManager();
		manager.addClass("b");
		manager.addClass("i");
		manager.addClass("g");
		String j = manager.convertToJSON();
		
		UMLClassManager parse = new UMLClassManager();
		parse.parseJSON(j);
		assertTrue("List is the same as start", parse.parseJSON(j));
	}
}
