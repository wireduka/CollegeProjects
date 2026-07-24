package utils;

import java.awt.Frame;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import exception.InvalidDataException;
import exception.InvalidFileFormatException;
import gui.TextDialog;
import model.AirportTable;
import model.DataType;
import model.FlightTable;

public class ImportExportManager {
	
	// For further processing
	private AirportTable airportTable;
	private FlightTable flightTable;
	
	// Used for Error dialog ownership 
	private Frame owner;
	
	// Importers/exporters, parser
	private CsvImporter csvImporter;
	private JsonImporter jsonImporter;
	private ManualImporter manualImporter;
	private CsvExporter csvExporter;
	private JsonExporter jsonExporter;
	private Parser parser;
	
	// Used for separating file types
	public enum FileType{
		CSV,JSON
	}
	// Used for separating import and export cases
	public enum Mode{
		IMPORT,EXPORT
	}
	// Main constructor
	public ImportExportManager(Frame owner, AirportTable at, FlightTable ft) {
		
		this.owner = owner;
		this.airportTable = at;
		this.flightTable = ft;
		this.csvImporter = new CsvImporter();
		this.jsonImporter = new JsonImporter();
		this.manualImporter = new ManualImporter();
		this.csvExporter = new CsvExporter(at, ft);
		this.jsonExporter = new JsonExporter(at,ft);
		this.parser = new Parser(airportTable, flightTable, owner);
	}
	// Method for file handling
	public void handle(File file, FileType type, Mode mode) {
		
		if(type == FileType.CSV && mode == Mode.IMPORT) {processImport(csvImporter,file);}
		if(type == FileType.JSON && mode == Mode.IMPORT) {processImport(jsonImporter,file);}
		if(type == FileType.CSV && mode == Mode.EXPORT) {processExport(csvExporter,file);}
		if(type == FileType.JSON && mode == Mode.EXPORT) {processExport(jsonExporter,file);}
		
			
	}
	// Method for text handling
	public void handle(String text, DataType type) {
		
		TokenizedData tokens = manualImporter.read(text, type);
		callParser(tokens);
		
	}
	// Calls importer and sends tokens to parser
	public void processImport(Importable importer, File file) {
		
		try {
			TokenizedData tokens = importer.readFile(file);
			callParser(tokens);
			}
		catch(FileNotFoundException e) {
			new TextDialog(owner,"Error","File not found"," The file may have been moved or deleted");
			}
		catch(IOException e) {
			new TextDialog(owner,"Error","Unable to read file","The file might be open in another program or the program does not have privileges to read the file");
		} 
		catch (InvalidFileFormatException e) {
			new TextDialog(owner,"Error",e.getMessage());
		}
	}
	
	public void processExport(Exportable exporter, File file) {
		
		try {
			exporter.write(file);
		} 
		catch (IOException e) {
			new TextDialog(owner,"Error","Unable to write in file","The file might be open in another program or the program does not have privileges to read the file");
		}
	}
	// Calls parser and updates tables
	public void callParser(TokenizedData tokens) {
		
		try {
			parser.parse(tokens);
			}
		catch (InvalidFileFormatException e) {
			new TextDialog(owner,"Error",e.getMessage());
		}
		catch (NumberFormatException e) {
			new TextDialog(owner,"Error","Invalid x/y coordinate, check if coordinates represent numbers");
		}
		catch (InvalidDataException e) {
			new TextDialog(owner,"Error",e.getMessage());
		}
	}
}
