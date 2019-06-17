package ru.Gorodentsev.ast;

import ru.Gorodentsev.instructions.Instruction;
import ru.Gorodentsev.instructions.InstructionSequence;
import ru.Gorodentsev.instructions.Opcode;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Objects;

public class CharDimStatement extends Statement {

    private final String name;
    private ArrayList<Character> values = new ArrayList<>();
    private Integer length;

    private ArraysRepository repository;
    private static int shift = 0;

    public CharDimStatement(String name, ArrayList<Character> values, Integer length) throws IOException {
        this.name = name;
        this.values = values;
        this.length = length;
        this.shift++;

        if (length != values.size()) {
            throw new IOException("Wrong array length");
        }

        repository.addCharVariable(name, values);
    }

    public String getName() {
        return name;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        CharDimStatement that = (CharDimStatement) o;

        if (!name.equals(that.name)) return false;
        if (!values.equals(that.values)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, values);
    }

    @Override
    public String toString() {
        return "CDIM " + name + " = " + values;
    }

    @Override
    public void compile(InstructionSequence seq) {
        //value.compile(seq);
        seq.append(new Instruction(Opcode.STORE_CARRAY, name, shift, values));
    }
}
