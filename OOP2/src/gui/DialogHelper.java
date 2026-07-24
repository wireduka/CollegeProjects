package gui;

import java.awt.FileDialog;
import java.awt.Frame;
import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;

import model.DataType;
import utils.ImportExportManager;
import utils.ImportExportManager.Mode;

// Helper class for dialog opening and closing
public class DialogHelper {
	
	public File openFileDialog(Frame owner,ImportExportManager.Mode mode, ImportExportManager.FileType type ) {
		
		// FileDialog requires integer constant
		int fdMode = mode == Mode.IMPORT ? FileDialog.LOAD : FileDialog.SAVE;
		
		FileDialog fd = new FileDialog(owner, fdMode == FileDialog.LOAD ? "Import" : "Export", fdMode);
		fd.setVisible(true);
		
		// Helper variables
		String fileName = fd.getFile();
		String fileDirectory = fd.getDirectory();
		String expectedExtension;
		
		// If user clicks Cancel
		if(fileName == null) return null;
		
		// Adding extension cases
		switch(type) {
		case JSON: expectedExtension = ".json"; 	break;
		case CSV: expectedExtension = ".csv"; 		break;
		default: expectedExtension = "";			break;
		}
		
		// Checking for extension
		if(!fileName.endsWith(expectedExtension)) {
			new TextDialog(owner,"Error","Invalid file format. Expected a " + 
							expectedExtension + " file.", "Please select a valid " + 
							expectedExtension + " file.");
			return null;
		}

		// Paths.get returns String as return value
		Path path = Paths.get(fileDirectory,fileName);
		File file = path.toFile();
		
		// Delegate to Controller for further processing
		return file;

		
	}
	
	// Helper method for text importing
	public String openTextDialog(Frame owner,DataType type) {
		String dialogText = (type == DataType.AIRPORT) ? "AIRPORT format: CODE, NAME, X, Y" : "FLIGHT format: FROM, TO, DEPARTURE, DURATION";
		TextAreaDialog txtDialog = new TextAreaDialog(owner, dialogText);
		String text = txtDialog.getInput();
		
		if(!txtDialog.isConfirmed())
			return null;
		// Delegate to Controller for further processing
		return text;

		
	}
}
