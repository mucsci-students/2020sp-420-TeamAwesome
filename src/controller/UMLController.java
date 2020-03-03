package controller;

import model.UMLClassManager;
import observe.Observable;

public abstract class UMLController extends Observable {
	protected UMLClassManager model;
	
	public UMLController(UMLClassManager model) {
		this.model = model;
	}
	
	public UMLClassManager getModel() {
		return model;
	}
	
	public abstract int addClass(String className);
	public abstract int removeClass(String className);
	
	public abstract int addField(String className, String fieldName);
	public abstract int removeField(String className, String fieldName);
	
	public abstract int addMethod(String className, String methodName);
	public abstract int removeMethod(String className, String methodName);
	
	public abstract int addRelationship(String class1, String class2);
	public abstract int removeRelationship(String class1, String class2);
}
