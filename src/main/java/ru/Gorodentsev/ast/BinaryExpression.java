package ru.Gorodentsev.ast;


import ru.Gorodentsev.instructions.Instruction;
import ru.Gorodentsev.instructions.InstructionSequence;
import ru.Gorodentsev.instructions.Opcode;

import java.util.Objects;

public final class BinaryExpression extends StringExpression {

    private final BinaryOperator operator;
    private final StringExpression leftExpression, rightExpression;

    public BinaryExpression(BinaryOperator operator, StringExpression leftExpression, StringExpression rightExpression) {
        this.operator = operator;
        this.leftExpression = leftExpression;
        this.rightExpression = rightExpression;
    }

    public BinaryOperator getOperator() {
        return operator;
    }

    public StringExpression getLeftExpression() {
        return leftExpression;
    }

    public StringExpression getRightExpression() {
        return rightExpression;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        BinaryExpression that = (BinaryExpression) o;

        if (!leftExpression.equals(that.leftExpression)) return false;
        if (operator != that.operator) return false;
        if (!rightExpression.equals(that.rightExpression)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return Objects.hash(operator, leftExpression, rightExpression);
    }

    @Override
    public String toString() {
        return "(" + leftExpression + " " + operator + " " + rightExpression + ")";
    }

    @Override
    public void compile(InstructionSequence seq) {
        leftExpression.compile(seq);
        rightExpression.compile(seq);
        switch (operator) {
            case PLUS:
                seq.append(new Instruction(Opcode.ADD));
                break;
            case MINUS:
                seq.append(new Instruction(Opcode.SUB));
                break;
            case MULT:
                seq.append(new Instruction(Opcode.MUL));
                break;
            case DIV:
                seq.append(new Instruction(Opcode.DIV));
                break;
            default:
                throw new AssertionError();
        }
    }

}
