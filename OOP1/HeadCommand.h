#ifndef HEADCOMMAND_H
#define HEADCOMMAND_H
#include "ICommand.h"
class HeadCommand : public Command {
public:
	HeadCommand();

	void validate(const vector<Token>& tokens) override;
	void execute(istream& in, ostream& out) override;

	bool hasOption() const override { return true; }
	bool isOptionNum() const override { return true; }

};
#endif