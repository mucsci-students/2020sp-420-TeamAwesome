// Package name
package controller;

// System imports

// Local imports
import model.UMLClassManager;
import views.components.DiagramPanel;

public class GUIController extends UMLController {
	private DiagramPanel diagram;

	public GUIController(UMLClassManager model) {
		super(model);
	}
	
	/**
	 * Set the diagram to add visual classes to
	 * @param diagram - DiagramPanel instance
	 */
	public void setDiagram(DiagramPanel diagram) {
		
	}

	@Override
	public int addClass(String className) {
		// TODO Auto-generated method stub
		return 0;
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
