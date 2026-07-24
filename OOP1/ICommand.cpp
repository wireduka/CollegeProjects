#include "ICommand.h"
#include "SemanticException.h"
#include "FileFormatException.h"
#include <array>
#include <stdexcept>
using namespace std;

Command::Command(): append(false),optionNumber(-1){}
// Checks if command has both an argument and input redirection
void Command::validate(const vector<Token>& tokens)
{
	if (hasInput() && !tokens.empty() && !inputFile.empty()) {
		throw SemanticException("Error - cannot use both argument and input redirection");
	}
	if (!tokens.empty() && tokens[0].type == DASH_QUOTED_STRING)
		throw SemanticException("Error - command does not support this argument type");

	if (this->hasOption())
		checkOption();
}

void Command::setInputFile(const string& filename){
	if (!inputFile.empty()) {
		throw SemanticException("Error - multiple input directions");
	}
	// checkFile(filename);
	inputFile = filename;
}
void Command::setOutputFile(const string& filename, bool isAppend){
	if (!outputFile.empty()) {
		throw SemanticException("Error - multiple output directions");
	}
	outputFile = filename;
	if (isAppend)
		append = true;


}

void Command::setOption(const string& optionName)
{
	if (hasOption()) {
		option = optionName;
	}
	else
		throw SemanticException("Error - command does not support options");
}

//void Command::checkFile(const string& filename)
//{
//	bool valid = false;
//	const int numOfExtensions = 4;
//	const array<string, numOfExtensions> allowedExtensions = { ".txt",".csv",".log",".out"};
//	for (const string& extension : allowedExtensions) {
//		if (filename.size() > extension.size() && filename.substr(filename.size() - extension.size()) == extension)
//			valid = true;
//	}
//	if (!valid)
//		throw FileFormatException("Error - invalid file format");
//	
//}

void Command::checkOption()
{
	if (mustHaveOption() && option.empty())
		throw SemanticException("Error - command must have an option");
	bool optFound = false;
	for (string opt : options) {
		if (isOptionNum() && option.substr(0, opt.size()) == opt) {
			try {
				optionNumber = stoi(option.substr(opt.size()));
				optFound = true;
			}
			catch (const std::invalid_argument&){
				throw SemanticException("Error - option must be a number");
			}
		}
		else if (option == opt) optFound = true;
	}
	if (optFound == false && !option.empty())
		throw SemanticException("Error - command does not support such option");
}

void Command::standardInput(const vector<Token>& tokens, bool ifConsole)
{
	if (inputFile.empty()) {
		if (ifConsole && tokens.empty()) {
			return;
		}
		else if (tokens[0].type == QUOTED_STRING) {
			argument = tokens[0].value;
		}
		else if (tokens[0].type == TOKEN_WORD) {
//			checkFile(tokens[0].value);
			inputFile = tokens[0].value;
		}
	}
}


