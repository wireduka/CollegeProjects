#ifndef TIMECOMMAND_H
#define TIMECOMMAND_H
#include "ICommand.h"

class TimeCommand : public Command {
public:
	void validate(const vector<Token>& tokens) override;
	void execute(istream& in, ostream& out) override;

	bool hasInput() const override { return false; }
};
#endif