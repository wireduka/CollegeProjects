package utils;

import java.awt.Frame;
import java.util.ArrayList;
import java.util.List;

import exception.InvalidDataException;
import exception.InvalidFileFormatException;
import gui.TextDialog;
import model.Airport;
import model.Airport.AirportVar;
import model.AirportTable;
import model.Coordinate;
import model.Flight;
import model.FlightTable;

public class Parser {
	
	// Variables
	private AirportTable airportTable;
	private FlightTable flightTable;
	private Frame owner;
	
	// Duplicates
	private List<String> duplicateCodes;
	private List<Coordinate> duplicateCoordinates;
	
	// Main constructor
	public Parser(AirportTable airportTable, FlightTable flightTable, Frame owner) {
		this.airportTable = airportTable;
		this.flightTable = flightTable;
		this.owner = owner;
	}

	public void parse(TokenizedData tokens) throws InvalidFileFormatException, NumberFormatException, InvalidDataException{
		
		if(!tokens.airportTokens.isEmpty())
			parseAirports(tokens.airportTokens);
		if(!tokens.flightTokens.isEmpty());
			parseFlights(tokens.flightTokens);
	}
	
	public void parseAirports(List<List<String>> airportTokens) throws InvalidFileFormatException, NumberFormatException, InvalidDataException {
		
		if(airportTokens.isEmpty()) throw new InvalidFileFormatException("No entries.");
		
		duplicateCodes = new ArrayList<>();
		duplicateCoordinates = new ArrayList<>();
		
		for(List<String> airportToken : airportTokens) {
			if(airportToken.size() != 4) throw new InvalidFileFormatException("Invalid number of tokens, check file structure ");
			
			String code = airportToken.get(0);
			String name = airportToken.get(1);
			
			int x = Integer.parseInt(airportToken.get(2));
			int y = Integer.parseInt(airportToken.get(3));
			
			Coordinate coordinate = new Coordinate(x,y);
			
			if(parseCodeAndReturnDuplicate(code)) continue;
			if(parseCoordinateAndReturnDuplicate(x,y,coordinate)) continue;
			parseAirportName(name);
			generateAirport(code,name,coordinate);
		}
		
		if(!duplicateCodes.isEmpty() || !duplicateCoordinates.isEmpty()) {
			new TextDialog(owner,"Duplicates!", "Airports skipped due to duplicate codes/coordinates: ",String.join(", ", duplicateCodes));
		}
		
	}
	public void parseFlights(List<List<String>> flightTokens) throws InvalidFileFormatException, NumberFormatException, InvalidDataException{
		
		if(flightTokens.isEmpty()) throw new InvalidFileFormatException("No entries.");
		
		for(List<String> flightToken : flightTokens) {
			if(flightToken.size() != 4) throw new InvalidFileFormatException("Invalid number of tokens, check file structure ");
			
			String from = flightToken.get(0);
			String to = flightToken.get(1);
			String departure = flightToken.get(2);
			
			int duration = Integer.parseInt(flightToken.get(3));
			
			parseFlightCodes(from,to);
			int departureTime = parseTime(departure,duration);
			generateFlight(from,to,departureTime,duration);
		}
	}
	private void generateFlight(String from, String to, int departureTime, int duration) {
		Flight flight = new Flight(from,to,departureTime,duration);
		flightTable.add(flight);
		
	}

	private boolean parseCodeAndReturnDuplicate(String code) throws InvalidDataException{
		
		if(!isValidCode(code)) throw new InvalidDataException("Code length must be 3 characters and written only using upper case letters, code in file: " + code);
		if(airportTable.exists(code, AirportVar.CODE)) {duplicateCodes.add(code); return true;}
		else return false;
	}
	
	private boolean parseCoordinateAndReturnDuplicate(int x, int y, Coordinate coordinate)  throws InvalidDataException{
		
		if((x > Airport.MAX_X || x < Airport.MIN_X) || (y > Airport.MAX_Y || y < Airport.MIN_Y))
			throw new InvalidDataException("X must be in interval [-180,180], Y must be in interval [-90,90], X: " + x + " Y:" + y);
		if(airportTable.exists(coordinate, AirportVar.COORDINATE)) {duplicateCoordinates.add(coordinate); return true;}
			
		return false;
	}
	
	private void parseAirportName(String name) throws InvalidDataException {
		if(name.isEmpty()) throw new InvalidDataException("Name cannot be empty.");
	}
	
	private void generateAirport(String code, String name, Coordinate coordinate) {
		Airport airport = new Airport(code,name,coordinate);
		airportTable.add(airport);
	}
	
	private void parseFlightCodes(String from, String to) throws InvalidDataException {
		
		if(!isValidCode(from)) throw new InvalidDataException("Code length must be 3 characters and written only using upper case letters, code in file: " + from);
		if(!isValidCode(to)) throw new InvalidDataException("Code length must be 3 characters and written only using upper case letters, code in file: " + to);
		
		boolean foundFrom = false;
		boolean foundTo = false;
		
		for(Airport airport : airportTable.getAirports()) {
			if(airport.getCode().equals(from)) foundFrom = true;
			if(airport.getCode().equals(to)) foundTo = true;
		}
		
		if(!foundFrom) {throw new InvalidDataException("Flight with departure " + from + " does not have a corresponding departure airport");}
		if(!foundTo) {throw new InvalidDataException("Flight with destination " + to + " does not have a corresponding target airport");}
		if(!foundFrom && !foundTo) {throw new InvalidDataException("Flight: " + from + "->" + to + " does not have a corresponding target and departure airport");}
		
	}
	
	private int parseTime(String departureTime, int flightDuration) throws InvalidDataException {
		String[] temp = departureTime.split(":");
		if(temp.length != 2) throw new InvalidDataException("Invalid flight time format, number of tokens must be 2");
		
		int hours = Integer.parseInt(temp[0]);
		int minutes = Integer.parseInt(temp[1]);
		
		if(hours > 23 || hours < 0) throw new InvalidDataException("Invalid flight time format, specified hour must be between 0 and 23.");
		if(minutes > 59 || minutes < 0) throw new InvalidDataException("Invalid flight time format, specified minutes must be between 0 and 59.");
		
		if(flightDuration <= 0) throw new InvalidDataException("Invalid flight time format, specified flight duration must be longer than 0.");
		return hours * 60 + minutes;
		
	}
	
	private boolean isValidCode(String code) {
		return code.matches("[A-Z]{3}");
	}
	
}
