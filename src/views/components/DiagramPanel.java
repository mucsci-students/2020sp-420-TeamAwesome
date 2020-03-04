// Package name
package views.components;

import java.awt.Color;
import java.awt.Graphics;

// System imports
import javax.swing.JPanel;

import observe.Observable;
import observe.Observer;

// Local imports

/**
 * A JPanel to display the UMLClasses and relationships
 * @author Ryan
 *
 */
public class DiagramPanel extends JPanel implements Observer {
	public DiagramPanel() {
		// To enable dragging and dynamic locations, remove the layout manager
		setLayout(null);
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
		// TODO Auto-generated method stub
		
	}
}
