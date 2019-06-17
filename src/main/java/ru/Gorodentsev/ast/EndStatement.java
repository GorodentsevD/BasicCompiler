package ru.Gorodentsev.ast;

import ru.Gorodentsev.instructions.Instruction;
import ru.Gorodentsev.instructions.InstructionSequence;
import ru.Gorodentsev.instructions.Opcode;

public final class EndStatement extends Statement {

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return 0;
    }

    @Override
    public String toString() {
        return "END";
    }

    @Override
    public void compile(InstructionSequence seq) {
        seq.append(new Instruction(Opcode.HLT));
    }

}
