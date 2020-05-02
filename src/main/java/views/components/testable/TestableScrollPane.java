package views.components.testable;

import java.awt.Component;

import javax.swing.JScrollPane;

public class TestableScrollPane extends JScrollPane {
	public Component add(Component c) {
		System.out.println("call");
		return c;
	}
}
