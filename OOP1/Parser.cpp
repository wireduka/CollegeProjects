#include "Parser.h"
#include "SyntaxException.h"
#include "SemanticException.h"
#include "Commands.h"


Parser::Parser(Program& program) : isPipe(false), program(program){}

vector<Command*> Parser::parse(const vector<Token>& tokens)
{
	isPipe = false;
	if (tokens.empty()) throw SyntaxException("Error - empty command line");
    vector<vector<Token>> groups = splitByPipe(tokens);
	vector<Command*> commands;
	//	Creates command using the first element of each group, and sets the option flag using the second element of each group, aswell as sets redirections using the last 2 elements
	//	Afterward, deletes the group elements that have already fulfilled their purpose, to make parsing the command simpler
	for (vector<Token> group : groups) {
		Command* cmd = createCommand(group[0].value);
		group.erase(group.begin());
		if (!group.empty() && group[0].value.front() == '-' && group[0].type == TOKEN_WORD) {
			cmd->setOption(group[0].value);
			group.erase(group.begin());
		}

		group = setRedirections(group, cmd);
		cmd->validate(group);
		commands.push_back(cmd);
	}
	if(isPipe)
		isValidPipe(commands);
    return commands;
}

vector<vector<Token>> Parser::splitByPipe(const vector<Token>& tokens)
{
	vector<vector<Token>> groups;
	vector<Token> currentGroup;

	if (tokens.front().type == PIPE)
		throw SyntaxException("Error - pipe cannot be at start of command line");
	if (tokens.back().type == PIPE)
		throw SyntaxException("Error - pipe cannot be at end of command line");

	for (Token token : tokens) {
		if (token.type == PIPE) {
			if (currentGroup.empty()) {
				throw SyntaxException("Error - unexpected pipe");
			}
			groups.push_back(currentGroup);
			isPipe = true;
			currentGroup.clear();
		}
		else {
			currentGroup.push_back(token);
		}
	}
	if (!currentGroup.empty()) {
		groups.push_back(currentGroup);
	}
	
   return groups;
}

Command* Parser::createCommand(const string& name)
{
	if (name == "echo") return new EchoCommand();
	if (name == "prompt") return new PromptCommand(program);
	if (name == "time") return new TimeCommand();
	if (name == "date") return new DateCommand();
	if (name == "touch") return new TouchCommand();
	if (name == "truncate") return new TruncateCommand();
	if (name == "rm") return new RmCommand();
	if (name == "wc") return new WcCommand();
	if (name == "tr") return new TrCommand();
	if (name == "head") return new HeadCommand();
	if (name == "batch") return new BatchCommand(program);
	else throw SyntaxException("Error - unknown command");
	
}

vector <Token> Parser::setRedirections(vector<Token> group, Command* command)
{
	if (!group.empty() && group.back().type == REDIRECTION_IN ||
		!group.empty() && group.back().type == REDIRECTION_OUT ||
		!group.empty() && group.back().type == REDIRECTION_APPEND)
		throw SemanticException("Error - redirection must have a stream");
	while (group.size() >= 2) {
		TypeToken type = group[group.size() - 2].type;
		string filename = group.back().value;
		if (type != REDIRECTION_IN && type != REDIRECTION_OUT && type != REDIRECTION_APPEND)
			break;
		if (type == REDIRECTION_IN)
			if (command->hasInput()) {
				command->setInputFile(filename);
			}
			else
				throw SemanticException("Error - command does not support an input stream");
		else if (type == REDIRECTION_OUT)
			if (command->hasOutput()) {
				command->setOutputFile(filename, false);
			}
			else
				throw SemanticException("Error - command does not support an output stream");
		else if (type == REDIRECTION_APPEND)
			if (command->hasOutput()) {
				command->setOutputFile(filename, true);
			}
			else
				throw SemanticException("Error - command does not support an output stream");

		group.pop_back();
		group.pop_back();
	}
	for (const Token& token : group) {
		if (token.type == REDIRECTION_IN || token.type == REDIRECTION_OUT || token.type == REDIRECTION_APPEND)
			throw SemanticException("Error -  redirection must be at end of command");
	}


	return group;
}

void Parser::isValidPipe(vector<Command*> commands)
{
	for (int i = 1; i < commands.size(); i++)
		if (!commands[i]->getArgument().empty())
			throw SemanticException("Error - piped command cannot have an argument");
	if (!commands[0]->getOutputFile().empty())
		throw SyntaxException("Error - pipe entry point cannot have an output stream");
	else if (!commands.back()->getInputFile().empty())
		throw SyntaxException("Error - pipe exit point cannot have an input stream");
	else{
		for (int i = 1; i < commands.size() - 1; i++)
			if (!commands[i]->getInputFile().empty() || !commands[i]->getOutputFile().empty())
				throw SyntaxException("Error - commands connected in pipe cannot have an input or output stream");
	}
	if (!commands.front()->hasOutput())
		throw SemanticException("Error - command cannot be pipe input");
	if (!commands.back()->hasInput())
		throw SemanticException("Error - command cannot be pipe output");
	for (int i = 1; i < commands.size() - 1; i++)
		if (!commands[i]->hasInput() || !commands[i]->hasOutput())
			throw SemanticException("Error - command cannot be in pipe");
}


