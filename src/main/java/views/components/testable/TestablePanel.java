package views.components.testable;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.LayoutManager;
import java.awt.event.MouseListener;
import java.util.ArrayList;

import javax.swing.JPanel;
import javax.swing.border.Border;

public class TestablePanel extends JPanel {
	private static final long serialVersionUID = 1L;
	
	private ArrayList<Component> components = new ArrayList<Component>();
	private boolean isHuman = false;
	
	// This constructor should only be called if you want a regular JPanel
	public TestablePanel(boolean isHuman) {
		super();
		this.isHuman = isHuman;
	}
	
	public void setLayout(LayoutManager mgr) {
		if(isHuman)
			super.setLayout(mgr);
	}
	
	public void paintComponent(Graphics g) {
		if(isHuman)
			super.paintComponent(g);
	}
	
	public void addMouseListener(MouseListener l) {
		if(isHuman)
			super.addMouseListener(l);
	}
	
	public Component findComponentAt(int x, int y) {
		if(isHuman)
			return super.findComponentAt(x, y);
		return null;
	}
	
	public void validate() {
		if(isHuman)
			super.validate();
	}
	
	public void repaint() {
		if(isHuman)
			super.repaint();
	}
	
	public void paintAll(Graphics g) {
		if(isHuman)
			super.paintAll(g);
	}
	
	public int getWidth() {
		if(isHuman)
			return super.getWidth();
		return 0;
	}
	
	public int getHeight() {
		if(isHuman)
			return super.getHeight();
		return 0;
	}
	
	public Component add(Component comp) {
		if(isHuman) {
			return super.add(comp);
		}
		components.add(comp);
		return comp;
	}
	
	public void remove(Component comp) {
		if(isHuman)
			super.remove(comp);
		else
			components.remove(comp);
	}
	
	public Component[] getComponents() {
		if(isHuman)
			return super.getComponents();
		
		Component[] comps = new Component[components.size()];
		for(int i = 0; i < comps.length; i++)
			comps[i] = components.get(i);
		return comps;
	}
	
	public void setVisible(boolean bool) {
		if(isHuman)
			super.setVisible(bool);
	}
	
	public void setLocation(int x, int y) {
		if(isHuman)
			super.setLocation(x, y);
	}
	
	public void setBorder(Border b) {
		if(isHuman)
			super.setBorder(b);
	}
	
	public void setSize(Dimension size) {
		if(isHuman)
			super.setSize(size);
	}
	
	public void setBounds(int x, int y, int width, int height) {
		if(isHuman)
			super.setBounds(x, y, width, height);
	}
	
	public Graphics getGraphics() {
		if(isHuman)
			return super.getGraphics();
		return null;
	}
	
	public void paint(Graphics g) {
		if(isHuman)
			super.paint(g);
	}
	
	public void paintChildren(Graphics g) {
		if(isHuman)
			super.paintChildren(g);
	}
	
	public void paintComponents(Graphics g) {
		if(isHuman)
			super.paintComponents(g);
	}
	
	public Graphics getComponentGraphics(Graphics g) {
		return super.getComponentGraphics(g);
	}
	
	public void setSize(int width, int height) {
		if(isHuman)
			super.setSize(width, height);
	}
	
	public void setPreferredSize(Dimension d) {
		if(isHuman)
			super.setPreferredSize(d);
	}
}
