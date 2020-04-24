// Package name
package views.components.testable;

import java.awt.Component;

import javax.swing.Icon;
// System imports
import javax.swing.JOptionPane;

// Local imports

/**
 * Mock JOptionPane for testing purposes
 * @author Ryan
 */
public class TestableOptionPane extends JOptionPane {
	private String result;
	
	/**
	 * Initialize instance of a testable JOptionPane
	 * @param desiredResult - the result you want for the option pane
	 */
	public TestableOptionPane(String desiredResult) {
		result = desiredResult;
	}
	
	public String showInputDialog(Component parent, String message) {
		return result;
	}
	
	 public String showInputDialog(Component parentComponent, String message, 
			 String title, int messageType, Icon icon, Object[] selectionValues, Object initialSelectionValue) {
		 return result;
	 }
}