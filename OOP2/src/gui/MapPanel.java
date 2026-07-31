package gui;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import logic.BlinkTimer;
import logic.Controller;
import logic.EventType;
import logic.Observer;
import model.Airport;
import model.AirportTable;
import model.Coordinate;
import model.Flight;
import model.Flight.FlightStatus;
import model.Movement;

public class MapPanel extends Canvas implements Observer{
	
	private AirportTable airportTable;
	private static Airport selectedCurr;
	private BlinkTimer blinkTimer;
	private Controller controller = Controller.getInstance();
	
	private static final int RECTANGLE_SIZE = 20;
	private static final int FLIGHT_SIZE = 12;
	
	// Constructor
	public MapPanel() {
		
		controller.addMapPanel(this);
		this.airportTable = controller.getAirportTable();;
		this.blinkTimer = new BlinkTimer(this);
		
		blinkTimer.start();
		airportTable.addObserver(this);
		controller.getAnimationClock().addObserver(this);
		
		this.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {processSelection(e.getX(),e.getY());}});
	}
	
	@Override
	public void onObserverSignal(EventType event) {
		updateCoordinates();
		repaint();
	}
	@Override
	public void paint(Graphics g) {
		
		paintBackground(g,Color.GRAY);					
		paintVisibleAirports(g);	
		paintFlights(g);
		
	}
	
	// Helper method for calculating X coordinate and converting the X coordinate starting point to the Canvas starting point
	private int toPixelX(int x) {
		return getWidth()/2 + (int)(x * getScale());
	}
	
	// Helper method for calculating Y coordinate and converting the Y coordinate starting point to the Canvas starting point
	private int toPixelY(int y) {
		return getHeight()/2 - (int)(y * getScale());
	}
	
	// Helper method for calculating Canvas X coordinate and converting the X coordinate to the model coordinate
	private int fromPixelX(int px) {
	    return (int)((px - getWidth()/2) / (getScale()));
	}
	// Helper method for calculating Canvas Y coordinate and converting the Y coordinate to the model coordinate
	private int fromPixelY(int py) {
	    return (int)((getHeight()/2 - py) / (getScale()));
	}
	
	// Helper method for setting the map scaling
	private double getScale() {
	    return Math.min(
	        getWidth() / (double)(Airport.MAX_X - Airport.MIN_X),
	        getHeight() / (double)(Airport.MAX_Y - Airport.MIN_Y)
	    );
	}
	
	// Helper method for detecting if an airport was selected
	private Airport getAirportAt(int x, int y) {
		
		for(Airport at: airportTable.getAirports()) {
			
			int airportXPixelCoordinate = toPixelX(at.getX());
			int airportYPixelCoordinate = toPixelY(at.getY());
			
			if(x >= airportXPixelCoordinate - RECTANGLE_SIZE/2 && x <= airportXPixelCoordinate + RECTANGLE_SIZE/2 &&
			   y >= airportYPixelCoordinate - RECTANGLE_SIZE/2 && y <=  airportYPixelCoordinate + RECTANGLE_SIZE/2)
			{return at;}
		}
		return null;
	}
	
	// Helper method for detecting if a flight was selected
	private Flight getFlightAt(int x, int y) {
		
		for(Flight ft: controller.getFlightTable().getFlights()) {
			if(ft.getStatus() != FlightStatus.IN_FLIGHT) continue;			// In the case of further status implementation, the status case must be defined here
			
			int flightXPixelCoordinate = toPixelX(ft.getCurrentCoordinate().getX());
			int flightYPixelCoordinate = toPixelY(ft.getCurrentCoordinate().getY());
			
			if(x >= flightXPixelCoordinate - FLIGHT_SIZE/2 && x <= flightXPixelCoordinate + FLIGHT_SIZE/2 &&
			   y >= flightYPixelCoordinate - FLIGHT_SIZE/2 && y <= flightYPixelCoordinate + FLIGHT_SIZE/2)
			{return ft;}
		}
		return null;
	}
	
	// Helper method for checking if an airport is selected and visible
	private boolean processSelection(int x, int y) {
		
		Airport clickedAirport = getAirportAt(x,y);
		
		if(clickedAirport == null) return false;
		if(clickedAirport == selectedCurr && clickedAirport.isVisible()) { 
			
			selectedCurr = null;
			if(controller.isSimulationActive()) {controller.resumeTimer();}
		}
		else if(clickedAirport != null && clickedAirport.isVisible()) {
			
			selectedCurr = clickedAirport;
			controller.pauseTimer();
		}
		return true;
	}
	
	// Helper method for setting a background color
	private void paintBackground(Graphics g, Color color) {
		g.setColor(color);
		g.fillRect(0,0,getWidth(), getHeight());
	}
	
	// Helper method for painting a square at the airport coordinate
	private void paintVisibleAirports(Graphics g) {
		
		for(Airport at : airportTable.getAirports()) {
			
			// Removes selection if the selected airport was hidden
			if(!at.isVisible() && at == selectedCurr) selectedCurr = null;
			if(!at.isVisible()) continue;
			
			int x = toPixelX(at.getX());
			int y = toPixelY(at.getY());
			
			if(at == selectedCurr && blinkTimer.getBlinkState()) {g.setColor(Color.RED);}
			else {g.setColor(Color.DARK_GRAY);}
			
			g.fillRect(x - RECTANGLE_SIZE/2, y - RECTANGLE_SIZE/2, RECTANGLE_SIZE, RECTANGLE_SIZE);
			g.drawString(at.getCode(), x + RECTANGLE_SIZE, y);
		}
	}
	
	private void paintFlights(Graphics g) {
		for(Flight f : controller.getFlightTable().getFlights()) {
			if(f.getStatus() == FlightStatus.IN_FLIGHT) {					// In the case of further status implementation, the status case must be defined here
				g.setColor(Color.BLUE);
				g.fillOval(toPixelX(f.getCurrentCoordinate().getX()) - FLIGHT_SIZE/2,toPixelY(f.getCurrentCoordinate().getY()) - FLIGHT_SIZE/2, FLIGHT_SIZE, FLIGHT_SIZE);
			}
		}
		
	}
	private void updateCoordinates() {
		
		for(Flight f : controller.getFlightTable().getFlights()) {
			
			int time = controller.getSimulationTime();
			
			if (f.getStatus() != FlightStatus.IN_FLIGHT) continue;			// In the case of further status implementation, the status case must be defined here
			
			Movement m = f.getCurrentMovement();
			
			double progress = (double)(time - m.getStartTime()) / m.getDuration();
			progress = Math.min(progress, 1.0);
			
			Coordinate start = m.getStart();
			Coordinate end = m.getEnd();
			int currentX = (int)(start.getX() + progress * (end.getX() - start.getX()));
			int currentY = (int)(start.getY() + progress * (end.getY() - start.getY()));
			
			f.setCurrentCoordinate(currentX, currentY);
			
			processFlightEvents(f);
		}
	}
	
	// Method added for further software implementation
	private void processFlightEvents(Flight f) {
		
	}
	// Update method overriding to eliminate screen flickering when repaint is called by implementing a buffer
	@Override
	public void update(Graphics g) {
		Image buffer = createImage(getWidth(), getHeight());
		Graphics background = buffer.getGraphics();
		    
		paint(background);
		    
		g.drawImage(buffer, 0, 0, this);
		background.dispose();
		}
		
	public static boolean isSelected() {
		if(selectedCurr != null) return true;
		return false;
	}
	
	
	

}
