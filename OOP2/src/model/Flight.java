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
	private Airport fromAirport = null;
	private Airport toAirport = null;
	private Path path;
	
	// Constructor
	public Flight(Airport fromAirport, Airport toAirport, int departure, int duration) {
		
		this.fromAirport = fromAirport;
		this.toAirport = toAirport;
		this.from = fromAirport.getCode();
		this.to = toAirport.getCode();
		this.departure = departure;
		this.actualDeparture = departure;
		this.duration = duration;
		
		Coordinate tempStart = new Coordinate(fromAirport.getX(),fromAirport.getY());
		Coordinate tempEnd = new Coordinate(toAirport.getX(),toAirport.getY());
		
		path = new Path(tempStart,tempEnd,departure,duration);
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
		
	// Converts time to a readable format
	public String getDepartureString() {
		
	    int hours = departure / 60;
	    int minutes = departure % 60;

	    return String.format("%02d:%02d", hours, minutes);
	}
	
	public void resetMovement() {
		Coordinate start = new Coordinate(fromAirport.getX(), fromAirport.getY());
	    Coordinate end = new Coordinate(toAirport.getX(), toAirport.getY());
	    path.reset(start, end, departure, duration);
	}
	
	// Departs the flight
	public void depart(int time) {
		actualDeparture = time;
		path.setStartTime(time);
		status = FlightStatus.IN_FLIGHT;
	}
	
	// Returns current flight position
	public Coordinate getPositionAt(int simulationTime) {
	    return path.getPositionAt(simulationTime);
	}

	// Returns the flight path object
	public Path getPath() {
	    return path;
	}
}
