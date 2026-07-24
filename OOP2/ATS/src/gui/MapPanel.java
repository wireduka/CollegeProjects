package gui;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import logic.BlinkTimer;
import logic.Controller;
import model.Airport;
import model.AirportTable;
import model.Observer;

public class MapPanel extends Canvas implements Observer{
	
	private AirportTable airportTable;
	private Airport selected;
	private BlinkTimer blinkTimer;
	private static final int RECTANGLE_SIZE = 20;
	
	
	public MapPanel(AirportTable airportTable, Controller controller) {
		
		this.airportTable = airportTable;
		this.blinkTimer = new BlinkTimer(this);
		blinkTimer.start();
		
		this.addMouseListener(new MouseAdapter() {
			
			@Override
			public void mouseClicked(MouseEvent e) {
				
				Airport clickedAirport = getAirportAt(e.getX(),e.getY());
				if(clickedAirport == selected) { selected = null; controller.resumeTimer();}
				else if(clickedAirport != null) {selected = clickedAirport; controller.pauseTimer();}

				
			}
		});
		airportTable.addObserver(this);
	}

	@Override
	public void onObserverSignal() {
		repaint();
	}
	@Override
	public void paint(Graphics g) {
		g.setColor(Color.GRAY);
		g.fillRect(0,0,getWidth(), getHeight());
		
		// Draws a rectangle at the specified pixel position
		for(Airport at : airportTable.getAirports()) {
			int x = toPixelX(at.getX());
			int y = toPixelY(at.getY());
			
			if(at == selected && blinkTimer.getBlinkState())
				g.setColor(Color.RED);
			else
				g.setColor(Color.DARK_GRAY);
			
			g.fillRect(x, y, RECTANGLE_SIZE, RECTANGLE_SIZE);
			g.drawString(at.getCode(), x + RECTANGLE_SIZE, y);
		}
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
	
	// update method overriding to eliminate screen flickering when repaint is called
	@Override
	public void update(Graphics g) {
	    Image buffer = createImage(getWidth(), getHeight());
	    Graphics background = buffer.getGraphics();
	    
	    paint(background);
	    
	    g.drawImage(buffer, 0, 0, this);
	    background.dispose();
	}
	
	
	

}
