#include "Lexer.h"
#include "LexicalException.h"
//	Defined constaint of 512 characters
#define MAX 512

Lexer::Lexer(){}
vector<Token> Lexer::tokenize(const string& line){
	vector <Token> tokens;
	vector <int> errorMarkers;
	string current;
	string nline = (line.size() > MAX) ? line.substr(0, MAX) : line;
	// main loop
	for (int i = 0; i < nline.size(); i++) {
		char c = nline[i];
		// quotation subprogram
		if (isQuotationMark(c)) {
			bool hasDash = (current == "-");
			if (!current.empty() && !hasDash)
				errorMarkers.push_back(i);
			i++; // skip opening " sign
			current.clear();
			while ( i < nline.size() && nline[i] != '"') {
				current.push_back(nline[i]);
				i++;
			}
			if (i >= nline.size()) {
				throw LexicalException("Error - unenclosed quotation mark");
			}
			i++; // skip closing " sign
			if (current.empty())
				throw LexicalException("Error - quoted string cannot be empty");
			if(hasDash)
				tokens.push_back(setToken(current, true, true));
			else
				tokens.push_back(setToken(current, true, false));
			current.clear();
			continue;
		}
		if (!isValidCharacter(nline[i])) {
			errorMarkers.push_back(i);
		}
		// checking possible redirection situations: >, <, >>
		if (nline[i] == '<') {
			if (!current.empty()) {
				tokens.push_back(setToken(current, false, false));
				current.clear();
			}
			current.push_back(nline[i]);
			tokens.push_back(setToken(current, false, false));
			current.clear();
			continue;
		}

		else if (i + 1 < nline.size() && nline[i] == '>' && nline[i + 1] == '>') {
			if (!current.empty()) {
				tokens.push_back(setToken(current, false, false));
				current.clear();
			}
			current.push_back(nline[i]);
			current.push_back(nline[i + 1]);
			tokens.push_back(setToken(current, false, false));
			current.clear();
			i++;
			continue;
		}

		else if (nline[i] == '>') {
			if (!current.empty()) {
				tokens.push_back(setToken(current, false, false));
				current.clear();
			}
			current.push_back(nline[i]);
			tokens.push_back(setToken(current, false, false));
			current.clear();
			continue;
		}
		
		
		if (isspace(nline[i])) {
			if (!current.empty()) {
				tokens.push_back(setToken(current, false, false));
				current.clear();
			}
		}
		else
			current.push_back(nline[i]);
	}

	if (!current.empty()) {
		tokens.push_back(setToken(current, false, false));
	}
	if (!errorMarkers.empty()) throw LexicalException(nline, errorMarkers);

	return tokens;
	

}

bool Lexer::isValidCharacter(char character)
{
	return isalnum(character)
		|| isspace(character)
		|| character == '-'
		|| character == '|'
		|| character == '<'
		|| character == '>'
		|| character == '.'
		|| character == '/'
		|| character == '_';
}
bool Lexer::isQuotationMark(char character)
{
	return character == '"';
}

Token Lexer::setToken(string& word, bool isQuoted, bool hasDash)
{
	Token temp;
	temp.value = word;
	if (hasDash == true)				temp.type = DASH_QUOTED_STRING;
	else if (isQuoted == true)			temp.type = QUOTED_STRING;
	else if (word == "|")				temp.type = PIPE;
	else if (word == "<")				temp.type = REDIRECTION_IN;
	else if (word == ">")				temp.type = REDIRECTION_OUT;
	else if (word == ">>")				temp.type = REDIRECTION_APPEND;
	else								temp.type = TOKEN_WORD;
	return temp;
}

string Lexer::tokenTypeToString(TypeToken type)
{
	switch (type) {
	case TypeToken::TOKEN_WORD:			return "WORD"; break;
	case TypeToken::PIPE:				return "PIPE"; break;
	case TypeToken::REDIRECTION_IN:		return "REDIRECTION_IN"; break;
	case TypeToken::REDIRECTION_OUT:	return "REDIRECTION_OUT"; break;
	case TypeToken::REDIRECTION_APPEND: return "REDIRECTION_APPEND"; break;
	case TypeToken::QUOTED_STRING:		return "QUOTED_STRING"; break;
	case TypeToken::DASH_QUOTED_STRING:	return "DASH_QUOTED_STRING"; break;
	default:							return "UNKNOWN"; break;
	}
	
}



