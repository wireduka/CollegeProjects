#ifndef TRCOMMAND_H
#define TRCOMMAND_H
#include "ICommand.h"
class TrCommand : public Command {
public:

	void validate(const vector<Token>& tokens) override;
	void execute(istream& in, ostream& out) override;

	bool trailingNewLine() const override { return true; }

private:
	string what;
	string with;
};
#endif
