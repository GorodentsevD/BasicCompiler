package ru.Gorodentsev.tokens;

import java.io.IOException;
import java.util.Objects;
import java.util.Optional;

public final class Token {

    private final TokenType type;
    private Optional<String> value;
    //private final int col, row;

    public Token(TokenType type) {
        this.type = type;
        this.value = Optional.empty();
        System.out.println("\n" + type + " ");
    }

    public Token(TokenType type, String value) {
        this.type = type;
        this.value = Optional.of(value);
        System.out.println("\n" + type + " " + value);
    }

    public TokenType getType() {
        return type;
    }

    public Optional<String> getValue() throws IOException {
        if(value.isPresent()) {
            return value;
        }
        else {
            throw new IOException("Unexpected Variable");
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        Token token = (Token) o;

        if (type != token.type) {
            return false;
        }

        if (!value.equals(token.value)) {
            return false;
        }

        return true;
    }

    @Override
    public int hashCode() {
        return Objects.hash(type, value);
    }

    @Override
    public String toString() {
        return Token.class.getSimpleName() + " [type=" + type + ", value=" + value + "]";
    }
}
