package resources;

public interface Observer {
	public void updated(Observable src, String tag, Object eventData);
}
