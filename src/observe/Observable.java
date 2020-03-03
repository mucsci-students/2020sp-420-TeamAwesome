// Package name
package observe;

// System imports
import java.util.ArrayList;

// Local imports

/**
 * Object to make classes observable to notify changes
 * Observer Design Pattern
 * @author ryan
 *
 */
public class Observable {
	// List of 'subscribers' to be notified of changes
	private ArrayList<Observer> observers;
	
	/**
	 * Initialize the list of observers
	 */
	public Observable() {
		observers = new ArrayList<Observer>();
	}
	
	/**
	 * Add the given observer to the list observers
	 * @param o - observer to be notified
	 */
	public void addObserver(Observer o) {
		observers.add(o);
	}
	
	/**
	 * Notify all observers that some event has taken place
	 * @param tag - Event identifier
	 * @param data - Associated data
	 */
	protected void notify(String tag, Object data) {
		for(Observer o : observers) {
			o.updated(this, tag, data);
		}
	}
}
