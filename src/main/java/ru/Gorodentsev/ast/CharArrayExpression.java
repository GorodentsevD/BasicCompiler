package ru.Gorodentsev.ast;

import ru.Gorodentsev.instructions.Instruction;
import ru.Gorodentsev.instructions.InstructionSequence;
import ru.Gorodentsev.instructions.Opcode;

import java.io.IOException;

public final class CharArrayExpression extends Expression {
    private final String name;
    private int index;

    //private int shift = 0;

    public CharArrayExpression(String name) {
        this.name = name;
    }

    public CharArrayExpression(String name, int index) {

        this.name = name;
        this.index = index;
    }


    public String getName() {
        return name;
    }
    public int getIndex() { return index; }
    public int getValue(String name, int index) throws IOException {
        return ArraysRepository.getValueByIndex(name, index);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        CharArrayExpression that = (CharArrayExpression) o;

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

        seq.append(new Instruction(Opcode.LOAD_CHAR_INDEX, name, index));
        //seq.append(new Instruction(Opcode.LOAD, name, shift));
    }

}
