#ifndef LEXICALEXCEPTION_H
#define LEXICALEXCEPTION_H
#include "IException.h"
#include <vector>

class LexicalException : public Exception {
public:
	// Seperate constructor in the case of an not allowed character
	LexicalException(const string& line, const vector<int>& errorPosition);
	// Default constructor
	LexicalException(const string& message);

	void print() const override;
private:

	string line;
	vector<int> errorPositions;
	string message;
};

#endif