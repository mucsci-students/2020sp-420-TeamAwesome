// Package name
package tests;

import static org.junit.Assert.assertEquals;
// System imports
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertFalse;
import org.junit.Test;

import main.UMLEditor;
// Local imports
import resources.UML;

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
	UML manager = new UML();
	assertTrue("List is empty", manager.empty());
	}
	
	/*
	 * Creates a new manager and adds classes
	 */
	@Test
	public void addTest() {
		UML manager = new UML();
		manager.addClass("a");
		manager.addClass("n");
		manager.addClass("t");
		assertEquals("List is populated", manager.toString(), "[a, n, t]");
		int result = manager.addClass("t");
		assertEquals("Class already exists", result, 200);
	}
	
	/*
	 * Creates a new manager, adds classes, and removes them
	 */
	@Test
	public void removeTest() {
		UML manager = new UML();
		int result = manager.removeClass("a");
		assertEquals("There is no size", result, 201);
		manager.addClass("a");
		manager.addClass("n");
		manager.addClass("t");
		manager.removeClass("a");
		manager.removeClass("n");
		manager.removeClass("t");
		assertEquals("List is empty", manager.toString(), "[]");
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
		UML manager = new UML();
		manager.addClass("b");
		manager.addClass("i");
		manager.addClass("g");
		String j = manager.convertToJSON();
		
		UML parse = new UML();
		int result = parse.parseJSON(j);
		assertEquals("List is the same as start", result, 0);
	}
}
