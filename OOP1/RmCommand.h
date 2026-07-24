#ifndef RMCOMMAND_H
#define RMCOMMAND_H
#include "FileCommand.h"
class RmCommand : public FileCommand {
public:
	void execute(istream& in, ostream& out) override;
};
#endif