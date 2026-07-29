package model;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import logic.EventType;
import logic.Observable;
import logic.Observer;

public class FlightTable implements Observable{
	
	private List<Observer> observers = new ArrayList<>();
	private List<Flight> flights = new ArrayList<>();
	

	@Override
	public void addObserver(Observer o) {
		observers.add(o);
	}

	@Override
	public void notifyObservers() {
		for(Observer o : observers) o.onObserverSignal(EventType.TABLE);
	}
	
	// Adds flight to the table
	public void add(Flight flight) {
		flights.add(flight);
		notifyObservers();
	}
	
	public List<Flight> getFlights() {
		return flights;
	}
	
	public boolean isEmpty() {
		return flights.isEmpty();
	}
	
	// Sorts the table
	public void sortFlights() {
		flights.sort(Comparator.comparing(Flight::getDeparture));
		notifyObservers();
	}

}
