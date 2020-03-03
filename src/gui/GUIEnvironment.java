package gui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JFrame;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPopupMenu;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import main.ErrorHandler;
import main.UMLEditor;
import resources.UMLClass;
import resources.UMLClassManager;
import tests.ClassManagerTests;

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
	private UMLClassManager classManager;
	
	// Mouse menu to enable right click menu
	private JPopupMenu mouseMenu;
	
	// Mouse menu options
	private JMenuItem mouseAddClass;
	
	// Mouse coords
	private int mouseX;
	private int mouseY;
	
	/**
	 * Start the GUI and pass an instance of the Editor to the GUI so we can call commands
	 */
	public GUIEnvironment(UMLEditor umlEditor) {
		// Set look and feel to that of the OS
		int result = setUILook();
		if(result != 0)
			alertError(result);
		
		// Set the editor
		this.classManager = umlEditor.getClassManager();
		
		// Setup the window with title "UML Editor"
		window = new JFrame("UML Editor");
		
		// Create UMLContainer for dragging
		umlContainer = new UMLContainer(this, classManager);
		
		// Setup mouse menu
		mouseMenu = new JPopupMenu();
		mouseAddClass = new JMenuItem("Add Class");
		
		mouseMenu.add(mouseAddClass);
		
		// Add listener for the container to display mouse menu
		umlContainer.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				// Only display if the container was right clicked
				if(SwingUtilities.isRightMouseButton(e)) {
					mouseX = e.getX();
					mouseY = e.getY();
					mouseMenu.show(umlContainer, e.getX(), e.getY());
				}
			}
		});
		
		// Add listeners to mouse options
		mouseAddClass.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// Prompt user for class name
				String newClassName = promptInput("Enter the new class name:");
				// Try and add the class
				int localResult = classManager.addClass(newClassName);
				
				// If adding fails, inform user
				if(localResult != 0) {
					alertError(localResult);
				}
				// Otherwise add class to GUI
				else {
					umlContainer.addClass(classManager.getClass(newClassName), mouseX, mouseY);
				}
			}
		});
		
		// Set the initial size of the window
		window.setPreferredSize(new Dimension(600, 600));
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		// Add components to frame
		window.add(umlContainer);
		
		// Pack and display the window
		window.pack();
		window.setVisible(true);
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
	 * Display an alert box indicating an error occured with a description of the error
	 * @param errorCode - the given error code
	 */
	public void alertError(int errorCode) {
		// Create error message dialog
		JOptionPane.showMessageDialog(umlContainer, ErrorHandler.toString(errorCode), "Error", JOptionPane.ERROR_MESSAGE);
	}
	
	/**
	 * Set the look of the GUI to match the system
	 */
	private int setUILook() {
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch(Exception e) {
			return 110;
		}
		
		return 0;
	}
}
