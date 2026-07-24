#ifndef FILECOMMAND_H
#define FILECOMMAND_H
#include "ICommand.h"

class FileCommand : public Command {
public:

	void validate(const vector<Token>& tokens) override;

	bool hasInput() const override { return false; }
	bool hasOutput() const override { return false; }
};
#endif