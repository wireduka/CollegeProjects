#include "FileCommand.h"
#include "SemanticException.h"
void FileCommand::validate(const vector<Token>& tokens)
{
	if (tokens.empty())
		throw SemanticException("Error - must enter a file name");
	if (tokens.size() > 1)
		throw SemanticException("Error - command does not support multiple arguments");
	if (tokens[0].type == TOKEN_WORD) {
//		checkFile(tokens[0].value);
		argument = tokens[0].value;
	}
	else
		throw SemanticException("Error - argument must be a file");
}
