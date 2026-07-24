package model;

import java.util.ArrayList;
import java.util.List;

import model.Airport.AirportVar;

public class AirportTable implements Observable{
	
	private List<Observer> observers = new ArrayList<>();
	private List<Airport> airports = new ArrayList<>();
	

	@Override
	public void addObserver(Observer o) {
		observers.add(o);
	}

	@Override
	public void notifyObservers() {
		for(Observer o : observers) o.onObserverSignal();
	}
	
	public void add(Airport airport) {
		airports.add(airport);
		notifyObservers();
	}
	public <T> boolean exists(T s, AirportVar type) {
		
		for(Airport airport : airports) {
			switch(type) {
			case CODE: if(airport.getCode().equals((String)s)) return true; break;
			case NAME: if(airport.getName().equals((String)s)) return true; break;
			case COORDINATE: if(((Coordinate)s).getX() == airport.getX() && ((Coordinate)s).getY() == airport.getY()) return true; break;
			}
		}
		return false;
	}
	
	public List<Airport> getAirports() {
		return airports;
	}
	
	public boolean isEmpty() {
		return airports.isEmpty();
	}
}

	
	
	
	
	
	
	
	
	
	
	
	
	