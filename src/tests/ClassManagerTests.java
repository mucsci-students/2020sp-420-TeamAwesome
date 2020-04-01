// Package name
package tests;

import static org.junit.Assert.assertEquals;
// System imports
import static org.junit.Assert.assertTrue;


import org.junit.Test;

// Local imports
import model.UMLClassManager;
import model.UMLRelationship;

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
		assertEquals("Class already exists", 200, result);
	}
	
	/*
	 * Creates a new manager, adds classes, and removes them
	 */
	@Test
	public void removeTest() {
		UMLClassManager manager = new UMLClassManager();
		int result = manager.removeClass("a");
		assertEquals("There is no size", 201, result);
		manager.addClass("a");
		manager.addClass("n");
		manager.addClass("t");
		manager.removeClass("a");
		manager.removeClass("n");
		manager.removeClass("t");
		assertEquals("List is empty", "[]", manager.listClasses());
		result = manager.removeClass("c");
		assertEquals("Class does not exist", 201, result);
		result = manager.addClass("c");
		assertEquals("class removed", 0, result);
	}
	
	/*
	 * Creates manager then adds and removes methods from classes in list.
	 */
	@Test
	public void methodTests() {
		UMLClassManager manager = new UMLClassManager();
		int result = manager.addMethods("class1", "int", "method", "string");
		assertEquals("Class does not exist", 403, result);
		manager.addClass("c");
		result = manager.addMethods("c", "int", "method", "string");
		assertEquals("Success", 0, result);
		result = manager.addMethods("c", "int", "method", "string");
		assertEquals("Method exists already", 402, result);
		manager.removeClass("c");
		result = manager.removeMethods("c", "method", "string");
		assertEquals("Class does not exist", 403, result);
		manager.addClass("d");
		result = manager.removeMethods("d", "method", "string");
		assertEquals("Method does not exist", 406, result);
		manager.addMethods("d", "double", "method", "string");
		String r = manager.listMethods("d")[0].toString();
		assertEquals("method is the only method", "[double method(string)]", r);
		result = manager.removeMethods("d", "method", "string");
		assertEquals("Success", 0, result);
	}
	
	/*
	 * Creates manager then adds and removes fields from classes in list.
	 */
	@Test
	public void fieldTests() {
		UMLClassManager manager = new UMLClassManager();
		int result = manager.addFields("class1", "int", "field");
		assertEquals("Class does not exist", 403, result);
		manager.addClass("c");
		result = manager.addFields("c", "int", "field");
		assertEquals("Success", 0, result);
		result = manager.addFields("c", "int", "field");
		assertEquals("Field exists already", 404, result);
		manager.removeClass("c");
		result = manager.removeFields("c", "field");
		assertEquals("Class does not exist", 403, result);
		manager.addClass("d");
		result = manager.removeFields("d", "field");
		assertEquals("Field does not exist", 405, result);
		manager.addFields("d", "double", "field");
		String r = manager.listFields("d")[0].toString();
		assertEquals("field is the only field", "[double field]", r);
		result = manager.removeFields("d", "field");
		assertEquals("Success", 0, result);
	}
	
	/*
	 * Creates manager then adds classes, fields, and methods to be changed.
	 */
	@Test
	public void EditTests() {
		UMLClassManager manager = new UMLClassManager();
		int result = manager.editClass("e", "f");
		assertEquals("Selected class does not exist", 401, result);
		result = manager.editFields("e", "f", "g");
		assertEquals("Selected class does not exist", 403, result);
		result = manager.editMethods("e", "f", "string", "g");
		assertEquals("Selected class does not exist", 403, result);
		manager.addClass("e");
		result = manager.editClass("e", "f");
		assertEquals("Success", 0, result);
		manager.addClass("h");
		result = manager.editClass("f", "h");
		assertEquals("New class name already exists", 400, result);
		result = manager.editFields("h", "f", "g");
		assertEquals("Selected field does not exist", 405, result);
		result = manager.editMethods("h", "f", "string", "g");
		assertEquals("Selected method does not exist", 406, result);
		manager.addFields("h", "int", "f");
		manager.addMethods("h", "double", "m", "string");
		result = manager.editFields("h", "f", "g");
		assertEquals("Success", 0, result);
		result = manager.editMethods("h", "m", "string", "b");
		assertEquals("Succcess", 0, result);
		manager.addFields("h", "double", "z");
		manager.addMethods("h", "int", "a", "string");
		result = manager.editFields("h", "g", "z");
		assertEquals("Selected new name already exists", 404, result);
		result = manager.editMethods("h", "b", "string", "a");
		assertEquals("Selected new name already exists", 402, result);
	}
	
	/*
	 * Creates manager to test relationships between classes.
	 */
	@Test
	public void relationshipTests() {
		UMLClassManager manager = new UMLClassManager();
		int result = manager.addRelationship("class1", "aggregation", "method");
		assertEquals("One or more of the classes do not exist", 107, result);
		manager.addClass("c");
		manager.addClass("d");
		result = manager.addRelationship("c","Jibberish", "d");
		assertEquals("Relationship is not a valid relationship", 202, result);
		result = manager.addRelationship("c","aggregation", "d");
		assertEquals("Success", 0, result);
		result = manager.addRelationship("c", "aggregation", "d");
		assertEquals("Relationship exists already", 106, result);
		String r = manager.listRelationships("c")[0].toString();
		assertEquals("C is related to D", "[c" + UMLRelationship.AGGREGATION + "d]", r);
		manager.removeClass("c");
		result = manager.removeRelationship("c","aggregation", "d");
		assertEquals("One or more classes do not exist", 107, result);
		manager.addClass("c");
		result = manager.removeRelationship("c","composition", "d");
		assertEquals("Relationship does not exist", 108, result);
		manager.addRelationship("c", "composition", "d");
		result = manager.removeRelationship("c", "composition", "d");
		assertEquals("Success", 0, result);
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
		assertEquals("List is the same as start", 0, result);
	}
}
