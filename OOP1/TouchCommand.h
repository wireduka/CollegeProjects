#ifndef TOUCHCOMMAND_H
#define TOUCHCOMMAND_H
#include "FileCommand.h"
class TouchCommand : public FileCommand {
public:
	void execute(istream& in, ostream& out) override;
};
#endif