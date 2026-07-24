#ifndef LEXER_H
#define LEXER_H
#include <string>
#include <vector>
using namespace std;

enum TypeToken {
	TOKEN_WORD,
	QUOTED_STRING,
	REDIRECTION_IN,
	REDIRECTION_OUT,
	REDIRECTION_APPEND,
	PIPE,
	DASH_QUOTED_STRING
};

struct Token {
	TypeToken type = TOKEN_WORD;
	string value;
};

class Lexer {
public:
	Lexer();

	//	Tokenizes reader output into seperate tokens
	vector<Token> tokenize(const string& line);

	//	Validation checks
	bool isValidCharacter(char character);
	bool isQuotationMark(char character);

	//	Maps the token into the adequate enum member
	Token setToken(string& word,bool isQuoted, bool hasDash);
	string tokenTypeToString(TypeToken type);

};

#endif