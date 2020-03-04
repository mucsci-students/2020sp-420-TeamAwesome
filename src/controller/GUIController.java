// Package name
package controller;

// System imports

// Local imports
import model.UMLClassManager;
import views.components.DiagramPanel;

public class GUIController extends UMLController {
	public GUIController(UMLClassManager model) {
		super(model);
	}
	
	// Add class with specified location
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

	@Override
	public int addClass(String className) {
		return addClass(className, 0, 0);
	}

	@Override
	public int removeClass(String className) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int addField(String className, String fieldName) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int removeField(String className, String fieldName) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int addMethod(String className, String methodName) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int removeMethod(String className, String methodName) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int addRelationship(String class1, String class2) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int removeRelationship(String class1, String class2) {
		// TODO Auto-generated method stub
		return 0;
	}

}
