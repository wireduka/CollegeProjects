#ifndef WCCOMMAND_H
#define WCCOMMAND_H
#include "ICommand.h"
class WcCommand : public Command {
public:
	WcCommand();

	void validate(const vector<Token>& tokens) override;
	void execute(istream& in, ostream& out) override;

	virtual bool hasOption() const { return true; }

};
#endif