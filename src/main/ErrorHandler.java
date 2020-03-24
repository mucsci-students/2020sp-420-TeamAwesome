// Package name
package main;

// System imports
import java.util.HashMap;

// Local imports

/**
 * Class to help with error handling. Contains a map of error codes to their string equivalents.
 *
 */
public class ErrorHandler {
	// Map with the relationship: error_code -> String representation of error
	public static final HashMap<Integer, String> ERROR_TABLE = createErrorTable();
	
	// Initializer for ERROR_TABLE
	private static final HashMap<Integer, String> createErrorTable() {
		// Create temporary map
		HashMap<Integer, String> tempMap = new HashMap<Integer, String>();
		
		// Add error codes to map
		tempMap.put(100, "Failure to close console scanner.");
		tempMap.put(101, "No commands we're passed to console.");
		tempMap.put(102, "Did not get expected number of arguments.");
		tempMap.put(103, "User did not specify a file.");
		tempMap.put(104, "Command was not recognized");
		tempMap.put(105, "File does not exist.");
		tempMap.put(106, "Relationship already exists.");
		tempMap.put(107, "One or more specified classes do not exist.");
		tempMap.put(108, "Relationship between specified classes does not exist.");
		tempMap.put(109, "Class does not exists.");
		tempMap.put(110, "Unable to set GUI look and feel.");
		tempMap.put(200, "Class not added. Duplicate class.");
		tempMap.put(201, "Class not removed. Class does not exist.");
		tempMap.put(202, "Relationship is not of a valid type.");
		tempMap.put(301, "Was unable to create file");
		tempMap.put(302, "Was unable open file for writing");
		tempMap.put(303, "Was unable read file");
		tempMap.put(400, "NewName is exisiting class name");
		tempMap.put(401, "Oldname is not an exisitng class");
		tempMap.put(402, "MethodName already exists inside of class");
		tempMap.put(403, "Destination class does not exist");
		tempMap.put(404, "fieldName already exists inside of class");
		tempMap.put(405, "Field does not exist in the given class");
		tempMap.put(406, "Method does not exist in the given class");
		tempMap.put(407, "Class name entered is invalid.");
		tempMap.put(408, "Method name entered is invalid.");
		tempMap.put(409, "Field name entered is invalid.");
		return tempMap;
	}
	
	/**
	 * Get the string representation of the given code
	 * @param code - error code from table
	 * @return String representation of 'code' if 'code' exists in ERROR_TABLE
	 */
	public static final String toString(int code) {
		if(ERROR_TABLE.containsKey(code))
			return "Error " + code + ": " + ERROR_TABLE.get(code);
		return "No such error found";
	}
}