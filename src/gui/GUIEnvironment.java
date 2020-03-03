package gui;

import java.awt.Color;
import java.awt.Dimension;

import javax.swing.JFrame;

import main.UMLEditor;
import resources.UMLClass;

/**
 * A GUI representation of the UMLEditor
 * 
 * @author Ryan
 */
public class GUIEnvironment {
	// Important environment variables
	private JFrame window;
	private UMLContainer umlContainer;
	
	// UML variables
	private UMLEditor umlEditor;
	
	/**
	 * Start the GUI and pass an instance of the Editor to the GUI so we can call commands
	 */
	public GUIEnvironment(UMLEditor umlEditor) {
		// Set the editor
		this.umlEditor = umlEditor;
		
		// Setup the window with title "UML Editor"
		window = new JFrame("UML Editor");
		
		// Create UMLContainer
		umlContainer = new UMLContainer();
		
		umlContainer.addClass(new UMLClass("temp"));
		
		// Set the initial size of the window
		window.setPreferredSize(new Dimension(600, 600));
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		// Add components to frame
		window.add(umlContainer);
		
		// Pack and display the window
		window.pack();
		window.setVisible(true);
	}
}
