// Package name
package main;

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
		
		if(gui)
			new GUIView();
		else
			new ConsoleView().start();
	}
}
