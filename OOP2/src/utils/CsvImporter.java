package utils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


public class CsvImporter implements Importable{

	@Override
	public TokenizedData readFile(File file) throws FileNotFoundException, IOException {
		
		try(BufferedReader reader = new BufferedReader(new FileReader(file))){
			
			String line;
			String section = null;
			List<List<String>> airports = new ArrayList<>();
			List<List<String>> flights = new ArrayList<>();
			
			while((line = reader.readLine()) != null) {
				
				// Checking for FLIGHTS/AIRPORTS section
				if(line.startsWith("#")) {
					section = line.trim();
					continue;
				}
				// Skip header row
				if(line.startsWith("CODE") || line.startsWith("FROM")) {
					continue;
				}
				// Skip empty rows
				if(line.isEmpty()) continue;
				
				// Add airport to ArrayList
				if(section.contains("AIRPORTS") && !line.isEmpty()) {
					ArrayList<String> airport = new ArrayList<>();
					for(String token : line.split(","))
						airport.add(token.trim());
					airports.add(airport);
				}
				// Add flight to ArrayList
				if(section.contains("FLIGHTS") && !line.isEmpty()) {
					ArrayList<String> flight = new ArrayList<>();
					for(String token : line.split(","))
						flight.add(token.trim());
					flights.add(flight);
				}
			};
			
			return new TokenizedData(airports,flights);
		
		}
	}
}
