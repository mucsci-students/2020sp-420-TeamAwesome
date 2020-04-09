// Package name
package core;

import controller.GUIController;
import controller.UMLController;
import model.UMLClassManager;

// System imports

// Local imports
import views.ConsoleView;
import views.GUIView;

/**
 * Driver program that will start the UMLEditor
 * @author ryan
 *
 */
public class UMLDriver {
	public static void main(String[] args) {
		// Check if GUI mode
		boolean gui = args.length > 0 && args[0].equals("gui");
		
		if(gui) {
			UMLClassManager model = new UMLClassManager();
			UMLController controller = new GUIController(model);
			new GUIView(controller, model).show();
		}
		else
			new ConsoleView().start();
	}
}
