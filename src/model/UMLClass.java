package model;

// System imports
import java.util.ArrayList;
import java.io.Serializable;

// Local imports

/**
 * Allows building of classes for use as something like a node
 * @author antho and Dylan
 * 
 */
public class UMLClass implements Serializable {
	// Version ID for serialization
	private static final long serialVersionUID = 1L;
	
	private String name;
	public static final String [] types = {"int", "double", "float", "short", "long", "boolean", "String"};
	private ArrayList<String> fields;
	private ArrayList<String> methods;
	
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
		fields = new ArrayList<String>();
		methods = new ArrayList<String>();
	}
	
	/**
	 * Adds a field to to ArrayList field
	 * @param field name of field to be added
	 */
	public void addField(String field) {
		fields.add(field);
	}
	
	/**
	 * Adds a method to ArrayList methods
	 * @param method the name of the method to be added
	 */
	public void addMethod(String method) {
		methods.add(method);
	}
	
	/**
	 * Removes a field from to ArrayList fields
	 * @param field name of field to be removed
	 */
	public void removeField(String field) {
		fields.remove(fields.indexOf(field));
	}
	
	/**
	 * Removes a method from ArrayList methods
	 * @param method the name of the method to be removeded
	 */
	public void removeMethod(String method) {
		methods.remove(methods.indexOf(method));
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
	public boolean hasMethod(String methodName) {
		return methods.contains(methodName);
	}
	
	/**
	 * Check if the class has a given field
	 * @param fieldName
	 * @return - true if class has field
	 */
	public boolean hasField(String fieldName) {
		return fields.contains(fieldName);
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
	public ArrayList<String> getFields() {
		return fields;
	}

	/**
	 * Get the methods that are in the class
	 * @return - methods
	 */
	public ArrayList<String> getMethods() {
		return methods;
	}
}