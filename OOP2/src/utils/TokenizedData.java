package utils;

import java.util.List;

public class TokenizedData {
	
	public List<List<String>> flightTokens;
	public List<List<String>> airportTokens;
	
	// Tokenized data structure
	public TokenizedData(List<List<String>> airportTokens, List<List<String>> flightTokens) {
		
		this.airportTokens = airportTokens;
		this.flightTokens = flightTokens;
	}
	
	

}
