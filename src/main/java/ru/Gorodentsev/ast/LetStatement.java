package ru.Gorodentsev.ast;

import ru.Gorodentsev.instructions.Instruction;
import ru.Gorodentsev.instructions.InstructionSequence;
import ru.Gorodentsev.instructions.Opcode;

import java.io.IOException;
import java.util.Objects;

public final class LetStatement extends Statement {

    private final String name;
    private final StringExpression value;

    private VariableRepository repository;
    private static int shift = 0;

    public LetStatement(String name, StringExpression value) throws IOException {
        this.name = name;
        this.value = value;

        repository.addVariable(name, value, shift++);
        //System.out.println("____----________VALUE:" + value + " " + shift);
    }

    public String getName() {
        return name;
    }

    public StringExpression getValue() {
        return value;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        LetStatement that = (LetStatement) o;

        if (!name.equals(that.name)) return false;
        if (!value.equals(that.value)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, value);
    }

    @Override
    public String toString() {
        return "LET " + name + " = " + value;
    }

    @Override
    public void compile(InstructionSequence seq) {
        value.compile(seq);
        seq.append(new Instruction(Opcode.STORE, name, shift));
    }

}
