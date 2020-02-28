package gui;

import java.awt.Color;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.ArrayList;

import javax.swing.JPanel;

import resources.UMLClass;

/**
 * JPanel where the representations of the classes and relationships will be displayed
 * @author Ryan
 */
public class UMLContainer extends JPanel implements MouseListener, MouseMotionListener {
	// List of GUI classes
	private ArrayList<GUIClass> guiClasses;
	
	// Keep track of old mouse location
	private int oldMouseX;
	private int oldMouseY;
	
	/**
	 * Setup the drag area and drawing area
	 */
	public UMLContainer() {
		// Set the layout to null so we can drag
		setLayout(null);
		
		// Initialize list of GUIClasses
		guiClasses = new ArrayList<GUIClass>();
	}
	
	/**
	 * Add a class to the GUI class using the UML information
	 * @param umlClass - Reference UMLClass
	 */
	public void addClass(UMLClass umlClass) {
		GUIClass temp = new GUIClass(umlClass);
		temp.addMouseListener(this);
		temp.addMouseMotionListener(this);
		add(temp);
		guiClasses.add(temp);
	}
	
	@Override
	public void mousePressed(MouseEvent me) {
		// Get which object triggered the event
		Object source = me.getSource();
		
		// Loop through the list of classes to compare it against the source
		for(GUIClass guiClass : guiClasses) {
			if(source == guiClass) {
				oldMouseX = me.getLocationOnScreen().x - guiClass.getX();
				oldMouseY = me.getLocationOnScreen().y - guiClass.getY();
				
				break;
			}
		}
	}

	@Override
	public void mouseDragged(MouseEvent me) {
		// Get which object triggered the event
		Object source = me.getSource();
		
		// Loop through the list of classes to compare it against the source
		for(GUIClass guiClass : guiClasses) {
			if(source == guiClass) {
				guiClass.setLocation(me.getLocationOnScreen().x - oldMouseX, me.getLocationOnScreen().y - oldMouseY);
				
				oldMouseX = me.getLocationOnScreen().x - guiClass.getX();
				oldMouseY = me.getLocationOnScreen().y - guiClass.getY();
				
				break;
			}
		}
	}

	@Override
	public void mouseEntered(MouseEvent arg0) {}

	@Override
	public void mouseExited(MouseEvent arg0) {}

	@Override
	public void mouseClicked(MouseEvent arg0) {}

	@Override
	public void mouseReleased(MouseEvent arg0) {}

	@Override
	public void mouseMoved(MouseEvent arg0) {}
}
