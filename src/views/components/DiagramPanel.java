// Package name
package views.components;

import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.HashMap;
import java.util.Map;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

// System imports

import model.UMLClass;
import observe.Observable;
import observe.Observer;

// Local imports

/**
 * A JPanel to display the UMLClasses and relationships
 * @author Ryan
 *
 */
public class DiagramPanel extends JPanel implements Observer, MouseListener, MouseMotionListener {
	// Map of graphical representations of classes
	private HashMap<String, GUIClass> guiClasses;
	
	// Location of the last class selected/dragged
	private int lastX;
	private int lastY;
	
	public DiagramPanel() {
		// To enable dragging and dynamic locations, remove the layout manager
		setLayout(null);
		
		// Setup map of class names to guiClasses (very similar to class manager)
		guiClasses = new HashMap<String, GUIClass>();
	}
	
	/**
	 * Override paint component so we can draw in the relationships
	 */
	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
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
			
			validate();
			repaint();
		}
	}
	
	@Override
	public void mousePressed(MouseEvent e) {
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
