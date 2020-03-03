package gui;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.SwingUtilities;
import javax.swing.border.Border;

import resources.UMLClass;

public class GUIClass extends JPanel {
	// Class information
	private UMLClass umlClass;
	private UMLContainer container;
	
	// JPopup for class options
	private JPopupMenu mouseMenu;
	private JMenuItem mouseRemoveClass;
	private JMenuItem mouseAddField;
	private JMenuItem mouseRemoveField;
	private JMenuItem mouseAddMethod;
	private JMenuItem mouseRemoveMethod;
	
	public GUIClass(UMLClass umlClass, UMLContainer container) {
		// Set the class information
		this.umlClass = umlClass;
		this.container = container;
		
		// Set the layout to a vertical box layout to display information
		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		
		setAlignmentX(CENTER_ALIGNMENT);
		setAlignmentY(TOP_ALIGNMENT);
		
		add(new JLabel(umlClass.getName()));
		setBackground(Color.WHITE);
		
		// Set border and pad the box
		Border padding = BorderFactory.createEmptyBorder(10, 10, 10, 10);
		Border boxOutline = BorderFactory.createLineBorder(Color.BLACK);
		setBorder(BorderFactory.createCompoundBorder(boxOutline, padding));
		
		// Set the size of the class and set its bounds
		updateBounds();
		validate();
		
		// Set mouse menu
		mouseMenu = new JPopupMenu();
		mouseRemoveClass = new JMenuItem("Remove Class");
		mouseAddField = new JMenuItem("Add Field");
		mouseRemoveField = new JMenuItem("Remove Field");
		mouseAddMethod = new JMenuItem("Add Method");
		mouseRemoveMethod = new JMenuItem("Remove method");
		
		mouseMenu.add(mouseRemoveClass);
		mouseMenu.add(mouseAddField);
		mouseMenu.add(mouseRemoveField);
		mouseMenu.add(mouseAddMethod);
		mouseMenu.add(mouseRemoveMethod);
		
		// Add mouse listener for mouse menu
		addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				// Only display if the container was right clicked
				if(SwingUtilities.isRightMouseButton(e)) {
					mouseMenu.show(GUIClass.this, e.getX(), e.getY());
				}
			}
		});
		
		// Add events for mouse options
		mouseRemoveClass.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				container.removeClass(GUIClass.this);
			}
		});
	}
	
	/**
	 * Set the location of the box and update its bounds
	 */
	@Override
	public void setLocation(int x, int y) {
		super.setLocation(x, y);
		updateBounds();
	}
	
	public void updateBounds() {
		setSize(getPreferredSize());
		setBounds(getX(), getY(), getWidth(), getHeight());
	}
	
	/**
	 * Return the UMLClass instance
	 * @return umlClass
	 */
	public UMLClass getUMLClass() {
		return umlClass;
	}
}
