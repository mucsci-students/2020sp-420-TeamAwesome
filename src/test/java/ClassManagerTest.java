import static org.junit.Assert.assertEquals;
// System imports
import static org.junit.Assert.assertTrue;

import java.util.Arrays;

import org.junit.Test;

// Local imports
import model.UMLClassManager;
import model.UMLRelationship;

/**
 * Class for running JUnit tests on ClassManager
 * 
 * @author Anthony
 */
public class ClassManagerTest {
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
		int valName = manager.addClass("");
		int val2 = manager.addClass(null);
		assertEquals("name is not valid when empty", 407, valName);
		assertEquals("name is not valid when null", 407, val2);
		manager.addClass("a");
		manager.addClass("n");
		manager.addClass("t");
		assertEquals("List is correct size", 3, manager.getClassNames().length);
		assertEquals("List contains a", "a", manager.getClass("a").getName());
		assertEquals("List contains n", "n", manager.getClass("n").getName());
		assertEquals("List contains t", "t", manager.getClass("t").getName());
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
		assertEquals("List is empty", 0, manager.getClassNames().length);
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
		manager.getClass("c").getMethods().get("methodstring").setReturnType("double");
		String type = manager.getClass("c").getMethods().get("methodstring").getReturnType();
		assertEquals("Return type was set successfully", "double", type);
		result = manager.addMethods("c", "double", "method", "string");
		assertEquals("Method exists already", 402, result);
		manager.removeClass("c");
		result = manager.removeMethods("c", "method", "string");
		assertEquals("Class does not exist", 403, result);
		manager.addClass("d");
		result = manager.removeMethods("d", "method", "string");
		assertEquals("Method does not exist", 406, result);
		manager.addMethods("d", "double", "method", "string");
		boolean r = manager.getClass("d").hasMethod("method", "string");
		assertEquals("method is the only method", true, r);
		result = manager.removeMethods("d", "method", "string");
		assertEquals("Success", 0, result);
		result = manager.addMethods("d", "not real", "method", "string");
		assertEquals("Return type is not valid", 203, result);
		
		UMLClassManager man = new UMLClassManager();
		man.addClass("a");
		man.addMethods("a", "int", "m", "int");
		man.getClass("a").getMethods().get("mint").setName("l");
		String name = man.getClass("a").getMethods().get("mint").getName();
		assertEquals("Name was set successfully", "l", name);
		man.getClass("a").getMethods().get("mint").setParams("double");
		String params = man.getClass("a").getMethods().get("mint").getParams();
		assertEquals("Params were set successfully", "(double)", params);
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
		String name = manager.getClass("c").getFields().get("field").getName();
		assertEquals("Name is correct", "field", name);
		result = manager.addFields("c", "int", "field");
		assertEquals("Field exists already", 404, result);
		manager.removeClass("c");
		result = manager.removeFields("c", "field");
		assertEquals("Class does not exist", 403, result);
		manager.addClass("d");
		result = manager.removeFields("d", "field");
		assertEquals("Field does not exist", 405, result);
		manager.addFields("d", "double", "field");
		boolean r = manager.getClass("d").hasField("field");
		assertEquals("field is the only field", true, r);
		manager.getClass("d").getFields().get("field").setType("String");
		String type = manager.getClass("d").getFields().get("field").getType();
		assertEquals("type was set correctly", "String", type);
		manager.getClass("d").getFields().get("field").setName("Field");
		String fieldName = manager.getClass("d").getFields().get("field").getName();
		assertEquals("name was set correctly", "Field", fieldName);
		result = manager.removeFields("d", "field");
		assertEquals("Success", 0, result);
		result = manager.addFields("d", "not real", "field");
		assertEquals("Type is not valid", 203, result);
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
		result = manager.editClass("h", "@");
		assertEquals("New class name is invalid.", 407, result);
		result = manager.editFields("h", "f", "g");
		assertEquals("Selected field does not exist", 405, result);
		result = manager.editMethods("h", "f", "string", "g");
		assertEquals("Selected method does not exist", 406, result);
		manager.addFields("h", "int", "f");
		manager.addMethods("h", "double", "m", "string");
		result = manager.editFields("h", "f", "g");
		assertEquals("Success", 0, result);
		result = manager.editMethods("h", "m", "b", "string");
		assertEquals("Succcess", 0, result);
		manager.addFields("h", "double", "z");
		manager.addMethods("h", "int", "a", "string");
		result = manager.editFields("h", "g", "z");
		assertEquals("Selected new name already exists", 404, result);
		result = manager.editFields("h", "g", "&");
		assertEquals("New field name is invalid.", 409, result);
		result = manager.editMethods("h", "b", "a", "string");
		assertEquals("Selected new name already exists", 402, result);
		result = manager.editMethods("h", "b", " ", "string");
		assertEquals("New method name is invalid", 408, result);
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
		boolean r = manager.getRelationships().containsKey("c" + UMLRelationship.AGGREGATION + "d");
		assertEquals("C is related to D", true, r);
		boolean c = manager.getRelationships().get("c" + UMLRelationship.AGGREGATION + "d").hasClass("c");
		boolean d = manager.getRelationships().get("c" + UMLRelationship.AGGREGATION + "d").hasClass("c");
		assertEquals("Realtionship contains class c", true, c);
		assertEquals("Realtionship contains class d", true, d);	
		String type = manager.getRelationships().get("c" + UMLRelationship.AGGREGATION + "d").getType();
		assertEquals("Type is returned correctly", "aggregation", type);
		manager.addClass("e");
		manager.addRelationship("c", "composition", "e");
		manager.addRelationship("e", "realization", "d");
		boolean neither = manager.getRelationships().get("e" + UMLRelationship.REALIZATION + "d").hasClass("c");
		boolean right = manager.getRelationships().get("c" + UMLRelationship.COMPOSITION + "e").hasClass("e");
		boolean left = manager.getRelationships().get("e" + UMLRelationship.REALIZATION + "d").hasClass("e");
		assertEquals("Realtionship does not contain either class", false, neither);
		assertEquals("Realtionship contains class e", true, right);
		assertEquals("Realtionship contains class e", true, left);
		manager.removeClass("c");	
		result = manager.removeRelationship("c","aggregation", "d");
		assertEquals("One or more classes do not exist", 107, result);
		manager.addClass("c");
		manager.addRelationship("c", "reaLization", "d");
		boolean contained = manager.getRelationships().containsKey("c" + UMLRelationship.REALIZATION + "d");
		assertEquals("The relationship is in the hash map.", true, contained);
		result = manager.removeRelationship("c","composition", "d");
		assertEquals("Relationship does not exist", 108, result);
		manager.addRelationship("c", "composition", "d");
		result = manager.removeRelationship("c", "composition", "d");
		assertEquals("Success", 0, result);
		
		UMLClassManager ager = new UMLClassManager();
		ager.addClass("a");
		ager.addClass("b");
		ager.addRelationship("a", "composition", "b");
		ager.addRelationship("b", "inheritance", "a");
		ager.addRelationship("a", "Realization", "a");
		
		String[] rel1 = ager.getRelationships().get("a" + UMLRelationship.COMPOSITION + "b").vertType();
		String[] rel2 = ager.getRelationships().get("b" + UMLRelationship.INHERITANCE + "a").vertType();
		String[] rel3 = ager.getRelationships().get("a" + UMLRelationship.REALIZATION + "a").vertType();
		
		String[] comp = {" |", " |", " |", "<=>"};
		String[] in = {" |", " |", " |", "> <"};
		String[] real = {" |", " ", " ", " |", "> <"};
		
		assertEquals("vert types match", Arrays.toString(comp), Arrays.toString(rel1));
		assertEquals("vert types match", Arrays.toString(in), Arrays.toString(rel2));
		assertEquals("vert types match", Arrays.toString(real), Arrays.toString(rel3));
		int flipped = ager.removeRelationship("b", "composition", "a");
		assertEquals("realationship is removed", 0, flipped);
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
