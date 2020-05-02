// Package name
package views;

// System imports
import java.awt.Dimension;
import java.io.File;

import javax.swing.JComponent;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.UIManager;
import javax.swing.filechooser.FileNameExtensionFilter;

//Local imports
import controller.UMLController;
import core.ErrorHandler;
import model.UMLClassManager;
import observe.Observable;
import views.components.DiagramPanel;
import views.components.testable.JOptionPaneWrapper;
import views.components.testable.TestableFileChooser;
import views.components.testable.TestableOptionPane;

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
	private JScrollPane scrollPane;
	private DiagramPanel umlDiagram;
	
	// Option pane
	private JOptionPaneWrapper optionPane;
	private TestableOptionPane testOP;
	
	// File chooser
	private JFileChooser fileChooser;
	
	private boolean isHuman;
	
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
		if(isHuman && setLook() != 0);

		// Setup controller and model
		this.controller = controller;
		this.model = model;
		this.controller.addObserver(this);
		
		this.isHuman = isHuman;

		// Setup options
		optionPane = new JOptionPaneWrapper();
		if(isHuman) {
			// Set to a regular file chooser
			setFileChooser(new JFileChooser());
			setupWindow();
		}
		// Set to a blank test pane otherwise (to avoid null pointers)
		else {
			setOptionPane(new TestableOptionPane(""));
			setFileChooser(new TestableFileChooser(new File("")));
		}
		
		setupDiagram();
		
		if(isHuman)
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
		window.setSize(new Dimension(600, 600));
		
		// Set the close button behavior to exit the program
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
	
	/**
	 * Initialize the diagram panel where the model will be represented
	 */
	private void setupDiagram() {
		// Setup a JPanel to display the classes and relationships
		umlDiagram = isHuman() ? new DiagramPanel(this, true) : new DiagramPanel(this, false);
		
		// Add the umlDiagram to the list of listeners for model changes
		controller.addObserver(umlDiagram);
		
		// Create scroll view
		scrollPane = new JScrollPane(umlDiagram);
		
		// Add the scroll pane to the frame
		if(isHuman())
			window.add(scrollPane);
	}
	
	/**
	 * Prompt the user for String input
	 * @param message - The message directing the user what to enter
	 * @return - User entered String
	 */
	public Object promptInput(String message) {
		// Prompt the user for input and return the input
		return optionPane.showInputDialog(window, message, testOP);
	}
	
	/**
	 * Prompt the user for input given selection
	 * @param message - The message directing the user what to enter
	 * @return - User entered String
	 */
	public Object promptSelection(String message, Object[] options) {
		// Prompt the user for input with a list of selections and return the input
		return optionPane.showInputDialog(window, message, "Selection", JOptionPane.QUESTION_MESSAGE, null, options, options[0], testOP);
	}
	
	/**
	 * Display an error message with the associated error
	 * @param parent - Parent component, can be null
	 * @param errorCode
	 */
	@SuppressWarnings("static-access")
	public void showError(JComponent parent, int errorCode) {
		// Create error message
		optionPane.showMessageDialog(parent, ErrorHandler.toString(errorCode), "Error", JOptionPane.ERROR_MESSAGE, testOP);
	}
	
	/**
	 * Open a JFileChooser and get a file from the user
	 * @param desc - description of the extension
	 * @param extension - the extension of the file, null if no filter
	 * @param title - the title of the windoer
	 * @param type - the type of chooser
	 * @return - The chosen file
	 */
	public File getFile(String desc, String extension, String title, int type) {
		// Set title of the chooser
		fileChooser.setDialogTitle(title);
		
		// Set extension filter to only allow extension
		// Make sure extension filter is not null and not empty
		if(desc != null && !desc.isEmpty() && extension != null && !extension.isEmpty())
			fileChooser.setFileFilter(new FileNameExtensionFilter(desc, extension));
		
		// Choose a file
		int result;
		if(type == JFileChooser.SAVE_DIALOG) {
			result = fileChooser.showSaveDialog(window);
		}
		else {
			result = fileChooser.showOpenDialog(window);
		}
		
		// Make sure input wasn't cancel
		if(result == JFileChooser.APPROVE_OPTION) {
			File file = fileChooser.getSelectedFile();
			return file;
		}
		
		return null;
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
	 * Get the view's model
	 * @return - model
	 */
	public UMLClassManager getModel() {
		return model;
	}
	
	/**
	 * Set the option pane for prompts
	 * @param pane - option pane instance
	 */
	public void setOptionPane(TestableOptionPane pane) {
		this.testOP = pane;
	}
	
	/**
	 * Set the file chooser for selecting files
	 * @param chooser - file chooser instance
	 */
	public void setFileChooser(JFileChooser chooser) {
		this.fileChooser = chooser;
	}

	@Override
	public void updated(Observable src, String tag, Object data) {
		
	}

	/**
	 * Get whether GUI is running in human or testable mode
	 * @return isHuman
	 */
	public boolean isHuman() {
		return isHuman;
	}

	/**
	 * Update the scroll pane to reflect dimension changes
	 */
	public void updateFrame() {
		scrollPane.revalidate();
	}
}
