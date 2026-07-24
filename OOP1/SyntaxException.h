#ifndef SYNTAXEXCEPTION_H
#define SYNTAXEXCEPTION_H
#include "IException.h"

class SyntaxException : Exception{
public:
	SyntaxException(const string& message);
	void print() const override;
private:
	string message;
};
#endif