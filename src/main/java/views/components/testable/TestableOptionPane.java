// Package name
package views.components.testable;

// System imports
import java.awt.Component;
import java.util.Collections;
import java.util.LinkedList;
import java.util.Queue;
import javax.swing.Icon;

// Local imports

/**
 * Mock JOptionPane for testing purposes. Adds return values to a queue and pops them off as the methods are called
 * @author Ryan
 * @implNote - Can't make methods return Object because that means everything would have to be static
 */
public class TestableOptionPane {
	// List of results
	// Use a queue in the event testing needs to send in multiple pieces of data
	// I.E. adding methods has several prompts in one call
	private Queue<String> results;
	
	/**
	 * Initialize instance of a testable JOptionPane
	 * @param desiredResult - as many values as you want the input dialog to return
	 */
	public TestableOptionPane(String... desiredResults) {
		results = new LinkedList<String>();
		// Add all desired results to the queue
		Collections.addAll(results, desiredResults);
	}
	
	public String showInputDialog(Component parent, Object message) {
		// Pop off the first return value
		return results.remove();
	}
	
	 public String showInputDialog(Component parentComponent, Object message, 
			 String title, int messageType, Icon icon, Object[] selectionValues, Object initialSelectionValue) {
		 return results.remove();
	 }
	 
	 public static void showMessageDialog(Component parentComponent, Object message, String title, int messageType) {}
}