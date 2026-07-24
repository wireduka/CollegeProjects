#ifndef PROMPTCOMMAND_H
#define PROMPTCOMMAND_H
#include "ICommand.h"
class Program;

class PromptCommand : public Command {
public:
	PromptCommand(Program& program);
	void validate(const vector<Token>& tokens) override;
	void execute(istream& in, ostream& out) override;

	bool hasOutput() const override { return false; }
private:
	Program& program;
};
#endif