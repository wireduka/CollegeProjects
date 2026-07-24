#include "TimeCommand.h"
#include "SemanticException.h"
#include <ctime>
void TimeCommand::validate(const vector<Token>& tokens)
{
	if (!tokens.empty()) {
		throw SemanticException("Error - time does not support arguments");
	}
}

void TimeCommand::execute(istream& in, ostream& out)
{
	struct tm timeStruct;
	time_t currTime = time(0);
	localtime_s(&timeStruct,&currTime);
	out << timeStruct.tm_hour << ":" << timeStruct.tm_min << ":" << timeStruct.tm_sec;
}
