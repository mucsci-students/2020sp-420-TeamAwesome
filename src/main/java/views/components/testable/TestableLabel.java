package views.components.testable;

import java.awt.Font;

import javax.swing.JLabel;

public class TestableLabel extends JLabel {
	private static final long serialVersionUID = 1L;
	private Font f;
	
	public String text;
	
	public TestableLabel(String text) {
		this.text = text;
	}
	
	public void setFont(Font f) {
		this.f = f;
	}
	
	public void setAlignmentX(int align) {};
	public void setAlignmentY(int align) {};
	
	public void setText(String text) {
		this.text = text;
	}
	
	public Font getFont() {
		return f;
	}
}
