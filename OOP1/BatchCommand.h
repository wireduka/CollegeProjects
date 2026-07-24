#ifndef BATCHCOMMAND_H
#define BATCHCOMMAND_H
#include "ICommand.h"
#include "Program.h"
class BatchCommand : public Command {
public:
	BatchCommand(Program& program);

	void validate(const vector<Token>& tokens) override;
	void execute(istream& in, ostream& out) override;

	virtual bool printsNewline() const { return false; }

private:
	Program& program;

};
#endif