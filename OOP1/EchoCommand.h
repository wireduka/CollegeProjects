#ifndef ECHOCOMMAND_H
#define ECHOCOMMAND_H
#include "ICommand.h"
class EchoCommand : public Command {
public:

	void validate(const vector<Token>& tokens) override;
	void execute(istream& in, ostream& out) override;

	bool trailingNewLine() const override { return true; }
	
};
#endif