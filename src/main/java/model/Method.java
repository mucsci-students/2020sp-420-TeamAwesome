package model;

import java.io.Serializable;


/**
 * 
 * @author Anthony, Ryan
 *
 */
public class Method implements Serializable {
	private static final long serialVersionUID = 1L;
	
	private String returnType;
	private String name;
	private String params;
	
	/**
	 * basic constructor given name, return type, and parameter list
	 * @param name the method name
	 * @param returnType the method return turn
	 * @param params method parameter list
	 */
	public Method(String returnType, String name, String params) {
		this.name = name;
		this.returnType = returnType;
		this.params = params;
	}
	
	/**
	 * sets method name
	 */
	public void setName(String name) {
		this.name = name;
	}
	
	/**
	 * sets method return type
	 */
	public void setReturnType(String returnType) {
		this.returnType = returnType;
	}
	
	/**
	 * sets parameter list
	 */
	public void setParams(String params) {
		this.params = params;
	}
	
	/**
	 * pulls method name
	 * @return the name of the method
	 */
	public String getName() {
		return this.name;
	}
	
	/**
	 * pulls method return type
	 * @return method return type
	 */
	public String getReturnType() {
		return this.returnType;
	}
	
	/**
	 * pulls parameter list
	 * @return method parameter list
	 */
	public String getParams() {
		return "(" + this.params + ")";
	}
	
	@Override
	/**
	 * returns a string representation of a method
	 */
	public String toString() {
		return returnType + " " + name + "(" + params + ")";
		
	}
}