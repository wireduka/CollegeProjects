package utils;

import java.io.File;
import java.io.IOException;

public interface Exportable {
	
	void write(File file) throws IOException;

}
