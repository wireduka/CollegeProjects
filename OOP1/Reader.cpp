#include "Reader.h"

Reader::Reader(istream& input) : input(input){}

string Reader::getLine()
{
	string line;
	getline(input, line);
	return line;
}

bool Reader::isEof()
{
	if (input.eof()) {
		input.clear();
		return true;
	}
	return false;
}
bool Reader::hasLine() {
	return input.peek() != EOF;
}