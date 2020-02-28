package gui;

import java.awt.Color;
import java.awt.Graphics;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.Border;

import resources.UMLClass;

public class GUIClass extends JPanel {
	// Class information
	private UMLClass umlClass;
	
	public GUIClass(UMLClass umlClass) {
		// Set the class information
		this.umlClass = umlClass;
		
		// Set the layout to a vertical box layout to display information
		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		
		setAlignmentX(CENTER_ALIGNMENT);
		setAlignmentY(TOP_ALIGNMENT);
		
		add(new JLabel(umlClass.getName()));
		setBackground(Color.RED);
		
		// Set border and pad the box
		Border padding = BorderFactory.createEmptyBorder(10, 10, 10, 10);
		Border boxOutline = BorderFactory.createLineBorder(Color.BLACK);
		setBorder(BorderFactory.createCompoundBorder(boxOutline, padding));
		
		// Set the size of the class and set its bounds
		setSize(getPreferredSize());
		setBounds(getX(), getY(), getWidth(), getHeight());
	}
	
	/**
	 * Set the location of the box and update its bounds
	 */
	@Override
	public void setLocation(int x, int y) {
		super.setLocation(x, y);
		setBounds(x, y, getWidth(), getHeight());
	}
}
