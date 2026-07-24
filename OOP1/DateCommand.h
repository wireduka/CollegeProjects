#ifndef DATECOMMAND_H
#define DATECOMMAND_H
#include "ICommand.h"

class DateCommand : public Command {
public:
	void validate(const vector<Token>& tokens) override;
	void execute(istream& in, ostream& out) override;

	bool hasInput() const override { return false; }
};
#endif