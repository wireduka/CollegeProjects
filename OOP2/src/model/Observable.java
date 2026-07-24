package model;

public interface Observable {
	
	// Adds observer to observer list
	void addObserver(Observer o);
	
	// Notifies all observers that something has changed
	void notifyObservers();
	
	

}
