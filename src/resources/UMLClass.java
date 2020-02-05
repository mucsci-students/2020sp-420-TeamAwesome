package resources;

// System imports
import java.util.ArrayList;

// Local imports

/**
 * Allows building of classes for use as something like a node
 * @author antho
 *
 */
public class UMLClass {
	public String name;
	private String [] types = {"int", "double", "float", "short", "long", "boolean", "String"};
	private ArrayList<String> fields;
	private ArrayList<String> methods;
	private String type;
	
	/**
	 * Constructor for class objects
	 * @param name the desied name of the class
	 * @param type the desired type of the class
	 */
	public UMLClass(String name, String type) {
		this.name = name;
		this.type = type;
		fields = new ArrayList<String>();
		methods = new ArrayList<String>();
	}
	
	/**
	 * Adds a field to to ArrayList fiel
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
}