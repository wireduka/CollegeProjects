package utils;

import java.io.File;
import java.io.IOException;
// Used for exporters
public interface Exportable {
	
	void write(File file) throws IOException;

}
