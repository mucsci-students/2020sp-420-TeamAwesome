// Package name
package views.components.testable;

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
	
	public String showInputDialog() {
		return null;
	}
}
