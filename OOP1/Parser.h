#ifndef PARSER_H
#define PARSER_H
#include <vector>
#include "ICommand.h"
#include "Lexer.h"

class Program;

class Parser {
public:
	Parser(Program& program);

	~Parser() = default;
	//	Parses the command
	vector <Command*> parse(const vector <Token>& tokens);

	//	Splits token list into seperate lists by using pipe sign as seperator
	vector <vector<Token>> splitByPipe(const vector <Token>& tokens);

	//	Calls the default command constructor
	Command* createCommand(const string& name);

	//	Checking for possible redirection cases
	vector <Token> setRedirections(vector<Token> group,Command* command);

	//	Handles possible pipe exceptions
	void isValidPipe(vector <Command*> commands);

	bool getPipe() { return isPipe; }

private:
	bool isPipe;
	Program& program;
};
#endif