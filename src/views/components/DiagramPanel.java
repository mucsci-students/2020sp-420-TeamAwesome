// Package name
package views.components;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.HashMap;
import java.util.Map;

import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.SwingUtilities;

import controller.GUIController;

// System imports

import model.UMLClass;
import observe.Observable;
import observe.Observer;
import views.GUIView;

// Local imports

/**
 * A JPanel to display the UMLClasses and relationships
 * @author Ryan
 *
 */
public class DiagramPanel extends JPanel implements Observer, MouseListener, MouseMotionListener {
	// Instance of the Controller
	private GUIView view;
	
	// Map of graphical representations of classes
	private HashMap<String, GUIClass> guiClasses;
	
	// Location of the last class selected/dragged
	private int lastX;
	private int lastY;
	
	// Menus
	private JPopupMenu mouseMenu;
	private JPopupMenu classMenu;
	
	// MenuItems for generic mouse menu
	private JMenuItem mouseAddClass;
	
	// MenuItems for class mouse menu
	private JMenuItem classRemoveClass;
	private JMenuItem classAddField;
	private JMenuItem classRemoveField;
	private JMenuItem classAddMethod;
	private JMenuItem classRemoveMethod;
	private JMenuItem classAddRelationship;
	private JMenuItem classRemoveRelationship;
	
	// Mouse coordinates of the last time a right click was detected
	// 		as well as the last GUIClass that was selected, if one exists
	private int mouseX;
	private int mouseY;
	private GUIClass prev;
	
	public DiagramPanel(GUIView view) {
		this.view = view;
		
		// To enable dragging and dynamic locations, remove the layout manager
		setLayout(null);
		
		// Setup map of class names to guiClasses (very similar to class manager)
		guiClasses = new HashMap<String, GUIClass>();
		
		// Add mouse listener for clicks
		addMouseListener(this);
		
		setupMenus();
		setupActions();
	}
	
	/**
	 * Override paint component so we can draw in the relationships
	 */
	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
	}
	
	/**
	 * Setup the mouse and window menus and their items
	 */
	private void setupMenus() {
		// Create the popup menu for when a user right clicks in the diagram
		mouseMenu = new JPopupMenu();
		
		// Initialize the menu items
		mouseAddClass = new JMenuItem("Add Class");
		
		// Add menu items to mouse menu
		mouseMenu.add(mouseAddClass);
		
		// Create the popup menu for when a user right clicks a class
		// 		NOTE: Displaying this is handled in the GUIView's mouse listeners
		classMenu = new JPopupMenu();
		
		// Initialize class menu options
		classRemoveClass = new JMenuItem("Remove Class");
		
		// Add items to class menu
		classMenu.add(classRemoveClass);
	}
	
	/**
	 * Setup the actions for buttons
	 */
	private void setupActions() {
		// Setup actions for generic mouse menu items
		mouseAddClass.addActionListener(addClassAction());
		
		// Setup actions for class menu items
		classRemoveClass.addActionListener(removeClassAction());
	}
	
	/**
	 * Get an action listener that will add a class
	 * @return - ActionListener with definition for adding a class
	 */
	private ActionListener addClassAction() {
		return new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// Prompt the user for a class name
				String className = view.promptInput("Enter class name:");
				int result = view.getController().addClass(className, mouseX, mouseY);
				if(result != 0)
					view.showError(DiagramPanel.this, result);
			}
		};
	}
	
	/**
	 * Get an action listener that will remove a class
	 * @return - ActionListener with definition for adding a class
	 */
	private ActionListener removeClassAction() {
		return new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// Make sure a selected class exists
				if(prev != null) {
					int result = view.getController().removeClass(prev.getName());
					if(result != 0)
						view.showError(DiagramPanel.this, result);
				}
			}
		};
	}

	/**
	 * Listen for changes from the model
	 */
	@Override
	public void updated(Observable src, String tag, Object data) {
		// Check the action and react accordingly
		if(tag.equals("addClass")) {
			// Cast data as UMLClass
			UMLClass umlClass = (UMLClass)data;
			
			// Create GUIClass representation
			GUIClass temp = new GUIClass(umlClass);
			// Add a mouse listener to allow dragging and different options
			temp.addMouseListener(this);
			temp.addMouseMotionListener(this);
			guiClasses.put(umlClass.getName(), temp);
			
			// Add GUIClass to panel
			add(temp);
		}
		else if(tag.equals("removeClass")) {
			// Find the GUIClass that correlates to the removed object and delete it
			UMLClass removed = (UMLClass)data;
			remove(guiClasses.get(removed.getName()));
			guiClasses.remove(removed.getName());
		}
		
		// Update display
		validate();
		repaint();
	}
	
	@Override
	public void mousePressed(MouseEvent e) {
		// Destroy last saved guiClass;
		prev = null;
		
		// Get the source of the mouse event
		Object source = e.getSource();
		
		// Check if press is a left click
		if(SwingUtilities.isLeftMouseButton(e)) {
			// Loop through GUI classes and check if it triggered the event
			for(Map.Entry<String, GUIClass> entry : guiClasses.entrySet()) {
				GUIClass guiClass = entry.getValue();
				
				if(source == guiClass) {
					lastX = e.getLocationOnScreen().x - guiClass.getX();
					lastY = e.getLocationOnScreen().y - guiClass.getY();
				
					break;
				}
			}
		}
		// Check if press is a right click
		else if(SwingUtilities.isRightMouseButton(e)) {
			// Check if the source is the diagram, if so show the generic mouse menu
			if(source == this) {
				mouseX = e.getX();
				mouseY = e.getY();
				mouseMenu.show(this, mouseX, mouseY);
			}
			// Otherwise check if the source was a GUI Class
			else if(source instanceof GUIClass) {
				// Cast mouse source to GUIClass
				GUIClass guiClass = (GUIClass)e.getSource();
				mouseX = e.getX();
				mouseY = e.getY();
				prev = guiClass;
				classMenu.show(guiClass, mouseX, mouseY);
			}
		}
	}
	
	@Override
	public void mouseDragged(MouseEvent e) {
		// Get the source of the mouse event
		Object source = e.getSource();
		
		// Check if press is a left click
		if(SwingUtilities.isLeftMouseButton(e)) {
			// Loop through GUI classes and check if it triggered the event
			for(Map.Entry<String, GUIClass> entry : guiClasses.entrySet()) {
				GUIClass guiClass = entry.getValue();
				
				if(source == guiClass) {
					// Set location of GUIClass
					int newX = e.getLocationOnScreen().x - lastX;
					int newY = e.getLocationOnScreen().y - lastY;
					guiClass.setLocation(newX, newY);
					
					lastX = e.getLocationOnScreen().x - guiClass.getX();
					lastY = e.getLocationOnScreen().y - guiClass.getY();
				
					break;
				}
			}
		}
	}

	@Override
	public void mouseClicked(MouseEvent arg0) {}

	@Override
	public void mouseEntered(MouseEvent arg0) {}
	
	@Override
	public void mouseExited(MouseEvent arg0) {}

	@Override
	public void mouseReleased(MouseEvent arg0) {}

	@Override
	public void mouseMoved(MouseEvent arg0) {}
}
