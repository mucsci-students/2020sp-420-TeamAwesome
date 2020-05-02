// Package name
package views.components.testable;

// System imports
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JMenuItem;

/**
 * Testable version of JMenuItem
 * @author Ryan
 *
 */
public class TestableMenuItem extends JMenuItem {
	private static final long serialVersionUID = 1L;
	
	private String name;
	
	// Store action to perform
	private ActionListener action;
	
	public void addActionListener(ActionListener action) {
		this.action = action;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public void doClick() {
		action.actionPerformed(new ActionEvent(this, 1, null));
	}
	
	public String getName() {
		return name;
	}
}
