package model;

import java.io.Serializable;

public class UMLRelationship implements Serializable {
	public static final String AGGREGATION = " ----<> ";
	public static final String COMPOSITION = " ----<=> ";
	public static final String INHERITANCE = " ----> ";
	public static final String REALIZATION = " - - - -> ";
	
	// Version ID for serialization
	private static final long serialVersionUID = 1L;
	
	private UMLClass class1;
	private UMLClass class2;
	private String type;
	
	/** Create relationship between class1 and class2
	 * @param class1
	 * @param class2
	 */
	public UMLRelationship(UMLClass class1, String type, UMLClass class2) {
		this.class1 = class1;
		this.class2 = class2;
		this.type = type;
	}

	/**
	 * Get the first class of the relationship
	 * @return - class1
	 */
	public UMLClass getClass1() {
		return class1;
	}

	/**
	 * Get the second class of the relationship
	 * @return - class2
	 */
	public UMLClass getClass2() {
		return class2;
	}
	
	/**
	 * pulls the relationship type
	 * @return the type of the relationship
	 */
	public String getType() {
		return this.type;
	}
	
	/**
	 * Checks to see if className is part of the relationship
	 * @param className
	 * @return
	 */
	public boolean hasClass(String className) {
		return class1.getName().equals(className) || class2.getName().equals(className);
	}
	
	/**
	 * Create string representing relationship between given classnames
	 * @param className1
	 * @param className2
	 * @return String of format 'className1 DELIMITER className2'
	 */
	public static final String GENERATE_STRING(String className1, String type, String className2) {
		if(type == "aggregation") {
			return className1 + AGGREGATION + className2;
		}
		else if(type == "composition") {
			return className1 + COMPOSITION + className2;
		}
		else if(type == "inheritance") {
			return className1 + INHERITANCE + className2;
		}
		else if(type == "Realization") {
			return className1 + REALIZATION + className2;
		}
		else {
			return "Invalid Type";
		}
	}
}
