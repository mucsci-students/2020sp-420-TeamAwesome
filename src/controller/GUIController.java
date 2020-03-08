// Package name
package controller;

// System imports


// Local imports
import model.UMLClass;
import model.UMLClassManager;

/**
 * Controller for the GUIView
 * @author ryan
 *
 */
public class GUIController extends UMLController {
	public GUIController(UMLClassManager model) {
		super(model);
	}
	
	/**
	 *  Add class with specified location
	 */
	public int addClass(String className, int x, int y) {
		int result = model.addClass(className);
		if(result == 0) {
			// Set classes x and y location
			result = model.setClassLocation(className, x, y);
			
			// Notify observers of change
			notify("addClass", getModel().getClass(className));
		}
		return result;
	}

	/**
	 * Add class with default location at 0, 0
	 */
	@Override
	public int addClass(String className) {
		return addClass(className, 0, 0);
	}

	/**
	 * Remove specified class
	 */
	@Override
	public int removeClass(String className) {
		// Save instance of class
		UMLClass temp = model.getClass(className);
		int result = model.removeClass(className);
		if(result == 0) {
			// Notify observer of change
			notify("removeClass", temp);
		}
		return result;
	}

	@Override
	public int addField(String className, String fieldName) {
		int result = model.addFields(className, fieldName);
		if(result == 0) {
			// Notify observer of change
			notify("fieldChange", model.getClass(className));
		}
		return result;
	}

	@Override
	public int removeField(String className, String fieldName) {
		int result = model.removeFields(className, fieldName);
		if(result == 0) {
			// Notify observer of change
			notify("fieldChange", model.getClass(className));
		}
		return result;
	}

	@Override
	public int addMethod(String className, String methodName) {
		int result = model.addMethods(className, methodName);
		if(result == 0) {
			// Notify observer of change
			notify("methodChange", model.getClass(className));
		}
		return result;
	}

	@Override
	public int removeMethod(String className, String methodName) {
		int result = model.removeMethods(className, methodName);
		if(result == 0) {
			// Notify observer of change
			notify("methodChange", model.getClass(className));
		}
		return result;
	}

	@Override
	public int addRelationship(String class1, String class2) {
		int result = model.addRelationship(class1, class2);
		if(result == 0) {
			// Notify observer of change
			notify("relationshipChange", model);
		}
		return result;
	}

	@Override
	public int removeRelationship(String class1, String class2) {
		int result = model.removeRelationship(class1, class2);
		if(result == 0) {
			// Notify observer of change
			notify("relationshipChange", model);
		}
		return result;
	}

}
