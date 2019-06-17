package ru.Gorodentsev.instructions;

public enum Opcode {
    PUSHS,
    PUSHI,
    LOAD,
    STORE,
    ADD,
    MUL,
    SUB,
    DIV,
    CALL,
    RET,
    JMP,
    JMPGT,
    JMPGTE,
    JMPLT,
    JMPLTE,
    JMPNE,
    JMPEQ,
    HLT,
    IN,

    OUTS,
    OUTI,
    OUTC,

    LABEL,

    STORE_ARRAY,
    LOAD_INDEX,
    LOAD_CHAR_INDEX,

    STORE_CARRAY
}
