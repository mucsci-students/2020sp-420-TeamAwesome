package gui;

import java.awt.Color;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.ArrayList;

import javax.swing.JPanel;

import resources.UMLClass;
import resources.UMLClassManager;

/**
 * JPanel where the representations of the classes and relationships will be displayed
 * @author Ryan
 */
public class UMLContainer extends JPanel implements MouseListener, MouseMotionListener {
	private GUIEnvironment guiEnvironment;
	private UMLClassManager classManager;
	
	// List of GUI classes
	private ArrayList<GUIClass> guiClasses;
	
	// Keep track of old mouse location
	private int oldMouseX;
	private int oldMouseY;
	
	/**
	 * Setup the drag area and drawing area
	 */
	public UMLContainer(GUIEnvironment guiEnvironment, UMLClassManager classManager) {
		this.guiEnvironment = guiEnvironment;
		this.classManager = classManager;
		
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
		addClass(umlClass, 0, 0);
	}
	
	/**
	 * Add a class at the given location
	 * @param umlClass - Reference UMLClass
	 * @param x - x coord
	 * @param y - y coord
	 */
	public void addClass(UMLClass umlClass, int x, int y) {
		GUIClass temp = new GUIClass(umlClass, this);
		temp.addMouseListener(this);
		temp.addMouseMotionListener(this);
		add(temp);
		revalidate();
		temp.updateBounds();
		temp.setLocation(x, y);
		guiClasses.add(temp);
	}

	/**
	 * Remove the class from class manager and GUI classes
	 * @param umlClass
	 */
	public void removeClass(GUIClass guiClass) {
		// Remove the class from the GUI
		remove(guiClass);
		
		// Remove the class from the list of GUIClasses
		guiClasses.remove(guiClass);
		
		// Remove the class from the classManager
		int result = classManager.removeClass(guiClass.getUMLClass().getName());
		if(result != 0)
			guiEnvironment.alertError(result);
		
		// Repaint the container to remove the left over panel drawing
		// 		from the screen.
		repaint();
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
