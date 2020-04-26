package model;

// System imports
import java.util.HashMap;
import java.io.Serializable;

// Local imports

/**
 * Allows building of classes for use as something like a node
 * @author Anthony and Dylan
 * 
 */
public class UMLClass implements Serializable {
	// Version ID for serialization
	private static final long serialVersionUID = 1L;
	
	private String name;
	public static final String [] types = {"int", "double", "float", "short", "long", "boolean", "String"};
	private HashMap<String, Field> fields;
	// Key = methodName + params
	private HashMap<String, Method> methods;
	
	// Coordinates of class, for GUI use only
	private int x;
	private int y;
	
	/**
	 * Constructor for class objects
	 * @param name the desired name of the class
	 * @param type the desired type of the class
	 */
	public UMLClass(String name) {
		this.name = name;
		fields = new HashMap<String, Field>();
		methods = new HashMap<String, Method>();
	}
	
	/**
	 * Adds a field to to ArrayList field
	 * @param field name of field to be added
	 */
	public void addField(String type, String field) {
		Field newField = new Field(type, field);
		fields.put(field, newField);
	}
	
	/**
	 * Adds a method to ArrayList methods
	 * @param method the name of the method to be added
	 */
	public void addMethod(String returnType, String method, String params) {
		Method newMethod = new Method(returnType, method, params);
		methods.put(method + params, newMethod);
	}
	
	/**
	 * Removes a field from to ArrayList fields
	 * @param field name of field to be removed
	 */
	public void removeField(String field) {
		fields.remove(field);
	}
	
	/**
	 * Removes a method from ArrayList methods
	 * @param method the name of the method to be removed
	 */
	public void removeMethod(String method, String params) {
		methods.remove(method + params);
	}
	
	/**
	 * Gets the name of the UMLClass
	 * @return - name
	 */
	public String getName() {
		return name;
	}
	
	/**
	 * Set the name of the class
	 * @param name - the new class name
	 */
	public void setName(String name) {
		this.name = name;
	}
	
	/**
	 * Check if the class has a given method
	 * @param methodName
	 * @return - true if class has method
	 */
	public boolean hasMethod(String methodName, String params) {
		return methods.containsKey(methodName + params);
	}
	
	/**
	 * Check if the class has a given field
	 * @param fieldName
	 * @return - true if class has field
	 */
	public boolean hasField(String fieldName) {
		return fields.containsKey(fieldName);
	}
	
	/**
	 * Get the X coordinate of class
	 * @return - x
	 */
	public int getX() {
		return x;
	}
	
	/**
	 * Get the Y coordinate of class
	 * @return - y
	 */
	public int getY() {
		return y;
	}
	
	/**
	 * Set the X coordinate of class
	 * @param x - new x coordinate
	 */
	public void setX(int x) {
		this.x = x;
	}
	
	/**
	 * Set the Y coordinate of class
	 * @param y - new y coordinate
	 */
	public void setY(int y) {
		this.y = y;
	}
	
	/**
	 * Set the class to a new location
	 * @param x - new x coordinate
	 * @param y - new y coordinate
	 */
	public void setLocation(int x, int y) {
		setX(x);
		setY(y);
	}

	/**
	 * Get the fields that are in the class
	 * @return - fields
	 */
	public HashMap<String, Field> getFields() {
		return fields;
	}

	/**
	 * Get the methods that are in the class
	 * @return - methods
	 */
	public HashMap<String, Method> getMethods() {
		return methods;
	}
}