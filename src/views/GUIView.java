// Package name
package views;

// System imports
import java.awt.Dimension;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.UIManager;

//Local imports
import controller.UMLController;
import controller.GUIController;
import main.ErrorHandler;
import main.UMLFileIO;
import model.UMLClassManager;
import observe.Observable;
import views.components.DiagramPanel;

/**
 * A graphical view of the UML editor
 * @author Ryan
 *
 */
public class GUIView extends View {
	private UMLController controller;
	private UMLFileIO fileIO;
	
	// Window elements
	private JFrame window;
	private DiagramPanel umlDiagram;
	
	public GUIView() {
		// Setup look and feel
		if(setLook() != 0);
		
		// Initialize file IO
		fileIO = new UMLFileIO();

		// Setup controller
		controller = new GUIController(new UMLClassManager());
		controller.addObserver(this);
		
		setupWindow();
		setupDiagram();
		
		window.pack();
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
	private void setupDiagram() {
		// Setup a JPanel to display the classes and relationships
		umlDiagram = new DiagramPanel(this);
		
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
	public String promptInput(String message) {
		// Prompt the user for input and return the input
		return JOptionPane.showInputDialog(window, message);
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
			return 110;
		}
		return 0;
	}

	@Override
	public void updated(Observable src, String tag, Object data) {
		
	}
}
