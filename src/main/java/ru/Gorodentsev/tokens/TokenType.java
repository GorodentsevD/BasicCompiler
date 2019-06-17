package ru.Gorodentsev.tokens;

public enum TokenType {
    EOF,
    LF,

    VAR,
    KEYWORD,
    ARRAY,
    CARRAY,

    NUMBER,
    STRING,
    CHAR,

    PLUS,
    MINUS,
    MULT,
    DIV,

    LPAREN,
    RPAREN,

    LBRACKET,
    RBRACKET,

    LBRACE,
    RBRACE,

    EQUALS,
    NOT_EQUALS,

    MORE,
    MORE_OR_EQUALS,
    LESS,
    LESS_OR_EQUALS,

    COMMA,
    SEMICOLON,
}
