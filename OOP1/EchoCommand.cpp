#include "EchoCommand.h"
#include "SemanticException.h"
#include <iostream>
void EchoCommand::validate(const vector<Token>& tokens){
	Command::validate(tokens);
	if (tokens.size() > 1)
		throw SemanticException("Error - too many arguments for echo command");

	standardInput(tokens,true);
}

void EchoCommand::execute(istream& in, ostream& out)
{
	char c;
	while (in.get(c)) {
		out.put(c);
	}
}
