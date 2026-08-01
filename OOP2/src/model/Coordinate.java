package model;

public class Coordinate {
	
    private double x;
    private double y;
    
    public Coordinate(int x, int y) {
    	
        this.x = x;
        this.y = y;
    }
    
    public Coordinate(double x, double y) {
        this.x = x;
        this.y = y;
    }
    
    public int getX() { return (int)Math.round(x); }
    public int getY() { return (int)Math.round(y); }

    public double getXPrecise() { return x; }
    public double getYPrecise() { return y; }

    public void setX(double x) { this.x = x; }
    public void setY(double y) { this.y = y; }
}
