#ifndef READER_H
#define READER_H

#include <string>
#include <istream>
using namespace std;

class Reader {
public:
	Reader(istream& input);
	~Reader() {}

	string getLine();
	bool isEof(); 
	bool hasLine();
private:
	istream& input;
};

#endif
