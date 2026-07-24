#include "PromptCommand.h"
#include "SemanticException.h"
#include "Program.h"

PromptCommand::PromptCommand(Program& program) : Command(), program(program)
{
}
void PromptCommand::validate(const vector<Token>& tokens) {
	Command::validate(tokens);
	
	if (tokens.size() > 1)
		throw SemanticException("Error - too many arguments for prompt command");
	else if ( tokens.empty() || !tokens[0].type == QUOTED_STRING) {
		throw SemanticException("Error - argument must exist and must be enclosed in quotation marks");
	}
	else
		argument = tokens[0].value;
	
}

void PromptCommand::execute(istream& in, ostream& out)
{ 
	
	program.getInstance()->setPrompt(argument);
}
