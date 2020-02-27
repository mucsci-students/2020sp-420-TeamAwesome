package resources;

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