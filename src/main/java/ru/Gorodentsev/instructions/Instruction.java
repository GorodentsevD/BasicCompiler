package ru.Gorodentsev.instructions;

import java.util.ArrayList;
import java.util.Objects;
import java.util.Optional;

public final class Instruction {

    private final Opcode opcode;
    private final Optional<String> stringOperand;
    private final Optional<Integer> integerOperand;
    private int shift;
    private int index;

    private ArrayList<Integer> values;
    private ArrayList<Character> cvalues;

    public Instruction(Opcode opcode) {
        this.opcode = opcode;
        this.stringOperand = Optional.empty();
        this.integerOperand = Optional.empty();
    }

    public Instruction(Opcode opcode, String operand) {
        this.opcode = opcode;
        this.stringOperand = Optional.of(operand);
        this.integerOperand = Optional.empty();
    }

    public Instruction(Opcode opcode, String operand, int shift) {
        this.opcode = opcode;
        this.stringOperand = Optional.of(operand);
        this.integerOperand = Optional.empty();
        this.shift = shift;
    }

    public Instruction(Opcode opcode, int operand) {
        this.opcode = opcode;
        this.stringOperand = Optional.empty();
        this.integerOperand = Optional.of(operand);
    }

    public Instruction(Opcode opcode, String operand, ArrayList<Integer> values, int shift) {
        this.opcode = opcode;
        this.stringOperand = Optional.of(operand);
        this.shift = shift;
        this.integerOperand = Optional.empty();

        this.values = values;
    }

    public Instruction(Opcode opcode, String operand, int shift, ArrayList<Character> cvalues) {
        this.opcode = opcode;
        this.stringOperand = Optional.of(operand);
        this.shift = shift + 1 - 1;
        this.integerOperand = Optional.empty();

        this.cvalues = cvalues;
    }

    public Opcode getOpcode() {
        return opcode;
    }

    public Optional<String> getStringOperand() {
        return stringOperand;
    }

    public int getShiftOperand() {
        return shift;
    }


    public Optional<Integer> getIntegerOperand() {
        return integerOperand;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Instruction that = (Instruction) o;

        if (!integerOperand.equals(that.integerOperand)) return false;
        if (opcode != that.opcode) return false;
        if (!stringOperand.equals(that.stringOperand)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return Objects.hash(opcode, stringOperand, integerOperand);
    }

    public String valuesToString() {
        StringBuilder val = new StringBuilder();
        for(Integer v : values) {
            val.append(v).append(',');
        }
        val.append("\n");

        return val.toString();
    }

    public String charValuesToString() {
        StringBuilder val = new StringBuilder();
        for(Character v : cvalues) {
            val.append("'").append(v).append("'").append(",");
        }
        val.append("\n");

        return val.toString();
    }

    @Override
    public String toString() {
        if (stringOperand.isPresent())
            return opcode + " " + stringOperand.get();
        else if (integerOperand.isPresent())
            return opcode + " " + integerOperand.get();
        else
            return opcode.toString();
    }

}
