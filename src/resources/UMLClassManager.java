package resources;
//System imports
import java.util.LinkedList;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

/**
 * For adding and removing classes from the UML diagram
 * @author antho
 * @author Dylan
 */
public class UMLClassManager {
	private LinkedList<UMLClass> classList;

	/**
	 * Default constructor if we don't have a linked list make one
	 */
	public UMLClassManager()
	{
		classList = new LinkedList<UMLClass>();
	}
	
	public boolean empty() {
		return classList.isEmpty();
	}
	/**
	 * Adds node of type UMLClass to list
	 * @param name: name of class
	 * @return true if the new class was successfully added to the list
	 */
	public boolean addClass(String name) {
		//Prevent duplicates
		UMLClass newClass = new UMLClass(name);
		int size = classList.size();
		for(int i = 0; i < size; ++i) {
			UMLClass temp = classList.get(i);
			if(temp.name.equals(name)) {
				return false;
			}
		}
		classList.addLast(newClass);
		return classList.contains(newClass);
	}
	
	/**
	 * Removes node of type UMLClass from list
	 * @param className: name of class
	 * @return true if the class was successfully removed from the list
	 */
	public boolean removeClass(String className) {
		int size = classList.size();
		for(int i = 0; i < size; ++i) {
			UMLClass temp = classList.get(i);
			if(temp.name.equals(className)) {
				classList.remove(i);
				return true;
			}
		}
		return false;
	}
	
	/**
	 * Get the list of classes in the UML diagram
	 * @return String of classes in format "[class1, class2, ...]"
	 */
	public String listClasses() {
		String result = "[";
		
		// Iterate through classList
		int size = classList.size();
		for(int i = 0; i < size; i++) {
			result += classList.get(i).name;
			// Only add comma if not the last element
			if(i != size-1) {
				result += ", ";
			}
		}
		
		result += "]";
		return result;
	}
	
	/**
	 * Convert the class list to a JSON string
	 * @return - JSON string
	 */
	public String convertToJSON() {
		String jsonString = "";
		
		// Create JSON builder and enable 'pretty printing' for multiple lines
		Gson gson = new GsonBuilder().setPrettyPrinting().create();
		
		// Convert LinkedList to standard array for serialization
		UMLClass[] classArray = new UMLClass[classList.size()];
		for(int i = 0; i < classArray.length; i++) {
			classArray[i] = (UMLClass) classList.get(i);
		}
		
		// Convert array to JSON
		jsonString += gson.toJson(classArray);
		
		return jsonString;
	}
	
	/**
	 * Parse JSON into classList
	 * @return true if parsed successfully
	 */
	public boolean parseJSON(String json) {
		// JSON parser object
		Gson gson = new GsonBuilder().setPrettyPrinting().create();
		
		// Parse JSON to array of classes
		UMLClass[] classArray = gson.fromJson(json, UMLClass[].class);
		
		// Load array into LinkedList
		for(UMLClass c : classArray) {
			classList.add(c);
		}
		
		return true;
	}
}