package ru.Gorodentsev.ast;

import ru.Gorodentsev.instructions.InstructionSequence;

public abstract class StringExpression {

    public abstract void compile(InstructionSequence seq);

}
