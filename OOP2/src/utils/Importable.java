package utils;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

import exception.InvalidFileFormatException;

public interface Importable {
	
	TokenizedData readFile(File file) throws FileNotFoundException, IOException, InvalidFileFormatException;

}
