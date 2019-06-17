package ru.Gorodentsev.ast;

import ru.Gorodentsev.instructions.Instruction;
import ru.Gorodentsev.instructions.InstructionSequence;
import ru.Gorodentsev.instructions.Opcode;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public final class IfStatement extends Statement {

    private final ComparisonOperator operator;
    private final StringExpression leftExpression, rightExpression;
    private Statement statement;

    private List<Statement> statementList = new ArrayList<>();

    public IfStatement(ComparisonOperator operator, StringExpression leftExpression,
                       StringExpression rightExpression, Statement statement) {
        this.operator = operator;
        this.leftExpression = leftExpression;
        this.rightExpression = rightExpression;
        this.statement = statement;
    }

    public IfStatement(ComparisonOperator operator, StringExpression leftExpression,
                       StringExpression rightExpression, List statementList) {
        this.operator = operator;
        this.leftExpression = leftExpression;
        this.rightExpression = rightExpression;
        this.statementList = statementList;
    }

    public ComparisonOperator getOperator() {
        return operator;
    }

    public StringExpression getLeftExpression() {
        return leftExpression;
    }

    public StringExpression getRightExpression() {
        return rightExpression;
    }

    public Statement getStatement() {
        return statement;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        IfStatement that = (IfStatement) o;

        if (!leftExpression.equals(that.leftExpression)) return false;
        if (operator != that.operator) return false;
        if (!rightExpression.equals(that.rightExpression)) return false;
        if (!statement.equals(that.statement)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return Objects.hash(operator, leftExpression, rightExpression, statement);
    }

    @Override
    public String toString() {
        return "IF " + leftExpression + " " + operator + " " + rightExpression + " THEN " + statementList;
    }

    @Override
    public void compile(InstructionSequence seq) {
        leftExpression.compile(seq);
        rightExpression.compile(seq);

        String thenLabel = seq.createGeneratedLabel();
        String endLabel = seq.createGeneratedLabel();

        Opcode opcode;
        switch (operator) {
            case EQUALS:
                opcode = Opcode.JMPEQ;
                break;
            case NOT_EQUALS:
                opcode = Opcode.JMPNE;
                break;
            case LESS_OR_EQUALS:
                opcode = Opcode.JMPLTE;
                break;
            case LESS:
                opcode = Opcode.JMPLT;
                break;
            case MORE:
                opcode = Opcode.JMPGT;
                break;
            case MORE_OR_EQUALS:
                opcode = Opcode.JMPGTE;
                break;
            default:
                throw new AssertionError();
        }

        seq.append(
                new Instruction(opcode, thenLabel),
                new Instruction(Opcode.JMP, endLabel),
                new Instruction(Opcode.LABEL, thenLabel)
        );

        for(Statement statement : statementList) {
            statement.compile(seq);
        }

        //statement.compile(seq);
        //System.out.println("------__________-------IFstatement: " + statement);

        seq.append(new Instruction(Opcode.LABEL, endLabel));
    }
}