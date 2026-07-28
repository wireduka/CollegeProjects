package logic;

import java.util.ArrayList;
import java.util.List;

import model.Airport;
import model.AirportTable;
import model.Flight;
import model.FlightTable;
import model.Flight.FlightStatus;

public class Scheduler implements Observer {
	
	private FlightTable flightTable;
	private AirportTable airportTable;
	private List<Flight> activeFlights = new ArrayList<>();
	
	public Scheduler(Clock simulationClock,FlightTable flightTable, AirportTable airportTable) {
		this.flightTable = flightTable;
		this.airportTable = airportTable;
		simulationClock.addObserver(this);
		flightTable.addObserver(this);
	}

	@Override
	public void onObserverSignal(EventType event) {
		switch(event) {
			case CLOCK:	processEvents(); break;
			case TABLE:	break;
		}
	}
	
	private void processEvents() {
		for(Airport at : airportTable.getAirports() ) {
			for(Flight ft : at.getFlights()) {
				if(ft.getDeparture() <= Controller.getInstance().getSimulationTime() && ft.getStatus() == FlightStatus.WAITING) {
					at.addToDepartureQueue(ft);
					ft.setStatus(FlightStatus.QUEUED);
				}
			}
			if(!at.getDepartureQueue().isEmpty()) {
				Flight inFlight = at.getDepartureQueue().remove(0);
				inFlight.setStatus(Flight.FlightStatus.IN_FLIGHT);
				System.out.println("Flight:" + inFlight.getFrom() + "->" + inFlight.getTo() + " in flight." + " Time: " + Controller.getInstance().getSimulationTime()); // TEMP
				activeFlights.add(inFlight);
			}
			
		}
		for(int i = 0 ; i < activeFlights.size();) {
			Flight active = activeFlights.get(i);
			if((active.getDeparture() + active.getDuration()) <= Controller.getInstance().getSimulationTime()) {
				active.setStatus(Flight.FlightStatus.LANDED);
				System.out.println("Flight:" + active.getFrom() + "->" + active.getTo() + " landed." + " Time: " + Controller.getInstance().getSimulationTime()); // TEMP
				activeFlights.remove(i);
			}
			else i++;
		}
	}
	
	public void resetScheduling() {
		for(Flight f : flightTable.getFlights()) { f.setStatus(FlightStatus.WAITING);}
		for(Airport a : airportTable.getAirports()) {a.getDepartureQueue().clear();}
	}

}
