#include "HeadCommand.h"
#include "SemanticException.h"
#include <iostream>
HeadCommand::HeadCommand() : Command()
{
	options = { "-n" };
}

void HeadCommand::validate(const vector<Token>& tokens){
	Command::validate(tokens);
	checkOption();
	standardInput(tokens, true);
	if (optionNumber > 99999 || optionNumber < 1)
		throw SemanticException("Error - number of out range");
}

void HeadCommand::execute(istream& in, ostream& out)
{
	string stream;
	string line;
	for (int i = 0; i < optionNumber; i++) {
		getline(in, line);
		stream += line + "\n";
	}
	if (!stream.empty() && stream.back() == '\n')
		stream.pop_back();
	out << stream;
}
