#include <filesystem>
#include <fstream>
#include "SemanticException.h"
#include "TouchCommand.h"

namespace fs = std::filesystem;
void TouchCommand::execute(istream& in, ostream& out)
{
	if (fs::exists(argument))
		throw SemanticException("Error - file already exists");
	else
		ofstream(argument).close();

}
