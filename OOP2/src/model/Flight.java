package model;

public class Flight {
	
	public enum FlightStatus{
		WAITING,IN_FLIGHT,LANDED,QUEUED
	}
	
	// Variables
	private String from;
	private String to;
	private int departure;
	private int duration;
	private FlightStatus status = FlightStatus.WAITING;
	
	public Flight(String from, String to, int departure, int duration) {
		
		this.from = from;
		this.to = to;
		this.departure = departure;
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
	
	// Converts time to a readable format
	public String getDepartureString() {
		
	    int hours = departure / 60;
	    int minutes = departure % 60;

	    return String.format("%02d:%02d", hours, minutes);
	}
	
}
