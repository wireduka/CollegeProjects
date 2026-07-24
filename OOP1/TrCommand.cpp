#include "TrCommand.h"
#include "SemanticException.h"
#include <iostream>
void TrCommand::validate(const vector<Token>& tokens){
	if (tokens.size() > 3)
		throw SemanticException("Error - too many arguments for tr command");

	if (hasInput() && !tokens.empty() && !inputFile.empty()) {
		throw SemanticException("Error - cannot use both argument and input redirection");
	}
	int dash = -1;
	for(int i = 0; i < tokens.size(); i++)
		if (tokens[i].type == DASH_QUOTED_STRING) {
			dash = i;
		}
	if (dash == -1)
		throw SemanticException("Error - tr requires -what argument");
	if (dash == 1) {
		if (inputFile.empty()) {
			if (tokens[0].type == QUOTED_STRING) {
				argument = tokens[0].value;
			}
			else if (tokens[0].type == TOKEN_WORD) {
//				checkFile(tokens[0].value);
				inputFile = tokens[0].value;
			}
		}
	}
	
	if (dash != 0 && dash != 1)
		throw SemanticException("Error - invalid tr command syntax");

	what = tokens[dash].value;
	if (dash + 1 < tokens.size() && tokens[dash + 1].type == QUOTED_STRING)
		with = tokens[dash + 1].value;
	if (dash + 1 < tokens.size() && tokens[dash + 1].type != QUOTED_STRING)
		throw SemanticException("Error - wrong -with syntax");
}

void TrCommand::execute(istream& in, ostream& out)
{
	string stream;
	char c;
	while(in.get(c))
		stream += c;
	size_t pos = 0;

	while ((pos = stream.find(what, pos)) != string::npos) {
		stream.replace(pos, what.size(), with);
		pos += with.size();
	}
	out << stream;
}
