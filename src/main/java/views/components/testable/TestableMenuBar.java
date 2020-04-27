package views.components.testable;

import java.util.ArrayList;

import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.plaf.MenuBarUI;

public class TestableMenuBar extends JMenuBar {
	private static final long serialVersionUID = 1L;
	
	private String name;
	private ArrayList<JMenu> menus = new ArrayList<JMenu>();
	
	public TestableMenuBar() {
		menus = new ArrayList<JMenu>();
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public String getName() {
		return name;
	}

	public JMenu add(JMenu menu) {
		menus.add(menu);
		return menu;
	}
	
	public int getMenuCount() {
		return menus.size();
	}
	
	public JMenu getMenu(int index) {
		return menus.get(index);
	}

	@Override
	public void setUI(MenuBarUI ui) {
	}

	@Override
	public void updateUI() {
	}
	
	
}
