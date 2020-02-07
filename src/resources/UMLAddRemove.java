package resources;
//System imports
import java.util.LinkedList;

/**
 * For adding and removing classes from the UML diagram
 * @author antho
 * @author Dylan
 */
public class UMLAddRemove {


	/**
	 * Default constructor if we don't have a linked list make one
	 */
	public UMLAddRemove()
	{
		LinkedList<UMLClass> list = new LinkedList<UMLClass>();
	}
	/**
	 * 
	 * @param load -  constructor for loading a existing linked list
	 */
	public UMLAddRemove(LinkedList<UMLClass> load)
	{
		LinkedList<UMLClass> list = load;
	}

	
	
	
	/**
	 * Adds node of type UMLClass to list
	 * @param name: name of class
	 * @param type: type of class
	 */
	public boolean addClass(String name, String type){
		UMLClass newClass = new UMLClass(name, type);
		list.addLast(newClass);
		return list.contains(newClass);
	}
	
	public boolean removeClass(String className) {
		int size = list.size();
		for(int i = 0; i < size - 1; ++i) {
			UMLClass temp = list.get(i);
			if(temp.name == className) {
				list.remove(i);
				return true;
			}
		}
		return false;
	}
}