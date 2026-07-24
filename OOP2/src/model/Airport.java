package model;

public class Airport {
	
	// Constants
	public static final int MIN_X = -180;
    public static final int MAX_X = 180;
    public static final int MIN_Y = -90;
    public static final int MAX_Y = 90;
    
    // Enum
    public enum AirportVar{
    	CODE,NAME,COORDINATE
    }
    
    // Variables
	private String code;
	private String name;
	private Coordinate coordinate;
	private boolean visible = true;
	// TODO departureQueue, nextAvaliableTime
	
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
	public boolean isVisible() {return visible;}
	
	// Setter methods
	public void setCode(String code) {this.code = code;}
	public void setName(String name) {this.name = name;}
	public void setVisible(boolean state) {this.visible = state;}


}