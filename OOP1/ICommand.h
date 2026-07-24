#ifndef ICOMMAND_H
#define ICOMMAND_H
#include <istream>
#include "Lexer.h"
using namespace std;

//	Base command class
//	-Ruleset-
//	Options must be defined in the constructor of the derived class by adding them to the existing options vector
class Command {
public:
	Command();
	virtual ~Command() = default;

	virtual void execute(istream& in, ostream& out) = 0;

	// Validates syntax and semantics of command, redefined in each command
	virtual void validate(const vector<Token>& tokens);

	// Default command arguments, must be redefined in the case of different semantics for each command
	virtual bool hasInput() const { return true; }
	virtual bool hasOutput() const { return true; }
	virtual bool hasOption() const { return false; }

	// Setters
	virtual void setInputFile(const string& filename);
	virtual void setOutputFile(const string& filename, bool isAppend);
	virtual void setOption(const string& optionName);

	// Standard checks used for adequate parsing
	virtual bool isAppend() const { return append; }

	// virtual void checkFile(const string& filename);
	virtual void checkOption();
	virtual void standardInput(const vector<Token>& tokens, bool ifConsole);
	virtual bool mustHaveOption() const { return true; }
	virtual bool trailingNewLine() const { return false; }
	virtual bool isOptionNum() const { return false; }
	virtual bool printsNewline() const { return true; }

	// Getters
	virtual string getInputFile() const { return inputFile; }
	virtual string getOutputFile() const { return outputFile; }
	virtual string getArgument() const { return argument; }
	


protected:

	int optionNumber;
	string argument;
	string option;
	vector <string> options;
	string inputFile;
	string outputFile;
	bool append;
};
#endif