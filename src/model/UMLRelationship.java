package model;

import java.io.Serializable;

public class UMLRelationship implements Serializable {
	public static final String DELIMITER = " --- ";
	
	// Version ID for serialization
	private static final long serialVersionUID = 1L;
	
	private UMLClass class1;
	private UMLClass class2;
	
	/** Create relationship between class1 and class2
	 * @param class1
	 * @param class2
	 */
	public UMLRelationship(UMLClass class1, UMLClass class2) {
		this.class1 = class1;
		this.class2 = class2;
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
	public static final String GENERATE_STRING(String className1, String className2) {
		return className1 + DELIMITER + className2;
	}
}
