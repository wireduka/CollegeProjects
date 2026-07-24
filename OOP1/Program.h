#ifndef PROGRAM_H
#define PROGRAM_H
#include <iostream>
#include <fstream>
#include <sstream>
#include <string.h>
#include "Reader.h"
#include "Lexer.h"
#include "Parser.h"
using namespace std;

class Program {
public:
	Program(istream& input, ostream& output);
	~Program();

	// Opens files
	ifstream openInputFile(Command* command);
	ofstream openOutputFile(Command* command);

	// Console input formatting
	stringstream consoleInput(Command* command);

	// Sets command in the case of pipe existance
	Command* setPipe(vector<Command*> commands);

	// Sets input and output
	istream& setInput(vector<Command*> commands);
	ostream& setOutput(Command* command);

	// Executes pipe
	void executePipe(vector<Command*> commands,istream& in, ostream& out);

	// Prompt getters and setters
	void setPrompt(const string& newPrompt) { prompt = newPrompt; }
	string getPrompt() { return prompt; }

	// Instance getters and setters
	Program* getInstance(){return instance;}
	void setInstance(Program* p) { instance = p; }

	// Run main program and run batch
	virtual void run();
	virtual void runBatch();

	// Command deletion
	void deleteCommands(vector <Command*> commands);


private:
	Program* instance;
	istream& input;
	ostream& output;
	Reader* reader;
	Lexer* lexer;
	Parser* parser;
	string prompt;

	ifstream fin;
	stringstream stream;
	stringstream console;
	ofstream fout;
};
#endif

