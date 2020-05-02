// Package name
package views.components.testable;

// System imports
import java.awt.Component;
import javax.swing.Icon;
import javax.swing.JOptionPane;

/**
 * Wrapper class for JOptionPane because JOptionPane's 'show' methods can't be overridden
 * @author Ryan
 *
 */
public class JOptionPaneWrapper {
	
	/**
	 * Show an input dialog
	 * @param parent - Parent component
	 * @param message - Message to present
	 * @param top - A TestableOptionPane for testing, null if human
	 * @return - User entered data
	 */
	public String showInputDialog(Component parent, Object message, TestableOptionPane top) {
		if(top == null)
			return JOptionPane.showInputDialog(parent, message);
		return top.showInputDialog(parent, message);
	}
	
	 public Object showInputDialog(Component parentComponent, Object message, 
			 String title, int messageType, Icon icon, Object[] selectionValues, 
			 Object initialSelectionValue, TestableOptionPane top) {
		 if(top == null)
			 return JOptionPane.showInputDialog(parentComponent, message, title, messageType, icon, selectionValues, initialSelectionValue);
		 return top.showInputDialog(parentComponent, message, title, messageType, icon, selectionValues, initialSelectionValue);
	 }
	 
	 public static void showMessageDialog(Component parentComponent, Object message, String title, 
			 int messageType, TestableOptionPane top) {
		 if(top == null)
			 JOptionPane.showMessageDialog(parentComponent, message, title, messageType);
	 }
}
