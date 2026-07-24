package gui;

import java.awt.BorderLayout;
import java.awt.Label;
import java.awt.List;
import java.awt.Panel;

import model.Airport;
import model.AirportTable;
import model.Observer;

public class AirportTablePanel extends Panel implements Observer{
	
	private AirportTable airportTable;
	private List listComponent = new List(20,false);
	
	public AirportTablePanel(AirportTable airportTable) {
		
		this.airportTable = airportTable;
		airportTable.addObserver(this);
		this.setLayout(new BorderLayout());
		add(new Label("AIRPORTS", Label.CENTER),BorderLayout.NORTH);
		add(listComponent,BorderLayout.CENTER);
	}

	@Override
	public void onObserverSignal() {
		listComponent.removeAll();
		for(Airport airport : airportTable.getAirports()) {
			listComponent.add(airport.getCode() + " | " + airport.getName() + " | " + airport.getX() + "," + airport.getY());
		}
		
	}

}
