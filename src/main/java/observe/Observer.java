// Package name
package observe;

/**
 * Observer interface for Observer Design Pattern
 * @author ryan
 *
 */
public interface Observer {
	/**
	 * Function that is told something changed
	 * @param src - the source of the notifier
	 * @param tag - Event identifier
	 * @param data - Associated data
	 */
	public void updated(Observable src, String tag, Object data);
}
