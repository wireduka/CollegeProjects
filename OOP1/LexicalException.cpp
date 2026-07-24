#include "LexicalException.h"
#include <string>
#include <iostream>
using namespace std;

LexicalException::LexicalException(const string& line, const vector<int>&  errorPositions) : line(line), errorPositions(errorPositions) {}
LexicalException::LexicalException(const string& message) : message(message){}

void LexicalException::print() const
{
	if (!errorPositions.empty()) {
		cout << endl;
		cerr << "Error - unexpected characters:" << endl;
		cout << line << endl;
		string marker(line.size(), ' ');
		for (int pos : errorPositions) {
			marker[pos] = '^';
		}
		cout << marker << endl;
	}
	else {
		cerr << message << endl;
	}


}