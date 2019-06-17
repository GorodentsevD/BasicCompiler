package ru.Gorodentsev.ast;

public enum ComparisonOperator {

    EQUALS("="),

    NOT_EQUALS("<>"),

    MORE(">"),

    MORE_OR_EQUALS(">="),

    LESS("<"),

    LESS_OR_EQUALS("<=");

    private final String string;

    private ComparisonOperator(String string) {
        this.string = string;
    }

    public String toString() {
        return string;
    }
}
