package model;

public class Flight {
	
	// Variables
	private String from;
	private String to;
	private int departure;
	private int duration;
	
	public Flight(String from, String to, int departure, int duration) {
		
		this.from = from;
		this.to = to;
		this.departure = departure;
		this.duration = duration;
	}
	// Getter methods
	public String getFrom() {return from;}
	public String getTo() {return to;}
	public int getDeparture() {return departure;}
	public int getDuration() {return duration;}
	
	public String getDepartureString() {
		
	    int hours = departure / 60;
	    int minutes = departure % 60;

	    return String.format("%02d:%02d", hours, minutes);
	}
	
}
