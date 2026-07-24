#include "BatchCommand.h"
#include "SemanticException.h"
#include "Program.h"

BatchCommand::BatchCommand(Program& program) : Command(), program(program)
{}

void BatchCommand::validate(const vector<Token>& tokens)
{
	Command::validate(tokens);
	if (tokens.size() > 1)
		throw SemanticException("Error - command does not support multiple arguments");
	if (!tokens.empty()) {
		if (tokens[0].type == TOKEN_WORD) {
//			checkFile(tokens[0].value);
			argument = tokens[0].value;
		}
		else {
			throw SemanticException("Error - argument must be a filename");
		}
	}
	if (tokens.empty())
		throw SemanticException("Error - argument must be a filename");
	
}

void BatchCommand::execute(istream& in, ostream& out)
{
	if (!argument.empty()) {
		ifstream file(argument);
		if (!file.is_open())
			throw SemanticException("Error - file does not exist");
		Program batchProgram(file,out);
		batchProgram.setInstance(program.getInstance());
		batchProgram.runBatch();
	}
	else {
		Program batchProgram(in,out);
		batchProgram.runBatch();
	}
}
