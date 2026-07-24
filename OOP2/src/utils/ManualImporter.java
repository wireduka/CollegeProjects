package utils;

import java.util.ArrayList;
import java.util.List;

import model.DataType;

public class ManualImporter {
	
	public TokenizedData read(String text, DataType type) {
		
		List<String> tokens;
		List<List<String>> listTokens = new ArrayList<>();
	
		String[] splitText = text.split("\n");
		
		for(String line : splitText) {
			tokens = new ArrayList<>();
			for(String splitToken : line.split(",")) 
				tokens.add(splitToken.trim());
			listTokens.add(tokens);
		}
		
		TokenizedData data = null;
		
		switch(type) {
		case AIRPORT: 	data = new TokenizedData(listTokens,new ArrayList<>()); break;
		case FLIGHT: 	data = new TokenizedData(null,new ArrayList<>()); break;
		}
		
		return data;
		
		
	}

}
