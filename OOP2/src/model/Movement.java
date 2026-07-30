package model;

// Used for precise flight path calculating
public class Movement {
	
	private Coordinate start;
    private Coordinate end;

    private int startTime;
    private int duration;
    
    public Movement(Coordinate start, Coordinate end, int startTime,int duration) {
    	this.start = start;
    	this.end = end;
    	this.startTime = startTime;
    	this.duration = duration;
    }
    
	public Coordinate getStart() {return start;}
	public void setStart(Coordinate start) {this.start = start;}
	
	public Coordinate getEnd() {return end;}
	public void setEnd(Coordinate end) {this.end = end;}
	
	public int getStartTime() {return startTime;}
	public void setStartTime(int startTime) {this.startTime = startTime;}
	
	public int getDuration() {return duration;}
	public void setDuration(int duration) {this.duration = duration;}

}
