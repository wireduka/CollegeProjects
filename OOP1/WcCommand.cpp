#include "WcCommand.h"
#include "SemanticException.h"

WcCommand::WcCommand() : Command(){
	options = { "-w", "-c" };
}

void WcCommand::validate(const vector<Token>& tokens){
	Command::validate(tokens);
	if (tokens.size() > 1)
		throw SemanticException("Error - too many arguments for wc command");

	standardInput(tokens,true);
	
}

void WcCommand::execute(istream& in, ostream& out)
{
	int count = 0;
	if (option == "-c") {
		char c;
		while (in.get(c))
			count++;
		
	}
	else if (option == "-w") {
		string word;
		while (in >> word)
			count++;
		
	}
	out << count;
}
