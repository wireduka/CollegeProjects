package gui;

import java.awt.Menu;
import java.awt.MenuBar;
import java.awt.MenuItem;
import java.awt.event.ActionListener;

public class MenuBuilder {

	public MenuBar buildMenu(
			ActionListener flightEntryAction,
			ActionListener airportEntryAction,
			ActionListener jsonImportAction,
			ActionListener csvImportAction,
			ActionListener jsonExportAction,
			ActionListener csvExportAction
			) 
	{
		MenuBar menuBar = new MenuBar();
		Menu fileMenu = new Menu("File");

		// File menu sub-menus
		Menu importMenu = new Menu("Import as");
		Menu exportMenu = new Menu("Export as");
		
		// Import sub-menu items
		Menu entryMenu = new Menu("Entry");
		MenuItem jsonImport = new MenuItem("JSON");
		MenuItem csvImport = new MenuItem("CSV");
		
		// Export sub-menu items
		MenuItem jsonExport = new MenuItem("JSON");
		MenuItem csvExport = new MenuItem("CSV");
		
		// Entry sub-menu items
		MenuItem flightEntry = new MenuItem("Flights");
		MenuItem airportEntry = new MenuItem("Airports");
		
		// Entry sub-menu adding
		entryMenu.add(airportEntry);
		entryMenu.add(flightEntry);
		
		// Import sub-menu adding
		importMenu.add(entryMenu);
		importMenu.add(jsonImport);
		importMenu.add(csvImport);
		
		// Export sub-menu adding
		exportMenu.add(jsonExport);
		exportMenu.add(csvExport);
		
		fileMenu.add(importMenu);
		fileMenu.add(exportMenu);
		
		menuBar.add(fileMenu);
		
		flightEntry.addActionListener(flightEntryAction);
		airportEntry.addActionListener(airportEntryAction);
		jsonImport.addActionListener(jsonImportAction);
		csvImport.addActionListener(csvImportAction);
		jsonExport.addActionListener(jsonExportAction);
		csvExport.addActionListener(csvExportAction);
		
		return menuBar;
		
	}
}
