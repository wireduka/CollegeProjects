# Command Line Interpreter (CLI)

This project was developed as part of the **Object-Oriented Programming 1** course at the Faculty of Electrical Engineering, University of Belgrade.

The goal of the project was to implement a simplified command-line interpreter (CLI) in **C++**, inspired by Unix-like shells. While considerably simpler than a real operating system shell, it supports many of the concepts found in modern command interpreters, including command parsing, input/output redirection, pipes, batch execution, and file manipulation.

The project follows the official specification provided by the course staff. Since the assignment specification is the intellectual property of the faculty, only a general overview of the required functionality is provided here.

## Features

The interpreter supports the following built-in commands:

- `echo`
- `prompt`
- `time`
- `date`
- `touch`
- `truncate`
- `rm`
- `wc`
- `tr`
- `head`
- `batch`

Additionally, the interpreter implements:

- command parsing
- quoted string arguments
- input and output redirection (`<`, `>`, `>>`)
- command pipelines (`|`)
- batch execution from text files
- lexical, syntax and runtime error handling
- file-based and console input/output

## Design

The project was designed using object-oriented principles, with responsibilities separated into independent components responsible for:

- lexical analysis
- command parsing
- command execution
- input/output stream abstraction
- file management
- error reporting

This structure makes it relatively straightforward to extend the interpreter with additional commands or new language features.

## Notes

This repository contains my own implementation of the project. The official assignment specification is not included, as it belongs to the course staff.

The interpreter was developed and tested on Windows using standard C++ libraries.
