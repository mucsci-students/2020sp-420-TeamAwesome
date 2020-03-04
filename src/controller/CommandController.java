package controller;

import model.UMLClassManager;

public class CommandController extends UMLController {
	public CommandController(UMLClassManager model) {
		super(model);
	}
	
	public UMLClassManager getModel() {
		return model;
	}

	public int addClass(String className) {
		int result = getModel().addClass(className);
		if(result == 0)
			notify("addClass", getModel());
		return result;
	}

	public int removeClass(String className) {
		int result = getModel().removeClass(className);
		if(result == 0)
			notify("removeClass", getModel());
		return result;
	}

	public int addField(String className, String fieldName) {
		int result = getModel().addFields(className, fieldName);
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

	public int addMethod(String className, String methodName) {
		int result = getModel().addMethods(className, methodName);
		if(result == 0)
			notify("addMethod", getModel());
		return result;
	}

	public int removeMethod(String className, String methodName) {
		int result = getModel().removeMethods(className, methodName);
		if(result == 0)
			notify("removeMethod", getModel());
		return result;
	}

	public int addRelationship(String class1, String class2) {
		int result = getModel().addRelationship(class1, class2);
		if(result == 0)
			notify("addRelationship", getModel());
		return result;
	}

	public int removeRelationship(String class1, String class2) {
		int result = getModel().removeRelationship(class1, class2);
		if(result == 0)
			notify("removeRelationship", getModel());
		return result;
	}

}
