	package logic;
	
	public interface Observer {
		
		// Defines what the observer does when the observable object sends a signal that something has changed
		void onObserverSignal(EventType event);
	
	}
