#include "SyntaxException.h"
#include <iostream>
SyntaxException::SyntaxException(const string& message) : message(message){}

void SyntaxException::print() const
{
	cerr << message << endl;
}

