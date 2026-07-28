package gui;

import java.awt.BorderLayout;
import java.awt.Checkbox;
import java.awt.GridLayout;
import java.awt.Label;
import java.awt.List;
import java.awt.Panel;
import java.awt.ScrollPane;
import java.awt.Scrollbar;

import logic.Controller;
import logic.EventType;
import logic.Observer;
import model.Airport;
import model.AirportTable;

public class AirportTablePanel extends Panel implements Observer{
	
	private AirportTable airportTable;
	private Panel listPanel = new Panel(new GridLayout(0,1));
	private ScrollPane scroll = new ScrollPane();
	
	public AirportTablePanel() {
		
		this.airportTable = Controller.getInstance().getAirportTable();
		airportTable.addObserver(this);
		this.setLayout(new BorderLayout());
		
		scroll.add(listPanel);
		scroll.setSize(300, 400);
		add(new Label("AIRPORTS", Label.CENTER),BorderLayout.NORTH);
		add(scroll,BorderLayout.CENTER);
	}

	@Override
	public void onObserverSignal(EventType event) {
		if(event == EventType.TABLE) {
			listPanel.removeAll();
			for(Airport airport : airportTable.getAirports()) {
				Checkbox cb = new Checkbox(airport.getCode() + " | " + airport.getName() + " | " + airport.getX() + "," + airport.getY(), true);
				cb.addItemListener(e -> {airport.setVisible(cb.getState()); Controller.getInstance().resetTimer();});
				listPanel.add(cb);
				
			}
			listPanel.revalidate();
		}
	}
}
