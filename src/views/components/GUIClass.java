// Package Name
package views.components;

import java.awt.Color;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
// System imports
import javax.swing.JPanel;
import javax.swing.border.Border;

import model.UMLClass;

//Local imports

/**
 * A GUI representation of a UML Class
 * @author Ryan
 *
 */
public class GUIClass extends JPanel {
	private UMLClass umlClass;
	
	/**
	 * Initialize a graphical view of a given UMLClass
	 * @param umlClass
	 */
	public GUIClass(UMLClass umlClass) {
		this.umlClass = umlClass;
		
		// Add a label of the class name
		add(new JLabel(umlClass.getName()));
		
		// Set the location given the classes location
		setLocation(umlClass.getX(), umlClass.getY());
		
		// Add padding and a border
		Border padding = BorderFactory.createEmptyBorder(10, 10, 10, 10);
		Border outline = BorderFactory.createLineBorder(Color.BLACK);
		setBorder(BorderFactory.createCompoundBorder(outline, padding));
		
		// Update the box bounds
		updateBounds();
	}
	
	/**
	 * Update the size and bounds of the box
	 */
	public void updateBounds() {
		setSize(getPreferredSize());
		setBounds(getX(), getY(), getWidth(), getHeight());
	}
	
	/**
	 * Set the location of the class
	 */
	public void setLocation(int x, int y) {
		super.setLocation(x, y);
		updateBounds();
		umlClass.setLocation(x, y);
	}
}
