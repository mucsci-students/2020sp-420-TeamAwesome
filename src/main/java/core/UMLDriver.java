// Package name
package core;

import controller.CommandController;
import controller.GUIController;
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
		
		// Create model
		UMLClassManager model = new UMLClassManager();
		
		if(gui) {
			new GUIView(new GUIController(model), model).show();
		}
		else
			new ConsoleView(model, new CommandController(model)).start();
	}
}
