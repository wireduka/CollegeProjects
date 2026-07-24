#ifndef EXCEPTION_H
#define EXCEPTION_H
#include <string>
using namespace std;

//	Base exception class
class Exception : public exception {
public:

	Exception() = default;
	virtual ~Exception() = default;
	virtual void print() const = 0;

};

#endif