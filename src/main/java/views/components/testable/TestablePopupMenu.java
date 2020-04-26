package views.components.testable;

import java.awt.Component;
import java.util.ArrayList;

import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;

public class TestablePopupMenu extends JPopupMenu {
	private static final long serialVersionUID = 1L;
	
	private String name;
	private ArrayList<JMenuItem> menuItems = new ArrayList<>();
	
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

	public void show(Component c, int mouseX, int mouseY) {}
	
	
}
