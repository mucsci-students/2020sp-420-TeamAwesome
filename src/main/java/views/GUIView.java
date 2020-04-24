// Package name
package views;

// System imports
import java.awt.Dimension;
import java.util.HashMap;

import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.UIManager;

//Local imports
import controller.UMLController;
import core.ErrorHandler;
import model.UMLClassManager;
import observe.Observable;
import views.components.DiagramPanel;
import views.components.GUIClass;

/**
 * A graphical view of the UML editor
 * @author Ryan
 *
 */
public class GUIView extends View {
	private UMLClassManager model;
	private UMLController controller;
	
	// Window elements
	private JFrame window;
	private DiagramPanel umlDiagram;
	
	/**
	 * Create a GUI view for a human
	 * @param controller
	 * @param model
	 */
	public GUIView(UMLController controller, UMLClassManager model) {
		this(controller, model, true);
	}
	
	/**
	 * Create a GUI view of the passed in model
	 * @param controller
	 * @param model
	 * @param isHuman
	 */
	public GUIView(UMLController controller, UMLClassManager model, boolean isHuman) {
		// Setup look and feel
		if(setLook() != 0);

		// Setup controller and model
		this.controller = controller;
		this.model = model;
		this.controller.addObserver(this);
		
		setupWindow();
		setupDiagram(isHuman);
		
		window.pack();
	}
	
	/**
	 * Set the view to be visible
	 */
	public void show() {
		window.setVisible(true);
	}
	
	/**
	 * Setup the GUI window with some default properties
	 */
	private void setupWindow() {
		// Initialize frame with title
		window = new JFrame("UML Editor");
		
		// Set the initial size of the window
		window.setPreferredSize(new Dimension(600, 600));
		
		// Set the close button behavior to exit the program
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
	
	/**
	 * Initialize the diagram panel where the model will be represented
	 */
	private void setupDiagram(boolean isHuman) {
		// Setup a JPanel to display the classes and relationships
		umlDiagram = new DiagramPanel(this, isHuman);
		
		// Add the umlDiagram to the list of listeners for model changes
		controller.addObserver(umlDiagram);
		
		// Add the diagram to the frame
		window.add(umlDiagram);
	}
	
	/**
	 * Prompt the user for String input
	 * @param message - The message directing the user what to enter
	 * @return - User entered String
	 */
	public Object promptInput(String message) {
		// Prompt the user for input and return the input
		return JOptionPane.showInputDialog(window, message);
	}
	
	/**
	 * Prompt the user for input given selection
	 * @param message - The message directing the user what to enter
	 * @return - User entered String
	 */
	public Object promptSelection(String message, Object[] options) {
		// Prompt the user for input with a list of selections and return the input
		return JOptionPane.showInputDialog(window, message, "Selection", JOptionPane.QUESTION_MESSAGE, null, options, options[0]);
	}
	
	/**
	 * Display an error message with the associated error
	 * @param parent - Parent component, can be null
	 * @param errorCode
	 */
	public void showError(JComponent parent, int errorCode) {
		// Create error message
		JOptionPane.showMessageDialog(parent, ErrorHandler.toString(errorCode), "Error", JOptionPane.ERROR_MESSAGE);
	}
	
	/**
	 * Get the instance of the controller
	 * @return
	 */
	public UMLController getController() {
		return controller;
	}
	
	/**
	 * Try to set the GUI look and feel to match the OS
	 * @return - return code, 0 if successful
	 */
	private int setLook() {
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch(Exception e) {
			return ErrorHandler.setCode(110);
		}
		return ErrorHandler.setCode(0);
	}
	
	/**
	 * Get the component that has the given name
	 * @param name - name of the component
	 * @return - associated JComponent
	 */
	public JComponent getComponent(String name) {
		return (JComponent)umlDiagram.findComponent(name);
	}
	
	/**
	 * Get the component at the specified location
	 * @param x - x coordinate contained in component
	 * @param y - y coordinate contained in component
	 * @return - associated JComponent
	 */
	public JComponent getComponent(int x, int y) {
		return (JComponent)umlDiagram.getComponentAt(x, y);
	}
	
	/**
	 * Get the entire window instance.
	 * @return - window JFrame
	 */
	public JFrame getWindow() {
		return window;
	}
	
	/**
	 * Get the instance of the diagram panel
	 * @return - diagramPanel
	 */
	public DiagramPanel getDiagram() {
		return umlDiagram;
	}
	
	/**
	 * Load a list of data to load into the diagram to substitute human input.
	 * Note - Will only be taken into consideration when GUI is set to non-human mode
	 * @param data - Data to load for actions
	 */
	public void loadData(String[] data) {
		umlDiagram.setData(data);
	}
	
	/**
	 * Return the map of added GUIClasses
	 * @return
	 */
	public HashMap<String, GUIClass> getGUIClasses() {
		return umlDiagram.getGuiClasses();
	}
	
	/**
	 * Get the view's model
	 * @return - model
	 */
	public UMLClassManager getModel() {
		return model;
	}

	@Override
	public void updated(Observable src, String tag, Object data) {
		
	}
}
