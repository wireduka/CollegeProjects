#ifndef SEMANTICEXCEPTION_H
#define SEMANTICEXCEPTION_H
#include "IException.h"

class SemanticException : Exception {
public:
	SemanticException(const string& message);
	void print() const override;
private:
	string message;
};
#endif