package gui;

import utils.ImportExportManager.FileType;

import java.awt.BorderLayout;
import java.awt.Frame;

import logic.Controller;
import model.DataType;


public class MainFrame extends Frame implements WindowClosing{
	
	{
		Controller.initialize(this);
	}
	
	// File managing
	private MenuBuilder menuBuilder = new MenuBuilder();
	private AirportTablePanel atp = new AirportTablePanel(); 
	private FlightTablePanel ftp = new FlightTablePanel();
	private ControlPanel cp = new ControlPanel();
	private MapPanel mp = new MapPanel();
	
	// Frame constructor
	public MainFrame() {
		
		super("ATS");
		this.setSize(1600,800);
		setupWindowClosingWithExit(this);
		setupMenu();
		setLayout(new BorderLayout());
		add(atp,BorderLayout.WEST);
		add(mp, BorderLayout.CENTER);
		add(ftp,BorderLayout.EAST);
		add(cp,BorderLayout.SOUTH);
		
		this.setVisible(true);
		
	}
	// Method for menu handling
	private void setupMenu() {
		
		Controller controller = Controller.getInstance();
		
		this.setMenuBar(menuBuilder.buildMenu(
				e-> controller.requestManual(DataType.FLIGHT),							// FlightEntryAction
				e-> controller.requestManual(DataType.AIRPORT),							// AirportEntryAction
				e-> controller.requestImport(FileType.JSON),							// jsonImportAction
				e-> controller.requestImport(FileType.CSV),								// csvImportAction
				e-> controller.requestExport(FileType.JSON),							// jsonExportAction
				e-> controller.requestExport(FileType.CSV)								// csvExportAction
				));
	}
}
	

	

