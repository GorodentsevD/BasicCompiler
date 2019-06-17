package ru.Gorodentsev.tokens;

import java.io.Closeable;
import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;

import static java.lang.Thread.sleep;

public final class Tokenizer implements Closeable {

    private static boolean isWhitespace(int ch) {
        return Character.isWhitespace(ch);
    }

    public static boolean isDigit(int ch) {
        return ch >= '0' && ch <= '9';
    }

    public static boolean isAlpha(int ch) {
        return ch >= 'A' && ch <= 'Z';
    }

    public static boolean isChar(int ch) {
        return ch >= 'a' && ch <= 'z';
    }

    private final Reader reader;

    public Tokenizer(String str) {
        this.reader = new StringReader(str);
    }

    public Tokenizer(Reader reader) {
        this.reader = reader;
    }

    private int peek() throws IOException {
        reader.mark(1);
        try {
            return reader.read();
        } finally {
            reader.reset();
        }
    }

    public Token nextToken() throws IOException {
        while (true)
        {
            int ch = reader.read();
            //System.out.format("ch = %c", ch);

            if (ch == -1) { return new Token(TokenType.EOF); }
            if (ch == '\n') { return new Token(TokenType.LF); }

            if (ch == ',') { return new Token(TokenType.COMMA); }
            if (ch == '"') { return nextStringToken(); }

            if (ch == '(') { return new Token(TokenType.LPAREN); }
            if (ch == ')') { return new Token(TokenType.RPAREN); }

            if (ch == '[') {return new Token(TokenType.LBRACKET); }
            if (ch == ']') {return new Token(TokenType.RBRACKET); }

            if (ch == '{') {return new Token(TokenType.LBRACE); }
            if (ch == '}') {return new Token(TokenType.RBRACE); }


            if (ch == '+') { return new Token(TokenType.PLUS); }
            if (ch == '-') { return new Token(TokenType.MINUS); }
            if (ch == '*') { return new Token(TokenType.MULT); }
            if (ch == '/') { return new Token(TokenType.DIV); }

            if (ch == '=') { return new Token(TokenType.EQUALS); }
            if (ch == '>' || ch == '<') { return nextComparisonOperatorToken(ch); }

            if (ch == '_') { return nextArrayToken(ch); }
            if (ch == '$') { return nextCArrayToken(ch); }

            if (isAlpha(ch) && !isAlpha(peek())) {
                return new Token(TokenType.VAR, new String(new char[]{(char) ch}));
            }

            if (isChar(ch)) {
                StringBuilder c = new StringBuilder();
                c.append((char) ch);
                return new Token(TokenType.CHAR, c.toString());
            }

            if (isAlpha(ch)) { return nextKeywordToken(ch); }
            if (isDigit(ch)) { return nextNumberToken(ch); }

            if(ch == ';') { return new Token(TokenType.SEMICOLON); }

            if (!isWhitespace(ch)) { throw new IOException("Unexpected character: " + ch); }
        }
    }

    private Token nextArrayToken(int first) throws IOException {
        StringBuilder buf = new StringBuilder();
        buf.append((char) first);
        for (;;) {
            int ch = peek();

            if (!isAlpha(ch))
                break;

            reader.skip(1);
            buf.append((char) ch);
        }
        return new Token(TokenType.ARRAY, buf.toString());

    }

    private Token nextCArrayToken(int first) throws IOException {
        StringBuilder buf = new StringBuilder();
        buf.append((char) first);
        for (;;) {
            int ch = peek();

            if (!isAlpha(ch))
                break;

            reader.skip(1);
            buf.append((char) ch);
        }
        return new Token(TokenType.CARRAY, buf.toString());

    }

    private Token nextComparisonOperatorToken(int first) throws IOException {
        int second = peek();

        if (first == '>') {
            if (second == '<') {
                reader.skip(1);
                return new Token(TokenType.NOT_EQUALS);
            } else if (second == '=') {
                reader.skip(1);
                return new Token(TokenType.MORE_OR_EQUALS);
            } else {
                return new Token(TokenType.MORE);
            }
        } else {
            assert first == '<';

            if (second == '>') {
                reader.skip(1);
                return new Token(TokenType.NOT_EQUALS);
            } else if (second == '=') {
                reader.skip(1);
                return new Token(TokenType.LESS_OR_EQUALS);
            } else {
                return new Token(TokenType.LESS);
            }
        }
    }

    private Token nextStringToken() throws IOException {
        StringBuilder buf = new StringBuilder();
        for (;;) {
            int ch = reader.read();
            if (ch == -1)
                throw new IOException("Unexpected EOF within string");
            else if (ch == '"')
                break;

            buf.append((char) ch);
        }
        return new Token(TokenType.STRING, buf.toString());
    }

    private Token nextKeywordToken(int first) throws IOException {
        StringBuilder buf = new StringBuilder();
        buf.append((char) first);
        for (;;) {
            int ch = peek();
            if (!isAlpha(ch))
                break;

            reader.skip(1);
            buf.append((char) ch);
        }
        return new Token(TokenType.KEYWORD, buf.toString());
    }

    private Token nextNumberToken(int first) throws IOException {
        StringBuilder buf = new StringBuilder();
        buf.append((char) first);
        for (;;) {
            int ch = peek();
            if (!isDigit(ch))
                break;

            reader.skip(1);
            buf.append((char) ch);
        }
        return new Token(TokenType.NUMBER, buf.toString());
    }

    @Override
    public void close() throws IOException {
        reader.close();
    }

}
