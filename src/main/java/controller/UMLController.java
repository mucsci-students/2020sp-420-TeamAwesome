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
	public abstract int addClass(String className, int x, int y);
	public abstract int removeClass(String className);
	public abstract int editClass(String oldClass, String newClass);
	
	public abstract int addField(String className, String fieldName, String type);
	public abstract int removeField(String className, String fieldName);
	public abstract int editField(String className, String oldField, String newField);
	
	public abstract int addMethod(String className, String returnType, String methodName, String params);
	public abstract int removeMethod(String className, String methodName, String params);
	public abstract int editMethod(String className, String oldMethod, String newMethod, String params);
	
	public abstract int addRelationship(String class1, String type, String class2);
	public abstract int removeRelationship(String class1, String type, String class2);
	public abstract int editRelationships(String originClass, String oldType, String destClass, String newType);
}
