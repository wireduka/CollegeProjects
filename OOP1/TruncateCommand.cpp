#include <filesystem>
#include <fstream>
#include "SemanticException.h"
#include "TruncateCommand.h"

namespace fs = std::filesystem;

void TruncateCommand::execute(istream& in, ostream& out)
{
	if (!fs::exists(argument))
		throw SemanticException("Error - file does not exist");
	else
		ofstream(argument, ios::trunc).close();
}
