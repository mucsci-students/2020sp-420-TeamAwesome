package resources;
//System imports
import java.util.LinkedList;
import java.util.HashMap; 
import java.util.Map;
import java.util.jar.Attributes.Name;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

/**
 * For adding and removing classes from the UML diagram
 * @author antho
 * @author Dylan
 */
public class UMLClassManager {
	private HashMap<String, UMLClass> classList;

	/**
	 * Default constructor if we don't have a linked list make one
	 */
	public UMLClassManager() {
		classList = new HashMap<String, UMLClass>();
	}
  
	public boolean empty() {
		return classList.isEmpty();
	}
	/**
	 * Adds node of type UMLClass to list
	 * @param name: name of class
	 * @return true if the new class was successfully added to the list
	 */
	public int addClass(String name) 
		{
		//Prevent duplicates
		if (classList.containsKey(name))
		{
			return 200;
		}
		UMLClass newClass = new UMLClass(name);
		classList.put(name, newClass);
		return 0;
	}
	/**
	 * 
	 * @param className - the class we want to add a method to
	 * @param methodName - the name of the new method
	 * @return 0 on success and corresponding error code else
	 */
	public int addMethods(String className, String methodName)
	{

		if (classList.containsKey(className))
		{
			if (classList.get(className).methods.contains(methodName)) 
			{
				return 402;
			}
			else 
			{
				classList.get(className).addMethod(methodName);
				return 0;
			}
		}
		else return 403;
		
	}

		/**
	 * 
	 * @param className - the class we want to add a field to
	 * @param fieldName - the name of the new field
	 * @return 0 on success and corresponding error code else
	 */
	public int addFields(String className, String fieldName)
	{
		if (classList.containsKey(className))
		{
			if (classList.get(className).fields.contains(fieldName)) 
			{
				return 404;
			}
			else 
			{
				classList.get(className).addField(fieldName);
				return 0;
			}
		}
		else return 403;
	}
	/**
	 * 
	 * @param className - class to remove field from
	 * @param fieldName - fieldname to remove
	 * @return - returns 0 on successfull removal and corresponding error code in all other cases
	 */
	public int removeFields(String className, String fieldName)
	{
		if (classList.containsKey(className))
		{
			if (classList.get(className).fields.contains(fieldName)) 
			{
				classList.get(className).removeField(fieldName);
				return 0;
			}
			else 
			{
				return 405;
			}
		}
		else return 403;
	}

	/**
	 * 
	 * @param className - the class we want to remove a method from
	 * @param methodName - the name of the method we want to remove
	 * @return - returns 0 on success and corresponding error codes else
	 */
	public int removeMethods(String className, String methodName)
	{

		if (classList.containsKey(className))
		{
			if (classList.get(className).methods.contains(methodName)) 
			{
				classList.get(className).removeMethod(methodName);
				return 0;
			}
			else 
			{
				return 406;
			}
		}
		else return 403;
		
	}
	/**
	 * 
	 * @param oldName; the class we want to edit 
	 * @param newName; the name of the new class
	 * @return 0 on succesful name change and error code on failure 
	 */
	public int editClass(String oldName, String newName)
	{
			//check if the new name doesn't already exist as a class name
		if (classList.containsKey(newName))
		{
			return 400;
		}
		if (classList.containsKey(oldName))
		{
			classList.get(oldName).name = newName;
			return 0;
		}
		return 401;
	}
		/**
	 * @param className; the class holding the field we want to edit
	 * @param oldField; the field we want to change 
	 * @param newName; the name of the new field
	 * @return 0 on succesful name change and error code on failure 
	 */
	public int editFields(Sting className, String oldField, String newName)
	{
			//check if the new name doesn't already exist as a class name
		if (classList.containsKey(className)){
			if (classList.get(className).fields.contains(newName))
			{
				return 404; 
			}
			if (classList.get(className).fields.contains(oldField))
			{
				//this is great code don't question it keep moving
				classList.get(className).removeField(oldField);
				classList.get(className).addField(newName);
				return 0;
			}
			return 405;
		}
		return 403;
	}

	/**
	 * 
	 * @param className - the class containing the method to edit
	 * @param oldMethod - the method name to edit
	 * @param newName - the new method name
	 * @return - 0 on successful 'name change' and corresponding error codes in all other cases
	 */
	public int editMethods(String className, String oldMethod, String newName)
	{
			//check if the new name doesn't already exist as a class name
		if (classList.containsKey(className)){
			if (classList.get(className).methods.contains(newName))
			{
				return 402; 
			}
			if (classList.get(className).methods.contains(oldMethod))
			{
				//this is great code don't question it keep moving
				classList.get(className).removeMethod(oldMethod);
				classList.get(className).addMethod(newName);
				return 0;
			}
			return 406;
		}
		return 403;
	}
	
	/**
	 * Removes node of type UMLClass from list
	 * @param className: name of class
	 * @return true if the class was successfully removed from the list
	 */
	public int removeClass(String className) {
		if (classList.containsKey(name))
		{
			classList.remove(Name);
			return 0;
		}
		return 201;
	}
	
	/**
	 * Get the list of classes in the UML diagram
	 * @return String of classes in format "[class1, class2, ...]"
	 */
	public String listClasses() {
		String result = "[";
		
		// Iterate through classList
		int count = 0;
		for(Map.Entry<String, UMLClass> entry : classList.entrySet()) {
			result += entry.getKey();
			// Only add comma if not the last element
			if(count++ != classList.size() -1) {
				
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
		int count = 0;
		UMLClass[] classArray = new UMLClass[classList.size()];
		for(Map.Entry<String, UMLClass> entry : classList.entrySet()) {
			classArray[i] = (UMLClass) entry.getValue();
		}
		
		// Convert array to JSON
		jsonString += gson.toJson(classArray);
		
		return jsonString;
	}
	
	/**
	 * Parse JSON into classList
	 * @return true if parsed successfully
	 */
	public int parseJSON(String json) {
		// JSON parser object
		Gson gson = new GsonBuilder().setPrettyPrinting().create();
		
		// Parse JSON to array of classes
		UMLClass[] classArray = gson.fromJson(json, UMLClass[].class);
		
		// Load array into LinkedList
		for(UMLClass c : classArray) {
			classList.put(c.name, c);
		}
		
		return 0;
	}
}