package gui;

import java.awt.BorderLayout;
import java.awt.Label;
import java.awt.List;
import java.awt.Panel;

import model.Flight;
import model.FlightTable;
import model.Observer;

public class FlightTablePanel extends Panel implements Observer{
	
	private FlightTable flightTable;
	private List listComponent = new List(20,false);
	
	public FlightTablePanel(FlightTable flightTable) {
		
		this.flightTable = flightTable;
		flightTable.addObserver(this);
		this.setLayout(new BorderLayout());
		add(new Label("FLIGHTS", Label.CENTER),BorderLayout.NORTH);
		add(listComponent,BorderLayout.CENTER);
	}

	@Override
	public void onObserverSignal() {
		listComponent.removeAll();
		for(Flight flight : flightTable.getFlights()) {
			listComponent.add(flight.getFrom() + " | " + flight.getTo() + " | " + flight.getDepartureString() + " | " + flight.getDuration());
		}
		
	}

}
