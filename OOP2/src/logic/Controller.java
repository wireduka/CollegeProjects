package logic;

import java.awt.Frame;
import java.io.File;

import gui.DialogHelper;
import gui.MapPanel;
import gui.TextDialog;
import model.AirportTable;
import model.DataType;
import model.FlightTable;
import utils.ImportExportManager;
import utils.ImportExportManager.Mode;

public class Controller {
	
	private AirportTable airportTable = new AirportTable();
	private FlightTable flightTable = new FlightTable();
	private DialogHelper dialogHelper = new DialogHelper();
	private ImportExportManager ioManager;
	private boolean simulationReady = false;
	private boolean simulationActive = false;
	private Frame owner;
	private InactivityTimer inactivityTimer;
	private Scheduler scheduler;
	private Clock simulationClock = new Clock(1000);
    private Clock animationClock = new Clock(200);
	private static Controller instance = null;
	
	// Method for singleton initialization
	public static void initialize(Frame owner) {
        if (instance == null) {
            instance = new Controller(owner);
        }
    }
	
	// Grants the instance of the object to the caller
	public static Controller getInstance() {
		if (instance == null) {
            throw new IllegalStateException("Controller not initialized");
        }
        return instance;
	}
	
	// Constructor
	private Controller(Frame owner) {
		this.owner = owner;
		this.ioManager = new ImportExportManager(owner,airportTable,flightTable);
		this.inactivityTimer = new InactivityTimer(owner);
		this.scheduler = new Scheduler(simulationClock,flightTable,airportTable);
		inactivityTimer.start();
	}
	
	// Sends a request to the IOManager
	private void requestFileOperation(ImportExportManager.FileType type, ImportExportManager.Mode mode ) {
		
		inactivityTimer.resetTimer();
		
		File file = dialogHelper.openFileDialog(owner, mode, type);
		if(file == null) return;
		
		ioManager.handle(file, type, mode);
		inactivityTimer.resetTimer();
		simulationReady = true;
	}
	// Sends an import request
	public void requestImport(ImportExportManager.FileType type ) {
		requestFileOperation(type,Mode.IMPORT);
	}
	
	// Sends an export request
	public void requestExport(ImportExportManager.FileType type ) {
		requestFileOperation(type,Mode.EXPORT);
	}
	
	// Separate method for manual requests
	public void requestManual(DataType type ) {
		
		inactivityTimer.resetTimer();
		
		String text = dialogHelper.openTextDialog(owner, type);
		if(text == null) return;
		
		ioManager.handle(text, type);
		inactivityTimer.resetTimer();
		if(type == DataType.FLIGHT) simulationReady = true;
	}
	
	public AirportTable getAirportTable() {
		return airportTable;
	}
	
	public FlightTable getFlightTable() {
		return flightTable;
	}

	
	// Timer manipulation
	public void pauseTimer() { inactivityTimer.pauseTimer(); }
	public void resumeTimer() { inactivityTimer.resumeTimer(); }
	public void resetTimer() {inactivityTimer.resetTimer();}
	
	// Starts the simulation
	public void startSimulation() {
		if(!simulationReady) {new TextDialog(owner,"Warning"," Flights have not been imported, please insert an entry."); return;}
		simulationActive = true;
		
		inactivityTimer.pauseTimer();
		
		if(!simulationClock.isAlive()) {
			simulationClock.start();
			animationClock.start();
		}
		else {
			simulationClock.resumeClock(); 
			animationClock.resumeClock();
		}
	}
	
	// Pauses the simulation
	public void pauseSimulation() {
		if(!simulationReady) {new TextDialog(owner,"Warning"," Flights have not been imported, please insert an entry."); return;}
		simulationActive = false;
		
		simulationClock.pauseClock();
		animationClock.pauseClock();
		if(!MapPanel.isSelected()) {inactivityTimer.resumeTimer();} 
	}
	
	// Resets the simulation
	public void resetSimulation() {
		if(!simulationReady) {new TextDialog(owner,"Warning"," Flights have not been imported, please insert an entry."); return;}
		simulationActive = false;
		
		simulationClock.resetClock();
		animationClock.resetClock();
		scheduler.resetScheduling();
		// TODO deleting all Flight GUI objects
	}
	
	// Getter methods
	public Clock getSimulationClock() {return simulationClock;}
	public String getSimulationTimeString() {return simulationClock.getClockString();}
	public int getSimulationTime() {return simulationClock.getSimulationTime();}
	public Clock getAnimationClock() {return animationClock;}
	
	public boolean isSimulationActive() {return simulationActive;}
	
	

}
