#define NOMINMAX
#define WIN32_LEAN_AND_MEAN
#include <windows.h>

// File used for console input hiding

inline void hideInput() {
    HANDLE h = GetStdHandle(STD_INPUT_HANDLE);
    DWORD mode;
    GetConsoleMode(h, &mode);
    SetConsoleMode(h, mode & ~ENABLE_ECHO_INPUT);
}

inline void showInput() {
    HANDLE h = GetStdHandle(STD_INPUT_HANDLE);
    DWORD mode;
    GetConsoleMode(h, &mode);
    SetConsoleMode(h, mode | ENABLE_ECHO_INPUT);
}