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

public class MapPanel extends Canvas implements Observer{
	
	private AirportTable airportTable;
	private static Airport selectedCurr;
	private BlinkTimer blinkTimer;
	private static final int RECTANGLE_SIZE = 20;
	
	// Constructor
	public MapPanel() {
		
		this.airportTable = Controller.getInstance().getAirportTable();;
		this.blinkTimer = new BlinkTimer(this);
		
		blinkTimer.start();
		airportTable.addObserver(this);
		
		this.addMouseListener(new MouseAdapter() {
			
			@Override
			public void mouseClicked(MouseEvent e) {processSelection(e.getX(),e.getY());}});
	}
	
	@Override
	public void onObserverSignal(EventType event) {
		repaint();
	}
	@Override
	public void paint(Graphics g) {
		paintBackground(g,Color.GRAY);
		paintVisibleAirports(g);
	}
	
	
	// Helper method for calculating X coordinate and converting the X coordinate starting point to the Canvas starting point
	private int toPixelX(int airportXCoordinate) {
		return (int)((airportXCoordinate - Airport.MIN_X) / (double)(Airport.MAX_X - Airport.MIN_X) * this.getWidth());
	}
	
	// Helper method for calculating Y coordinate and converting the Y coordinate starting point to the Canvas starting point
	private int toPixelY(int airportYCoordinate) {
		return (int)(this.getHeight() - (airportYCoordinate - Airport.MIN_Y) / (double)(Airport.MAX_Y - Airport.MIN_Y) * this.getHeight());
	}
	
	// Helper method for detecting if an airport was selected
	private Airport getAirportAt(int x, int y) {
		
		for(Airport at: airportTable.getAirports()) {
			
			int airportXPixelCoordinate = toPixelX(at.getX());
			int airportYPixelCoordinate = toPixelY(at.getY());
			
			if(x >= airportXPixelCoordinate && x <= airportXPixelCoordinate + RECTANGLE_SIZE &&
			   y >= airportYPixelCoordinate && y <=  airportYPixelCoordinate + RECTANGLE_SIZE)
			{return at;}
		}
		return null;
	}
	
	// Helper method for checking if an airport is selected and visible
	private void processSelection(int x, int y) {
		
		Airport clickedAirport = getAirportAt(x,y);
		
		if(clickedAirport == null) return;
		if(clickedAirport == selectedCurr && clickedAirport.isVisible()) { 
			
			selectedCurr = null;
			if(!Controller.getInstance().isSimulationActive()) {Controller.getInstance().resumeTimer();}
		}
		else if(clickedAirport != null && clickedAirport.isVisible()) {
			
			selectedCurr = clickedAirport;
			Controller.getInstance().pauseTimer();
		}
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
			
			g.fillRect(x, y, RECTANGLE_SIZE, RECTANGLE_SIZE);
			g.drawString(at.getCode(), x + RECTANGLE_SIZE, y);
		}
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
