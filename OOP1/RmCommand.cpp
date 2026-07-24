#include <filesystem>
#include <fstream>
#include "SemanticException.h"
#include "RmCommand.h"

namespace fs = std::filesystem;

void RmCommand::execute(istream& in, ostream& out)
{
	if (!fs::exists(argument))
		throw SemanticException("Error - file does not exist");
	else
		fs::remove(argument);
}
