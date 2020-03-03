package resources;

import java.util.ArrayList;

public class Observable {
	private ArrayList<Observer> observers;
	
	public Observable() {
		observers = new ArrayList<Observer>();
	}
	
	public void addObserver(Observer o) {
		observers.add(o);
	}
	
	protected void signal(String tag, Object data) {
		for(Observer o : observers) {
			o.updated(this, tag, data);
		}
	}
}
