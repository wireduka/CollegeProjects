#include "SemanticException.h"
#include <iostream>
SemanticException::SemanticException(const string& message) : message(message){}

void SemanticException::print() const
{
	cerr << message << endl;
}
