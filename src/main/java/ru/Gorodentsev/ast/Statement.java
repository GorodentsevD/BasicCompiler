package ru.Gorodentsev.ast;

import ru.Gorodentsev.instructions.InstructionSequence;

public abstract class Statement {

    public abstract void compile(InstructionSequence seq);

}
