// Package name
package views.components.testable;

// System imports
import java.awt.Component;
import java.util.ArrayList;

import javax.swing.JMenu;
import javax.swing.JMenuItem;

/**
 * Testable version of a JMenu
 * @author Ryan
 *
 */
public class TestableMenu extends JMenu {
	private static final long serialVersionUID = 1L;
	
	private String name;
	// Manually store menu items
	private ArrayList<JMenuItem> menuItems = new ArrayList<>();
	
	public TestableMenu() {}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public JMenuItem add(JMenuItem menuItem) {
		menuItems.add(menuItem);
		return menuItem;
	}
	
	public void addSeparator() {
		menuItems.add(null);
	}
	
	public String getName() {
		return name;
	}
	
	public Component[] getComponents() {
		Component[] comps = new Component[menuItems.size()];
		for(int i = 0; i < comps.length; i++)
			comps[i] = menuItems.get(i);
		return comps;
	}
	
	public int getItemCount() {
		return menuItems.size();
	}
	
	public JMenuItem getItem(int index) {
		return menuItems.get(index);
	}
}
