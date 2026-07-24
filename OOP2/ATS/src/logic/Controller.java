package logic;

import java.awt.Frame;
import java.io.File;

import gui.DialogHelper;
import model.AirportTable;
import model.DataType;
import model.FlightTable;
import utils.ImportExportManager;
import utils.ImportExportManager.Mode;

public class Controller {
	
	private AirportTable airportTable;
	private FlightTable flightTable;
	private DialogHelper dialogHelper;
	private ImportExportManager ioManager;
	private Frame owner;
	private InactivityTimer inactivityTimer;
	
	public Controller(Frame owner) {
		this.owner = owner;
		this.dialogHelper = new DialogHelper();
		this.airportTable = new AirportTable();
		this.flightTable = new FlightTable();
		this.ioManager = new ImportExportManager(owner,airportTable,flightTable);
		this.inactivityTimer = new InactivityTimer(owner);
		inactivityTimer.start();
	}
	
	private void requestFileOperation(ImportExportManager.FileType type, ImportExportManager.Mode mode ) {
		
		inactivityTimer.resetTimer();
		
		File file = dialogHelper.openFileDialog(owner, mode, type);
		if(file == null) return;
		
		ioManager.handle(file, type, mode);
		inactivityTimer.resetTimer();
	}
	
	public void requestImport(ImportExportManager.FileType type ) {
		requestFileOperation(type,Mode.IMPORT);
	}
	public void requestExport(ImportExportManager.FileType type ) {
		requestFileOperation(type,Mode.EXPORT);
	}
	
	public void requestManual(DataType type ) {
		
		inactivityTimer.resetTimer();
		
		String text = dialogHelper.openTextDialog(owner, type);
		if(text == null) return;
		
		ioManager.handle(text, type);
		inactivityTimer.resetTimer();
	}
	
	public AirportTable getAirportTable() {
		return airportTable;
	}
	
	public FlightTable getFlightTable() {
		return flightTable;
	}
	
	// Timer pausing
	public void pauseTimer() { inactivityTimer.pauseTimer(); }
	public void resumeTimer() { inactivityTimer.resumeTimer(); }

}
