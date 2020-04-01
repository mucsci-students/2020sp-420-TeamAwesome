package controller;

import model.UMLClassManager;

public class CommandController extends UMLController {
	public CommandController(UMLClassManager model) {
		super(model);
	}
	
	public UMLClassManager getModel() {
		return model;
	}
	
	@Override
	public int addClass(String className, int x, int y) {
		int result = getModel().addClass(className);
		if(result == 0) {
			result = getModel().setClassLocation(className, x, y);
			
			notify("addClass", getModel());
		}
		return result;
	}
	
	public int editClass(String className, String newName) {
		int result = getModel().editClass(className, newName);
		if(result == 0)
			notify("editClass", getModel());
		return result;
	}
	
	public int editField(String className, String oldName, String newName) {
		int result = getModel().editFields(className, oldName, newName);
		if(result == 0)
			notify("editField", getModel());
		return result;
	}
	
	public int editMethod(String className, String oldName, String params, String newName) {
		int result = getModel().editMethods(className, oldName, params, newName);
		if(result == 0)
			notify("editClass", getModel());
		return result;
	}

	public int addClass(String className) {
		return addClass(className, 0, 0);
	}	

	public int removeClass(String className) {
		int result = getModel().removeClass(className);
		if(result == 0)
			notify("removeClass", getModel());
		return result;
	}

	public int addField(String className, String type, String fieldName) {
		int result = getModel().addFields(className, type, fieldName);
		if(result == 0)
			notify("addField", getModel());
		return result;
	}

	public int removeField(String className, String fieldName) {
		int result = getModel().removeFields(className, fieldName);
		if(result == 0)
			notify("removeField", getModel());
		return result;
	}

	public int addMethod(String className, String returnType, String methodName, String params) {
		int result = getModel().addMethods(className, returnType, methodName, params);
		if(result == 0)
			notify("addMethod", getModel());
		return result;
	}

	public int removeMethod(String className, String methodName, String params) {
		int result = getModel().removeMethods(className, methodName, params);
		if(result == 0)
			notify("removeMethod", getModel());
		return result;
	}

	public int addRelationship(String class1, String type, String class2) {
		int result = getModel().addRelationship(class1, type, class2);
		if(result == 0)
			notify("addRelationship", getModel());
		return result;
	}

	public int removeRelationship(String class1, String type, String class2) {
		int result = getModel().removeRelationship(class1, type, class2);
		if(result == 0)
			notify("removeRelationship", getModel());
		return result;
	}

}
