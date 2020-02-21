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
		int result = manager.addClass("t");
		assertEquals("Class already exists", result, 200);
	}
	
	/*
	 * Creates a new manager, adds classes, and removes them
	 */
	@Test
	public void removeTest() {
		UMLClassManager manager = new UMLClassManager();
		int result = manager.removeClass("a");
		assertEquals("There is no size", result, 201);
		manager.addClass("a");
		manager.addClass("n");
		manager.addClass("t");
		manager.removeClass("a");
		manager.removeClass("n");
		manager.removeClass("t");
		assertEquals("List is empty", manager.listClasses(), "[]");
		result = manager.removeClass("c");
		assertEquals("Class does not exist", result, 201);
		result = manager.addClass("c");
		assertEquals("class removed", result, 0);
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
		int result = parse.parseJSON(j);
		assertEquals("List is the same as start", result, 0);
	}
}
