#include "FileFormatException.h"
#include <iostream>
FileFormatException::FileFormatException(const string& message) : message(message) {}

void FileFormatException::print() const
{
	cerr << message << endl;
}

