package utils;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import model.Airport;
import model.AirportTable;
import model.Flight;
import model.FlightTable;

public class JsonExporter implements Exportable{
	
	@SuppressWarnings("unused")
	private static class JsonAirport {
	    String code;
	    String name;
	    int x;
	    int y;
	}
	@SuppressWarnings("unused")
	private static class JsonFlight {
	    String from;
	    String to;
	    String departure;
	    int duration;
	}
	
	private static class JsonData {
	    List<JsonAirport> airports = new ArrayList<>();
	    List<JsonFlight> flights = new ArrayList<>();
	}
	
	private AirportTable airportTable;
	private FlightTable flightTable;
	
	
	public JsonExporter(AirportTable airportTable, FlightTable flightTable) {
		this.airportTable = airportTable;
		this.flightTable = flightTable;
	}
	
	@Override
	public void write(File file) throws IOException {
		
		JsonData data = new JsonData();

		for (Airport airport : airportTable.getAirports()) {
		    JsonAirport ja = new JsonAirport();

		    ja.code = airport.getCode();
		    ja.name = airport.getName();
		    ja.x = airport.getX();
		    ja.y = airport.getY();

		    data.airports.add(ja);
		}

		for (Flight flight : flightTable.getFlights()) {
		    JsonFlight jf = new JsonFlight();

		    jf.from = flight.getFrom();
		    jf.to = flight.getTo();
		    jf.departure = flight.getDepartureString();
		    jf.duration = flight.getDuration();

		    data.flights.add(jf);
		}

		Gson gson = new GsonBuilder()
		        .setPrettyPrinting()
		        .create();

		try (FileWriter writer = new FileWriter(file)) {
		    gson.toJson(data, writer);
		}
		
	}

}
