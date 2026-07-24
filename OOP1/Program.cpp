#include "ConsoleUtils.h"
#include "Program.h"
#include "LexicalException.h"
#include "SyntaxException.h"
#include "SemanticException.h"
#include "ICommand.h"
#include "FileFormatException.h"

Program::Program(istream& input, ostream& output) : input(input), output(output), prompt("$"), instance(nullptr)
{
	reader = new Reader(input);
	lexer = new Lexer();
	parser = new Parser(*this);
}

Program::~Program()
{
	delete reader;
	delete lexer;
	delete parser;
}

ifstream Program::openInputFile(Command* command)
{
	ifstream fin;
	if (!command->getInputFile().empty()) {
		fin.open(command->getInputFile());
		if (!fin.is_open())
			throw SemanticException("Error - file does not exist");
	}
	return fin;
}

ofstream Program::openOutputFile(Command* command){

	ofstream fout;
	if (!command->getOutputFile().empty()) {
		fout.open(command->getOutputFile(), command->isAppend() ? ios::app : ios::trunc);
	}
	return fout;
}

stringstream Program::consoleInput(Command* command)
{
	stringstream console;
	if (command->hasInput() && command->getArgument().empty() && command->getInputFile().empty()) {
		hideInput();
		string line;
		while (getline(cin, line))
			console << line << "\n";
		showInput();
	}
	string content = console.str();
	if (command->trailingNewLine()) {
		if (!content.empty() && content.back() == '\n')
			content.pop_back();
		console.str(content);
		console.clear();
	}

	return console;
}

Command* Program::setPipe(vector<Command*> commands)
{
	Command* cmd;
	if (parser->getPipe())
		cmd = commands.back();
	else
		cmd = commands.front();
	return cmd;
}

istream& Program::setInput(vector<Command*> commands)
{
	fin = openInputFile(commands.front());
	stream.str(commands.front()->getArgument());
	console = consoleInput(commands.front());
	cin.clear();
	stream.clear();

	istream& in = (!commands.front()->getArgument().empty() ? (istream&)stream
		: !commands.front()->getInputFile().empty() ? (istream&)fin
		: (istream&)console);

	return in;
}

ostream& Program::setOutput(Command* command)
{
	fout = openOutputFile(command);
	ostream& out = (!command->getOutputFile().empty() ? (ostream&)fout : output);

	return out;
}

void Program::executePipe(vector<Command*> commands, istream& in, ostream& out)
{
	stringstream pipeStream;
	commands.front()->execute(in, pipeStream);
	for (int i = 1; i < commands.size() - 1; i++) {

		stringstream nextStream;
		commands[i]->execute(pipeStream, nextStream);
		pipeStream.str(nextStream.str());
		pipeStream.clear();
	}
	commands.back()->execute(pipeStream, out);
}

void Program::run()
{
		try {
			string line = reader->getLine();
			reader->isEof();

			vector<Token> tokens = lexer->tokenize(line);
			vector<Command*> commands = parser->parse(tokens);
			
			Command* cmd = setPipe(commands);
			istream& in = setInput(commands);
			ostream& out = setOutput(cmd);
			
			if (parser->getPipe()) 
				executePipe(commands, in, out);
			else 
				cmd->execute(in, out);
			
			if (cmd->getOutputFile().empty() && cmd->hasOutput() && cmd->printsNewline())
				output << "\n";

			fout.close();
			fin.close();

			deleteCommands(commands);
			
		}
		catch (const LexicalException& e) {
			e.print();
		}
		catch (const SyntaxException& e) {
			e.print();
		}
		catch (const SemanticException& e) {
			e.print();
		}
//		catch (const FileFormatException& e) {
//			e.print();
//		}
}

void Program::runBatch()
{
	while (reader->hasLine())
		run();
}

void Program::deleteCommands(vector<Command*> commands)
{
	for (Command* c : commands)
		delete c;
}
