package model;

import java.util.ArrayList;
import java.util.List;


// Represents a full route 
public class Path {

    private List<Segment> segments = new ArrayList<>();

    // Simple path with two points
    public Path(Coordinate start, Coordinate end, int startTime, int totalDuration) {
        segments.add(new Segment(start, end, startTime, totalDuration));
    }

    public List<Segment> getSegments() {
        return segments;
    }

    public int getTotalDuration() {
        int sum = 0;
        for (Segment s : segments) sum += s.getDuration();
        return sum;
    }

    public int getStartTime() {
    	if(segments.isEmpty()) return 0;
    	else return segments.get(0).getStartTime();
    }

    // Used when a flight actually departs later than scheduled
    public void setStartTime(int newStartTime) {
        if (segments.isEmpty()) return;
        int shift = newStartTime - getStartTime();
        for (Segment s : segments) s.setStartTime(s.getStartTime() + shift);
    }

    // Returns the position along the path at a given simulation time
    public Coordinate getPositionAt(int simulationTime) {
        if (segments.isEmpty()) return null;

        for (Segment s : segments) {
            int segmentEnd = s.getStartTime() + s.getDuration();
            boolean isLast = s == segments.get(segments.size() - 1);

            if (simulationTime <= segmentEnd || isLast) {
            	double progress = (s.getDuration() == 0) ? 1.0 : (double)(simulationTime - s.getStartTime()) / s.getDuration();
            	progress = Math.max(0.0, Math.min(progress, 1.0));

                double x = s.getStart().getXPrecise() + progress * (s.getEnd().getXPrecise() - s.getStart().getXPrecise());
                double y = s.getStart().getYPrecise() + progress * (s.getEnd().getYPrecise() - s.getStart().getYPrecise());
                return new Coordinate(x, y);
            }
        }
        return segments.get(segments.size() - 1).getEnd();
    }

    // Resets to the original path
    public void reset(Coordinate start, Coordinate end, int startTime, int totalDuration) {
        segments.clear();
        segments.add(new Segment(start, end, startTime, totalDuration));
    }
    // Adds a segment to the segment list
    public void addSegment(Coordinate end, int duration) {
    	
    	Coordinate lastPoint;
  
    	if(segments.isEmpty()) lastPoint = end;
    	else lastPoint = getLastSegment().getEnd();
    	
    	int nextSegmentStartTime;
    	if(segments.isEmpty()) nextSegmentStartTime = 0;
    	else nextSegmentStartTime = getLastSegment().getStartTime() + getLastSegment().getDuration();
   

        segments.add(new Segment(lastPoint, end, nextSegmentStartTime, duration));
    }
    // Redirects a flight to specified target
    public void redirectTo(int simulationTime, Coordinate target, int duration) {
    	
    	Coordinate currentPosition = getPositionAt(simulationTime);
    	List<Segment> keptSegments = new ArrayList<>();
    	
    	for(Segment s : segments) {
    		if(simulationTime > s.getStartTime() + s.getDuration()) {keptSegments.add(s);}
    		else if(simulationTime > s.getStartTime()) {
    			Segment splitSegment = new Segment(s.getStart(),currentPosition,s.getStartTime(),simulationTime - s.getStartTime());
    			keptSegments.add(splitSegment);
    			break;
    		}
    		else break;
    	}
    	segments = keptSegments;
    	addSegment(target,duration);
    }
    // Returns the last segment
    private Segment getLastSegment() {
    	return segments.get(segments.size()-1);
    }
    
    // Helper method for calculating speed
    public double getSpeed(Coordinate origin, Coordinate destination, int duration) {
        double originalDistance = Math.sqrt(
            Math.pow(origin.getX() - destination.getX(), 2) +
            Math.pow(origin.getY() - destination.getY(), 2)
        );
        return originalDistance / duration;
    }
    
    // Helper method for calculating duration depending on speed
    public int durationFor(Coordinate from, Coordinate to, double speed) {
        double dx = to.getXPrecise() - from.getXPrecise();
        double dy = to.getYPrecise() - from.getYPrecise();
        double distance = Math.sqrt(dx*dx + dy*dy);
        return (int) Math.round(distance / speed);
    }
}