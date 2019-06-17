package ru.Gorodentsev.ast;

import ru.Gorodentsev.instructions.Instruction;
import ru.Gorodentsev.instructions.InstructionSequence;
import ru.Gorodentsev.instructions.Opcode;

import java.util.Objects;

public final class UnaryExpression extends StringExpression {

    private final UnaryOperator operator;
    private final StringExpression expression;

    public UnaryExpression(UnaryOperator operator, StringExpression expression) {
        this.operator = operator;
        this.expression = expression;
    }

    public UnaryOperator getOperator() {
        return operator;
    }

    public StringExpression getExpression() {
        return expression;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        UnaryExpression that = (UnaryExpression) o;

        if (!expression.equals(that.expression)) return false;
        if (operator != that.operator) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return Objects.hash(operator, expression);
    }

    @Override
    public String toString() {
        return "(" + operator + expression + ")";
    }

    @Override
    public void compile(InstructionSequence seq) {
        switch (operator) {
            case PLUS:
                expression.compile(seq);
                break;
            case MINUS:
                seq.append(new Instruction(Opcode.PUSHI, 0));
                expression.compile(seq);
                seq.append(new Instruction(Opcode.SUB));
                break;
            default:
                throw new AssertionError();
        }

    }

}
