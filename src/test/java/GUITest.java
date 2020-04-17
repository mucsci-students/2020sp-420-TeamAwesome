// System imports
import org.junit.Test;

import controller.GUIController;
import core.ErrorHandler;
import model.UMLClass;
import model.UMLClassManager;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;
import java.awt.Component;
import javax.swing.JComponent;
import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;
import javax.swing.JSeparator;

// Local imports
import views.GUIView;

/**
 * Class to run JUnit tests on the GUI
 * 
 * @author Ryan
 *
 */
public class GUITest {
	/**
	 * Create the GUI environment and the environment is initialized
	 */
	@Test
	public void baseInitialization() {
		UMLClassManager model = new UMLClassManager();
		GUIView gui = new GUIView(new GUIController(model), model, false);
		assertTrue("Window not null", gui.getWindow() != null);
		assertTrue("Controller not null", gui.getController() != null);
		assertTrue("Diagram exists", gui.getDiagram() != null);
	}
	
	/**
	 * Ensure the initialization of the mouse menu and its menu items
	 */
	@Test
	public void mouseMenuInitializations() {
		UMLClassManager model = new UMLClassManager();
		GUIView gui = new GUIView(new GUIController(model), model, false);
		JComponent mouseMenu = gui.getComponent("Mouse Menu");
		assertTrue("Main mouse menu exists", mouseMenu != null);
		assertTrue("Main mouse menu is popup menu", mouseMenu instanceof JPopupMenu);
		
		// Test MenuItems' initialization
		Component[] mouseMenuChildren = mouseMenu.getComponents();
		assertTrue("Main mouse menu not empty", mouseMenuChildren.length != 0);
		assertEquals("Main mouse menu number of items", 5, mouseMenuChildren.length);
		assertEquals("Main mouse menu first child class add", "mouseAddClass" , ((JMenuItem)mouseMenuChildren[0]).getName());
		assertTrue("Main mouse menu second child separator", mouseMenuChildren[1] instanceof JSeparator);
		assertEquals("Main mouse menu third child save", "mouseSave" , ((JMenuItem)mouseMenuChildren[2]).getName());
		assertEquals("Main mouse menu fourth child load", "mouseLoad" , ((JMenuItem)mouseMenuChildren[3]).getName());
		assertEquals("Main mouse menu fifth child load", "mouseExport" , ((JMenuItem)mouseMenuChildren[4]).getName());
	}
	
	/**
	 * Ensure the initialization of the class mouse menu and its items
	 */
	@Test
	public void classMenuInitialization() {
		UMLClassManager model = new UMLClassManager();
		GUIView gui = new GUIView(new GUIController(model), model, false);
		JComponent classMenu = gui.getComponent("Class Menu");
		assertTrue("Class menu exists", classMenu != null);
		assertTrue("Class menu is popup menu", classMenu instanceof JPopupMenu);

		// Test MenuItems' initialization
		Component[] classMenuChildren = classMenu.getComponents();
		assertTrue("Class menu not empty", classMenuChildren.length != 0);
		assertEquals("Class menu number of items", 13, classMenuChildren.length);
		assertEquals("Class menu first child", "classRemoveClass" , ((JMenuItem)classMenuChildren[0]).getName());
		assertEquals("Class menu second child", "classEditClass", ((JMenuItem)classMenuChildren[1]).getName());
		assertTrue("Class menu third child separator", classMenuChildren[2] instanceof JSeparator);
		assertEquals("Class menu fourth child", "classAddField" , ((JMenuItem)classMenuChildren[3]).getName());
		assertEquals("Class menu child 5", "classRemoveField" , ((JMenuItem)classMenuChildren[4]).getName());
		assertEquals("Class menu child 6", "classEditField" , ((JMenuItem)classMenuChildren[5]).getName());
		assertTrue("Class menu child 7 separator", classMenuChildren[6] instanceof JSeparator);
		assertEquals("Class menu child 8", "classAddMethod" , ((JMenuItem)classMenuChildren[7]).getName());
		assertEquals("Class menu child 9", "classRemoveMethod" , ((JMenuItem)classMenuChildren[8]).getName());
		assertEquals("Class menu child 10", "classEditMethod" , ((JMenuItem)classMenuChildren[9]).getName());
		assertTrue("Class menu child 11 separator", classMenuChildren[10] instanceof JSeparator);
		assertEquals("Class menu child 12", "classAddRelationship" , ((JMenuItem)classMenuChildren[11]).getName());
		assertEquals("Class menu child 13", "classRemoveRelationship" , ((JMenuItem)classMenuChildren[12]).getName());
	}
	
	/**
	 * Test the addition of classes to the GUI
	 */
	@Test
	public void addClass() {
		UMLClassManager model = new UMLClassManager();
		GUIView gui = new GUIView(new GUIController(model), model, false);
		
		gui.loadData(new String[] {"myclass"});
		((JMenuItem)gui.getComponent("mouseAddClass")).doClick();
		assertEquals("Add class normal exit code", 0, ErrorHandler.LAST_CODE);
		assertNotEquals("Add class normal exist check", null, model.getClass("myclass"));
		assertEquals("Number of classes after single add", 1, model.getClassNames().length);
		
		gui.loadData(new String[] {"secondclass"});
		((JMenuItem)gui.getComponent("mouseAddClass")).doClick();
		assertEquals("Add class normal exit code 2", 0, ErrorHandler.LAST_CODE);
		assertNotEquals("Add class normal exist check 2", null, model.getClass("secondclass"));
		assertEquals("Number of classes after second add", 2, model.getClassNames().length);
		
		gui.loadData(new String[] {"3*29"});
		((JMenuItem)gui.getComponent("mouseAddClass")).doClick();
		assertNotEquals("Add class invalid name error code", 0, ErrorHandler.LAST_CODE);
		assertEquals("Add class invalid not exists check", null, model.getClass("3*29"));
		assertEquals("Number of classes after invalid add", 2, model.getClassNames().length);
		
		gui.loadData(new String[] {"myclass"});
		((JMenuItem)gui.getComponent("mouseAddClass")).doClick();
		assertNotEquals("Add class duplicate exit code", 0, ErrorHandler.LAST_CODE);
		assertNotEquals("Add class duplicate exist check", null, model.getClass("myclass"));
		assertEquals("Number of classes after single add", 2, model.getClassNames().length);
	}
	
	/**
	 * Test the removal of classes from the GUI
	 */
	@Test
	public void removeClass() {
		UMLClassManager model = new UMLClassManager();
		GUIController controller = new GUIController(model);
		GUIView gui = new GUIView(controller, model, false);
		
		// Assuming add class works
		controller.addClass("myclass");
		controller.addClass("secondclass");
		
		assertEquals("Number of classes prior to remove", 2, model.getClassNames().length);
		
		gui.loadData(new String[] {"myclass"});
		((JMenuItem)gui.getComponent("classRemoveClass")).doClick();
		assertEquals("Remove class normal exit code", 0, ErrorHandler.LAST_CODE);
		assertEquals("Remove class normal exist check", null, model.getClass("myclass"));
		assertEquals("Number of classes after normal remove", 1, model.getClassNames().length);
		
		gui.loadData(new String[] {"secondclass"});
		((JMenuItem)gui.getComponent("classRemoveClass")).doClick();
		assertEquals("Remove class normal exit code 2", 0, ErrorHandler.LAST_CODE);
		assertEquals("Remove class normal exist check 2", null, model.getClass("secondclass"));
		assertEquals("Number of classes after normal remove 2", 0, model.getClassNames().length);
		
		gui.loadData(new String[] {"notreal"}); // Like birds
		((JMenuItem)gui.getComponent("classRemoveClass")).doClick();
		assertNotEquals("Remove class not exist", 0, ErrorHandler.LAST_CODE);
		assertEquals("Number of classes after invalid remove", 0, model.getClassNames().length);
	}
	
	/**
	 * Test the editing of classes
	 */
	@Test
	public void editClass() {
		UMLClassManager model = new UMLClassManager();
		GUIController controller = new GUIController(model);
		GUIView gui = new GUIView(controller, model, false);
		
		// Assuming add class works
		controller.addClass("myclass");
		controller.addClass("secondclass");
		
		assertEquals("Number of classes prior to edit", 2, model.getClassNames().length);
		
		gui.loadData(new String[] {"myclass", "changedlol"});
		((JMenuItem)gui.getComponent("classEditClass")).doClick();
		assertEquals("Edit class normal exit code", 0, ErrorHandler.LAST_CODE);
		assertNotEquals("Edit class new name exist check", null, model.getClass("changedlol"));
		assertEquals("Number of class post name change", 2, model.getClassNames().length);
		
		gui.loadData(new String[] {"changedlol", "myclass"});
		((JMenuItem)gui.getComponent("classEditClass")).doClick();
		assertEquals("Edit class normal exit code 2", 0, ErrorHandler.LAST_CODE);
		assertNotEquals("Edit class new name exist check 2", null, model.getClass("myclass"));
		assertEquals("Number of class post name change", 2, model.getClassNames().length);

		gui.loadData(new String[] {"myclass", "n*tg*(dnamebird"});
		((JMenuItem)gui.getComponent("classEditClass")).doClick();
		assertNotEquals("Edit class invalid new name exit code", 0, ErrorHandler.LAST_CODE);
		assertNotEquals("Edit class invalid old name still exists", null, model.getClass("myclass"));
		assertEquals("Edit class invalid new name does not exist", null, model.getClass("n*tg*(dnamebird"));
		assertEquals("Number of class post invalid name change", 2, model.getClassNames().length);
		
		gui.loadData(new String[] {"myclass", "secondclass"});
		((JMenuItem)gui.getComponent("classEditClass")).doClick();
		assertNotEquals("Edit class invalid new name exit code 2", 0, ErrorHandler.LAST_CODE);
		assertNotEquals("Edit class invalid old name still exists 2", null, model.getClass("myclass"));
		assertNotEquals("Edit class invalid new name still exists", null, model.getClass("secondclass"));
		assertEquals("Number of class post invalid name change", 2, model.getClassNames().length);
		
		gui.loadData(new String[] {"notreal", "likebirds"});
		((JMenuItem)gui.getComponent("classEditClass")).doClick();
		assertNotEquals("Edit class invalid old name exit code", 0, ErrorHandler.LAST_CODE);
		assertEquals("Edit class invalid old name still not exist", null, model.getClass("notreal"));
		assertEquals("Edit class invalid new name not exists", null, model.getClass("likebirds"));
		assertEquals("Number of class post invalid name change", 2, model.getClassNames().length);
	}
	
	/**
	 * Test the addition of fields to classes
	 */
	@Test
	public void addField() {
		UMLClassManager model = new UMLClassManager();
		GUIController controller = new GUIController(model);
		GUIView gui = new GUIView(controller, model, false);
		
		// Assuming add class works
		controller.addClass("myclass");
		UMLClass myclass = model.getClass("myclass");
		
		assertEquals("Number of fields initial", 0, myclass.getFields().size());
		
		gui.loadData(new String[] {"myclass", "int", "myfield"});
		((JMenuItem)gui.getComponent("classAddField")).doClick();
		assertEquals("Add field valid exit code", 0, ErrorHandler.LAST_CODE);
		assertTrue("Add field valid has field", myclass.hasField("myfield"));
		assertEquals("Add field valid num fields", 1, myclass.getFields().size());
		
		gui.loadData(new String[] {"myclass", "UMLClass", "another"});
		((JMenuItem)gui.getComponent("classAddField")).doClick();
		assertEquals("Add field valid exit code 2", 0, ErrorHandler.LAST_CODE);
		assertTrue("Add field valid has field 2", myclass.hasField("another"));
		assertEquals("Add field valid num fields 2", 2, myclass.getFields().size());
		
		gui.loadData(new String[] {"myclass", "double", "myfield"});
		((JMenuItem)gui.getComponent("classAddField")).doClick();
		assertNotEquals("Add field duplicate name diff type", 0, ErrorHandler.LAST_CODE);
		assertTrue("Add field original still exists", myclass.hasField("myfield"));
		assertEquals("Add field duplicate num fields", 2, myclass.getFields().size());
		
		gui.loadData(new String[] {"myclass", "int", "m@lf0rm*dn@me"});
		((JMenuItem)gui.getComponent("classAddField")).doClick();
		assertNotEquals("Add field invalid exit code", 0, ErrorHandler.LAST_CODE);
		assertFalse("Add field invalid does not exist", myclass.hasField("m@lf0rm*dn@me"));
		assertEquals("Add filed invalid num fields", 2, myclass.getFields().size());
	}
	
	/**
	 * Test the removal of fields from classes
	 */
	@Test
	public void removeField() {
		UMLClassManager model = new UMLClassManager();
		GUIController controller = new GUIController(model);
		GUIView gui = new GUIView(controller, model, false);
		
		// Assuming add class works
		controller.addClass("myclass");
		controller.addField("myclass", "int", "myfield");
		controller.addField("myclass", "String", "another");
		UMLClass myclass = model.getClass("myclass");
		
		assertEquals("Number of fields initial", 2, myclass.getFields().size());
		
		gui.loadData(new String[] {"myclass", "myfield"});
		((JMenuItem)gui.getComponent("classRemoveField")).doClick();
		assertEquals("Remove field valid return code", 0, ErrorHandler.LAST_CODE);
		assertFalse("Remove field valid no longer exists", myclass.hasField("myfield"));
		assertEquals("Remove field valid num fields", 1, myclass.getFields().size());
		
		gui.loadData(new String[] {"myclass", "myfield"});
		((JMenuItem)gui.getComponent("classRemoveField")).doClick();
		assertNotEquals("Remove field doesn't exist", 0, ErrorHandler.LAST_CODE);
		assertFalse("Remove filed not in list", myclass.hasField("myfield"));
		assertEquals("Remove field doesn't exist num fields", 1, myclass.getFields().size());
		
		gui.loadData(new String[] {"myclass", "n*tr3@l"}); // Like birds
		((JMenuItem)gui.getComponent("classRemoveField")).doClick();
		assertNotEquals("Remove field invalid", 0, ErrorHandler.LAST_CODE);
		assertEquals("Remove field invalid num fields", 1, myclass.getFields().size());
		
		gui.loadData(new String[] {"myclass", "another"});
		((JMenuItem)gui.getComponent("classRemoveField")).doClick();
		assertEquals("Remove field valid return code 2", 0, ErrorHandler.LAST_CODE);
		assertFalse("Remove field valid no longer exists 2", myclass.hasField("another"));
		assertEquals("Remove field valid num fields 2", 0, myclass.getFields().size());
	}
	
	/**
	 * Test the editing of field names
	 */
	@Test
	public void editField() {
		UMLClassManager model = new UMLClassManager();
		GUIController controller = new GUIController(model);
		GUIView gui = new GUIView(controller, model, false);
		
		// Assuming add class works
		controller.addClass("myclass");
		controller.addField("myclass", "Object", "myfield");
		controller.addField("myclass", "double", "another");
		UMLClass myclass = model.getClass("myclass");
		
		assertEquals("Number of fields init", 2, myclass.getFields().size());
		
		gui.loadData(new String[] {"myclass", "myfield", "other"});
		((JMenuItem)gui.getComponent("classEditField")).doClick();
		assertEquals("Edit field valid return code", 0, ErrorHandler.LAST_CODE);
		assertFalse("Edit field valid old name doesn't exist", myclass.hasField("myfield"));
		assertTrue("Edit field valid new name exists", myclass.hasField("other"));
		assertEquals("Edit field valid num fields", 2, myclass.getFields().size());
		
		gui.loadData(new String[] {"myclass", "other", "myfield"});
		((JMenuItem)gui.getComponent("classEditField")).doClick();
		assertEquals("Edit field valid return code 2", 0, ErrorHandler.LAST_CODE);
		assertFalse("Edit field valid old name not exists 2", myclass.hasField("other"));
		assertTrue("Edit field valid new name exists 2", myclass.hasField("myfield"));
		assertEquals("Edit field valid num fields unchanged", 2, myclass.getFields().size());
		
		gui.loadData(new String[] {"myclass", "myfield", "another"});
		((JMenuItem)gui.getComponent("classEditField")).doClick();
		assertNotEquals("Edit field duplicate return code", 0, ErrorHandler.LAST_CODE);
		assertTrue("Edit field duplicate original still exists", myclass.hasField("myfield"));
		assertEquals("Edit field duplicate num fields unchanged", 2, myclass.getFields().size());
		
		gui.loadData(new String[] {"myclass", "another", "_n*t()real"});
		((JMenuItem)gui.getComponent("classEditField")).doClick();
		assertNotEquals("Edit field invalid return code", 0, ErrorHandler.LAST_CODE);
		assertTrue("Edit field invalid old name still exists", myclass.hasField("another"));
		assertFalse("Edit field invalid new name does not exist", myclass.hasField("_n*t()real"));
		assertEquals("Edit field invalid num fields unchanged", 2, myclass.getFields().size());
	}
	
	/**
	 * Test the addition of methods to class
	 */
	@Test
	public void addMethod() {
		UMLClassManager model = new UMLClassManager();
		GUIController controller = new GUIController(model);
		GUIView gui = new GUIView(controller, model, false);
		
		// Assuming add class works
		controller.addClass("myclass");
		UMLClass myclass = model.getClass("myclass");
		
		assertEquals("Number of methods initial", 0, myclass.getMethods().size());
		
		// Adding method 'int noparams() {}'
		gui.loadData(new String[] {"myclass", "int", "noparams"});
		((JMenuItem)gui.getComponent("classAddMethod")).doClick();
		assertEquals("Add method valid return code", 0, ErrorHandler.LAST_CODE);
		assertEquals("Add method valid num methods", 1, myclass.getMethods().size());
		
		// Adding method 'void mymethod(String par1, int par2) {}'
		gui.loadData(new String[] {"myclass", "void", "mymethod", "String par1, int par2"});
		((JMenuItem)gui.getComponent("classAddMethod")).doClick();
		assertEquals("Add method valid return code 2", 0, ErrorHandler.LAST_CODE);
		assertEquals("Add method valid num methods 2", 2, myclass.getMethods().size());
		
		gui.loadData(new String[] {"myclass", "void", "mymethod", "String overloaded"});
		((JMenuItem)gui.getComponent("classAddMethod")).doClick();
		assertEquals("Add overload method return code", 0, ErrorHandler.LAST_CODE);
		assertEquals("Num methods", 3, myclass.getMethods().size());
		
		gui.loadData(new String[] {"myclass", "void", "*not()real*"});
		((JMenuItem)gui.getComponent("classAddMethod")).doClick();
		assertNotEquals("Add method invalid return code", 0, ErrorHandler.LAST_CODE);
		assertEquals("Num methods", 3, myclass.getMethods().size());
	}
	
	/**
	 * Test the removal of methods to class
	 */
	@Test
	public void removeMethod() {
		UMLClassManager model = new UMLClassManager();
		GUIController controller = new GUIController(model);
		GUIView gui = new GUIView(controller, model, false);
		
		controller.addClass("myclass");
		controller.addMethod("myclass", "int", "mymethod", "");
		controller.addMethod("myclass", "String", "another", "String par1");
		controller.addMethod("myclass", "String", "another", "");
		UMLClass myclass = model.getClass("myclass");
		
		assertEquals("Init number of methods", 3, myclass.getMethods().size());
		
		// Remove mymethod with no params
		gui.loadData(new String[] {"myclass", "mymethod"});
		((JMenuItem)gui.getComponent("classRemoveMethod")).doClick();
		assertEquals("Remove method valid return code", 0, ErrorHandler.LAST_CODE);
		assertEquals("Num methods post remove", 2, myclass.getMethods().size());
		
		// Remove another with args
		gui.loadData(new String[] {"myclass", "another", "String par1"});
		((JMenuItem)gui.getComponent("classRemoveMethod")).doClick();
		assertEquals("Remove overload method valid return code", 0, ErrorHandler.LAST_CODE);
		assertEquals("Num methods post overload remove", 1, myclass.getMethods().size());
		
		gui.loadData(new String[] {"myclass", "d*e$n0#"});
		((JMenuItem)gui.getComponent("classRemoveMethod")).doClick();
		assertNotEquals("Remove method invalid return code", 0, ErrorHandler.LAST_CODE);
		assertEquals("Remove invalid method nothing removed", 1, myclass.getMethods().size());
	}
	
	/**
	 * Test the editing of method names for a class
	 */
	@Test
	public void editMethod() {
		UMLClassManager model = new UMLClassManager();
		GUIController controller = new GUIController(model);
		GUIView gui = new GUIView(controller, model, false);
		
		controller.addClass("myclass");
		controller.addMethod("myclass", "int", "mymethod", "");
		controller.addMethod("myclass", "boolean", "another", "");
		controller.addMethod("myclass", "boolean", "another", "boolean istrue");
		UMLClass myclass = model.getClass("myclass");
		
		assertEquals("Init number of methods", 3, myclass.getMethods().size());
		
		gui.loadData(new String[] {"myclass", "mymethod", "newmethod", ""});
		((JMenuItem)gui.getComponent("classEditMethod")).doClick();
		assertEquals("Edit method name valid return code", 0, ErrorHandler.LAST_CODE);
		assertEquals("Num methods the same", 3, myclass.getMethods().size());
		
		gui.loadData(new String[] {"myclass", "newmethod", "mymethod", ""});
		((JMenuItem)gui.getComponent("classEditMethod")).doClick();
		assertEquals("Edit method name valid return code 2", 0, ErrorHandler.LAST_CODE);
		assertEquals("Num methods unchanged", 3, myclass.getMethods().size());
		
		gui.loadData(new String[] {"myclass", "mymethod", "another", ""});
		((JMenuItem)gui.getComponent("classEditMethod")).doClick();
		assertNotEquals("Edit method to duplicate name return code", 0, ErrorHandler.LAST_CODE);
		assertEquals("Num methods unchanged", 3, myclass.getMethods().size());
	}
	
	/**
	 * Test the adding of relationships
	 */
	@Test
	public void addRelationship() {
		UMLClassManager model = new UMLClassManager();
		GUIController controller = new GUIController(model);
		GUIView gui = new GUIView(controller, model, false);
		
		controller.addClass("class1");
		controller.addClass("class2");
		controller.addClass("class3");
		
		assertEquals("Init number of relationships for class1", "[]", model.listRelationships("class1")[0]);
		assertEquals("Init number of relationships for class2", "[]", model.listRelationships("class2")[0]);
		assertEquals("Init number of relationships for class3", "[]", model.listRelationships("class3")[0]);
		assertEquals("Init number total relationships", 0, model.getRelationships().size());
		
		gui.loadData(new String[] {"class1", "aggregation", "class2"});
		((JMenuItem)gui.getComponent("classAddRelationship")).doClick();
		assertEquals("Add relationship valid return code", 0, ErrorHandler.LAST_CODE);
		assertEquals("Num total relationships after valid add", 1, model.getRelationships().size());
		
		gui.loadData(new String[] {"class2", "inheritance", "class3"});
		((JMenuItem)gui.getComponent("classAddRelationship")).doClick();
		assertEquals("Add relationship valid return code 2", 0, ErrorHandler.LAST_CODE);
		assertEquals("Num total relationships", 2, model.getRelationships().size());
		
		gui.loadData(new String[] {"class1", "not real", "class3"});
		((JMenuItem)gui.getComponent("classAddRelationship")).doClick();
		assertNotEquals("Add relationship invalid return code", 0, ErrorHandler.LAST_CODE);
		assertEquals("Num total relationships", 2, model.getRelationships().size());
		
		gui.loadData(new String[] {"class1", "aggregation", "class2"});
		((JMenuItem)gui.getComponent("classAddRelationship")).doClick();
		assertNotEquals("Add relationship duplicate return code", 0, ErrorHandler.LAST_CODE);
		assertEquals("Num total relationships", 2, model.getRelationships().size());
		
		gui.loadData(new String[] {"class1", "aggregation", "notreal"});
		((JMenuItem)gui.getComponent("classAddRelationship")).doClick();
		assertNotEquals("Add relationship invalid return code", 0, ErrorHandler.LAST_CODE);
		assertEquals("Num total relationships", 2, model.getRelationships().size());
	}
	
	/**
	 * Test the removal of relationships
	 */
	@Test
	public void removeRelationship() {
		UMLClassManager model = new UMLClassManager();
		GUIController controller = new GUIController(model);
		GUIView gui = new GUIView(controller, model, false);
		
		controller.addClass("class1");
		controller.addClass("class2");
		controller.addClass("class3");
		controller.addRelationship("class1", "aggregation", "class2");
		controller.addRelationship("class1", "inheritance", "class3");
		controller.addRelationship("class2", "composition", "class3");
		
		assertEquals("Init num relationships", 3, model.getRelationships().size());
		
		gui.loadData(new String[] {"class1", "aggregation", "class2"});
		((JMenuItem)gui.getComponent("classRemoveRelationship")).doClick();
		assertEquals("Remove valid return code", 0, ErrorHandler.LAST_CODE);
		assertEquals("Num relationships post remove", 2, model.getRelationships().size());
		
		gui.loadData(new String[] {"class1", "aggregation", "class2"});
		((JMenuItem)gui.getComponent("classRemoveRelationship")).doClick();
		assertNotEquals("Remove invalid return code", 0, ErrorHandler.LAST_CODE);
		assertEquals("Num relationships post invalid remove", 2, model.getRelationships().size());
		
		gui.loadData(new String[] {"class2", "notrealtype", "class3"});
		((JMenuItem)gui.getComponent("classRemoveRelationship")).doClick();
		assertNotEquals("Remove invalid return code 2", 0, ErrorHandler.LAST_CODE);
		assertEquals("Num relationships post invalid remove 2", 2, model.getRelationships().size());
		
		gui.loadData(new String[] {"class1", "inheritance", "class3"});
		((JMenuItem)gui.getComponent("classRemoveRelationship")).doClick();
		assertEquals("Remove valid return code 2", 0, ErrorHandler.LAST_CODE);
		assertEquals("Num relationships post valid remove 2", 1, model.getRelationships().size());
	}
}
