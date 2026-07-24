#ifndef FILEFORMATEXCEPTION_H
#define FILEFORMATEXCEPTION_H
#include "IException.h"

class FileFormatException : Exception {
public:
	FileFormatException(const string& message);
	void print() const override;
private:
	string message;
};
#endif