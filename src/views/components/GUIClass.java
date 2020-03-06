// Package Name
package views.components;

import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
// System imports
import javax.swing.JPanel;
import javax.swing.JSeparator;
import javax.swing.SwingConstants;
import javax.swing.border.Border;

import model.UMLClass;

//Local imports

/**
 * A GUI representation of a UML Class
 * @author Ryan
 *
 */
public class GUIClass extends JPanel {
	// Instance of UMLClass
	private UMLClass umlClass;
	
	// Title of class
	private JLabel className;

	// Class Properties
	//		- Maps name to the corresponding label
	private HashMap<String, JLabel> fieldLabels;
	private HashMap<String, JLabel> methodLabels;
	
	// Store separators to toggle visibility
	private JSeparator fieldSeparator;
	private JSeparator methodSeparator;
	
	// Regions
	private JPanel fieldRegion;
	private JPanel methodRegion;
	
	/**
	 * Initialize a graphical view of a given UMLClass
	 * @param umlClass
	 */
	public GUIClass(UMLClass umlClass) {
		this.umlClass = umlClass;
		
		// Initialize label maps
		fieldLabels = new HashMap<String, JLabel>();
		methodLabels = new HashMap<String, JLabel>();
		
		// Set layout to be a vertical box layout
		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		
		// Initialize region panels, with a vertical BoxLayout
		fieldRegion = new JPanel();
		fieldRegion.setLayout(new BoxLayout(fieldRegion, BoxLayout.Y_AXIS));
		methodRegion = new JPanel();
		methodRegion.setLayout(new BoxLayout(methodRegion, BoxLayout.Y_AXIS));
		
		// Add a label of the class name
		className = generateLabel(umlClass.getName());
		className.setFont(className.getFont().deriveFont(Font.BOLD));
		add(className);
		
		// Initialize separators
		fieldSeparator = generateSeparator(Color.BLACK);
		methodSeparator = generateSeparator(Color.BLACK);
		
		// Add separators and regions
		add(fieldSeparator);
		add(fieldRegion);
		add(methodSeparator);
		add(methodRegion);
		
		// Set visibility of separators
		fieldSeparator.setVisible(false);
		methodSeparator.setVisible(false);
		
		// Set the location given the classes location
		setLocation(umlClass.getX(), umlClass.getY());
		
		// Add padding and a border
		Border padding = BorderFactory.createEmptyBorder(10, 10, 10, 10);
		Border outline = BorderFactory.createLineBorder(Color.BLACK, 2);
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
	
	/**
	 * Generate a centered JLabel
	 * @param text - The label text
	 * @return - JLabel instance
	 */
	public static JLabel generateLabel(String text) {
		JLabel temp = new JLabel(text);
		temp.setAlignmentX(Component.CENTER_ALIGNMENT);
		temp.setAlignmentY(Component.TOP_ALIGNMENT);
		return temp;
	}
	
	/**
	 * Generate a horizontal separator
	 * @return - JSeparator instance
	 */
	public static JSeparator generateSeparator(Color c) {
		JSeparator mySep = new JSeparator(SwingConstants.HORIZONTAL);
		mySep.setAlignmentY(TOP_ALIGNMENT);
		mySep.setBorder(BorderFactory.createMatteBorder(2, 2, 2, 2, c));
		return mySep;
	}
	
	/**
	 * Check to make sure the method labels match the class representation.
	 * This should be called every time the DiagramPanel is notified of a class change
	 */
	public void updateMethods() {
		// Loop through list of class methods
		
		// Check to see if there is a method in the class that is not a label
		//		If so then add it to the panel
		for(String methodName : umlClass.getMethods()) {
			if(!methodLabels.containsKey(methodName)) {
				JLabel temp = generateLabel(methodName);
				methodLabels.put(methodName, temp);
				
				// Add label to display
				methodRegion.add(temp);
			}
		}
		
		// Check to see if there is a label that is not in the class
		//		If so then remove it from the panel
		// Map iterator
		Iterator<Map.Entry<String, JLabel>> entryIt = methodLabels.entrySet().iterator();
		
		// Iterate over elements
		while(entryIt.hasNext()) {
			// Get entry
			Map.Entry<String, JLabel> entry = entryIt.next();
			
			// Get the label name
			String name = entry.getKey();
			// Check if label is not in class list
			if(!umlClass.hasMethod(name)) {
				// Remove label from display
				methodRegion.remove(entry.getValue());
				
				// Remove method
				entryIt.remove();
			}
		}
		
		// Check if need to change field separator visibility
		if(methodLabels.size() > 0 && !methodSeparator.isVisible())
			methodSeparator.setVisible(true);
		else if(methodLabels.size() == 0 && methodSeparator.isVisible())
			methodSeparator.setVisible(false);
		
		validate();
		repaint();
		updateBounds();
	}
	
	/**
	 * Check to make sure the field labels match the class representation.
	 * This should be called every time the DiagramPanel is notified of a class change
	 */
	public void updateFields() {
		// Loop through list of class methods
		
		// Check to see if there is a field in the class that is not a label
		//		If so then add it to the panel
		for(String fieldName : umlClass.getFields()) {
			if(!fieldLabels.containsKey(fieldName)) {
				JLabel temp = generateLabel(fieldName);
				System.out.println("Putting: " + fieldName);
				fieldLabels.put(fieldName, temp);
				
				// Add label to display
				fieldRegion.add(temp);
			}
		}
		
		// Check to see if there is a label that is not in the class
		//		If so then remove it from the panel
		// Map iterator
		Iterator<Map.Entry<String, JLabel>> entryIt = fieldLabels.entrySet().iterator();
		
		// Iterate over elements
		while(entryIt.hasNext()) {
			// Get entry
			Map.Entry<String, JLabel> entry = entryIt.next();
			
			// Get the label name
			String name = entry.getKey();
			// Check if label is not in class list
			if(!umlClass.hasField(name)) {
				// Remove label from display
				fieldRegion.remove(entry.getValue());
				
				// Remove field
				entryIt.remove();
			}
		}
		
		// Check if need to change field separator visibility
		if(fieldLabels.size() > 0 && !fieldSeparator.isVisible())
			fieldSeparator.setVisible(true);
		else if(fieldLabels.size() == 0 && fieldSeparator.isVisible())
			fieldSeparator.setVisible(false);
		
		validate();
		repaint();
		updateBounds();
	}
	
	/**
	 * Get the name of the represented class
	 * @return - UMLClass name
	 */
	public String getName() {
		return umlClass.getName();
	}
}
