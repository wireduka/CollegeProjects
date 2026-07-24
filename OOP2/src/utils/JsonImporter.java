package utils;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.google.gson.Gson;

import exception.InvalidFileFormatException;


public class JsonImporter implements Importable {
	
	private static class JsonAirport {
	    String code;
	    String name;
	    int x;
	    int y;
	}

	private static class JsonFlight {
	    String from;
	    String to;
	    String departure;
	    int duration;
	}
	
	private static class JsonData {
	    List<JsonAirport> airports;
	    List<JsonFlight> flights;
	}

	@Override
	public TokenizedData readFile(File file) throws FileNotFoundException, IOException, InvalidFileFormatException {
		
		Gson gson = new Gson();
		
		JsonData data = gson.fromJson(new FileReader(file),JsonData.class);
		if(data == null) throw new InvalidFileFormatException("JSON file is empty. ");
		
		List<List<String>> airportTokens = new ArrayList<>();
        List<List<String>> flightTokens = new ArrayList<>();
        
        if(data.airports != null) {
        	for (JsonAirport a : data.airports) {
        		List<String> tokens = new ArrayList<>();

        		tokens.add(a.code);
        		tokens.add(a.name);
        		tokens.add(String.valueOf(a.x));
        		tokens.add(String.valueOf(a.y));

        		airportTokens.add(tokens);
        	}
       	}
        
        if(data.flights != null) {
        	for (JsonFlight f : data.flights) {
            	List<String> tokens = new ArrayList<>();
            
            	tokens.add(f.from);
            	tokens.add(f.to);
            	tokens.add(String.valueOf(f.departure));
            	tokens.add(String.valueOf(f.duration));
            
            	flightTokens.add(tokens);
        	}
        }

        return new TokenizedData(airportTokens, flightTokens);
	}

}
