package utils;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

import model.Airport;
import model.AirportTable;
import model.Flight;
import model.FlightTable;

public class CsvExporter implements Exportable {
	
	private AirportTable airportTable;
	private FlightTable flightTable;
	
	
	public CsvExporter(AirportTable airportTable, FlightTable flightTable) {
		this.airportTable = airportTable;
		this.flightTable = flightTable;
	}

	@Override
	public void write(File file) throws IOException{
		
		try (PrintWriter writer = new PrintWriter(new FileWriter(file))) {
			
			if(!airportTable.isEmpty()) { 
				writer.println("# AIRPORTS");
				writer.println("CODE,NAME,X,Y");
				
				for(Airport airport : airportTable.getAirports()) {
					writer.printf("%s,%s,%d,%d\n", airport.getCode(),airport.getName(),airport.getX(),airport.getY());
				}
				if(!flightTable.isEmpty()) writer.println();
			}
			
			if(!flightTable.isEmpty()) {
				writer.println("# FLIGHTS");
				writer.println("FROM,TO,DEPARTURE,DURATION");
				
				for(Flight flight : flightTable.getFlights()) {
					writer.printf("%s,%s,%s,%d\n", flight.getFrom(),flight.getTo(),flight.getDepartureString(),flight.getDuration());
				}
			}
		}
	}

}
