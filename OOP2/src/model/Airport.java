package model;

import java.util.ArrayList;
import java.util.List;

public class Airport {
	
	// Constants
	public static final int MIN_X = -180;
    public static final int MAX_X = 180;
    public static final int MIN_Y = -90;
    public static final int MAX_Y = 90;
    
    // Enumerate
    public enum AirportVar{
    	CODE,NAME,COORDINATE
    }
    
    // Variables
	private String code;
	private String name;
	private Coordinate coordinate;
	private boolean visible = true;
	
	private List<Flight> departureQueue = new ArrayList<>();
	private List<Flight> flights = new ArrayList<>();
	
	public Airport(String code, String name, Coordinate coord) {
		
		this.code = code;
		this.name = name;
		coordinate = new Coordinate(coord.getX(),coord.getY());
	}
	// Getter methods
	public String getCode() {return code;}
	public String getName() {return name;}
	public int getX() {return coordinate.getX();}
	public int getY() {return coordinate.getY();}
	
	public List<Flight> getDepartureQueue(){return departureQueue;}
	public List<Flight> getFlights(){return flights;}
	
	public boolean isVisible() {return visible;}
	
	// Setter methods
	public void setCode(String code) {this.code = code;}
	public void setName(String name) {this.name = name;}
	public void setVisible(boolean state) {this.visible = state;}
	
	public void addFlight(Flight flight) {flights.add(flight);}
	public void addToDepartureQueue(Flight flight) {departureQueue.add(flight);}


}