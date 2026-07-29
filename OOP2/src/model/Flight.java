package model;

public class Flight {
	
	public enum FlightStatus{
		WAITING,IN_FLIGHT,LANDED,QUEUED
	}
	
	// Variables
	private String from;
	private String to;
	private int departure;
	private int actualDeparture;
	private int duration;
	private FlightStatus status = FlightStatus.WAITING;
	
	private Coordinate defaultCoordinate = null;
	private Coordinate currCoordinate = null;
	
	private Airport fromAirport = null;
	private Airport toAirport = null;
	
	// Constructor
	public Flight(Airport fromAirport, Airport toAirport, int departure, int duration) {
		
		this.fromAirport = fromAirport;
		this.toAirport = toAirport;
		this.from = fromAirport.getCode();
		this.to = toAirport.getCode();
		this.departure = departure;
		this.actualDeparture = departure;
		this.duration = duration;
	}
	
	// Default getter methods
	public String getFrom() {return from;}
	public String getTo() {return to;}
	public int getDeparture() {return departure;}
	public int getDuration() {return duration;}
	
	// Status getter and setter
	public FlightStatus getStatus() { return status; }
	public void setStatus(FlightStatus status) { this.status = status; }
	
	// Returns airport object
	public Airport getFromAirport() {return fromAirport;}
	public Airport getToAirport() {return toAirport;}
	
	// Used for precise flight duration calculation
	public int getActualDeparture() { return actualDeparture; }
	public void setActualDeparture(int actualDeparture) { this.actualDeparture = actualDeparture; }
	
	public synchronized Coordinate getCoordinate() {
		
		if(currCoordinate == null) {return defaultCoordinate;}
		else {return currCoordinate;}
		
	}
	
	public synchronized void setCoordinate(int x, int y) {
		
		if(defaultCoordinate == null) {defaultCoordinate = new Coordinate(x,y);}
		else {currCoordinate = new Coordinate(x,y);}
	}
		
	// Converts time to a readable format
	public String getDepartureString() {
		
	    int hours = departure / 60;
	    int minutes = departure % 60;

	    return String.format("%02d:%02d", hours, minutes);
	}
	
}
