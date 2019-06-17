package ru.Gorodentsev.ast;

import ru.Gorodentsev.instructions.Instruction;
import ru.Gorodentsev.instructions.InstructionSequence;
import ru.Gorodentsev.instructions.Opcode;

public final class VariableExpression extends StringExpression {

    private final String name;
    private int value = 0;
    private int shift = 0;

    public VariableExpression(String name) {
        this.name = name;
    }

    public VariableExpression(String name, int value) {

        this.name = name;
        this.value = value;
    }

    public VariableExpression(String name, int value, int shift) {

        this.name = name;
        this.value = value;
        this.shift = shift;
    }


    public String getName() {
        return name;
    }
    public int getValue() {return value; }
    public int getShift() { return shift; }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        VariableExpression that = (VariableExpression) o;

        if (!name.equals(that.name)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return name.hashCode();
    }

    @Override
    public String toString() {
        return name;
    }

    @Override
    public void compile(InstructionSequence seq) {
        seq.append(new Instruction(Opcode.LOAD, name, shift));
    }

}

