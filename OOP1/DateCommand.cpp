#include "DateCommand.h"
#include "SemanticException.h"
#include <ctime>
void DateCommand::validate(const vector<Token>& tokens)
{
	if (!tokens.empty())
		throw SemanticException("Error - command does not support arguments");
}

void DateCommand::execute(istream& in, ostream& out)
{
	struct tm timeStruct;
	time_t currTime = time(0);
	localtime_s(&timeStruct, &currTime);
	out << timeStruct.tm_mday << "." << timeStruct.tm_mon + 1 << "." << timeStruct.tm_year + 1900;
}
