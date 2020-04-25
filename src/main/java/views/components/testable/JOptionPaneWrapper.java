package views.components.testable;

import java.awt.Component;

import javax.swing.Icon;
import javax.swing.JOptionPane;

public class JOptionPaneWrapper {
	/**
	 * Initialize instance of a testable JOptionPane
	 * @param desiredResult - as many values as you want the input dialog to return
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
