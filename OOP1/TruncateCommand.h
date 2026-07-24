#ifndef TRUNCATECOMMAND_H
#define TRUNCATECOMMAND_H
#include "FileCommand.h"
class TruncateCommand : public FileCommand {
public:
	void execute(istream& in, ostream& out) override;
};
#endif