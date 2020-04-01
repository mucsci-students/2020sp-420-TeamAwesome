package model;

import java.io.Serializable;

/**
 * @author Anthony, Ryan
 */
public class Field  implements Serializable {
	private static final long serialVersionUID = 1L;
	private String name;
	private String type;
	
	/**
	 * basic constructor given type and name
	 * @param name field name
	 * @param type field type
	 */
	public Field(String type, String name) {
		this.name = name;
		this.type = type;
	}
	
	/**
	 * pulls the field name
	 * @return field name
	 */
	public String getName() {
		return this.name;
	}
	
	/**
	 * pulls the field type
	 * @return field type
	 */
	public String getType() {
		return this.type;
	}
	
	/**
	 * sets the field name
	 */
	public void setName(String name) {
		this.name = name;
	}
	
	/**
	 * sets the field type
	 */
	public void setType(String type) {
		this.type = type;
	}
	
	@Override
	/**
	 * returns a string representation of a field
	 */
	public String toString() {
		return type + " " + name;
		
	}
}
