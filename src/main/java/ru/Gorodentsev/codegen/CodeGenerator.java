package ru.Gorodentsev.codegen;

import ru.Gorodentsev.ast.ArrayExpression;
import ru.Gorodentsev.ast.VariableRepository;
import ru.Gorodentsev.instructions.Instruction;
import ru.Gorodentsev.instructions.InstructionSequence;

import java.io.Closeable;
import java.io.IOException;
import java.io.Writer;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public final class CodeGenerator implements Closeable {

    private final Writer writer;

    public CodeGenerator(Writer writer) {
        this.writer = writer;
    }


    public void generate(InstructionSequence seq) throws IOException {
        writer.append("[extern exit]\n");
        writer.append("[extern printf]\n");
        writer.append("[extern scanf]\n");
        writer.append("[section .code]\n");
        writer.append("[global main]\n");
        writer.append("main:\n");
        writer.append("  push rbp\n");
        writer.append("  mov rbp, rsp\n");
        writer.append("  sub rsp, " + (8 * 27) + "\n");

        Map<String, String> strings = new HashMap<>();
        ArrayList<String> arrayStrings = new ArrayList<>();
        for (Instruction instruction : seq.getInstructions()) {

            switch (instruction.getOpcode()) {
                case LABEL:
                    writer.append(instruction.getStringOperand().get() + ":\n");
                    break;

                case PUSHI:
                    writer.append("  push 0x" + Integer.toHexString(instruction.getIntegerOperand().get()) + "\n");
                    break;

                case PUSHS:
                    String label = seq.createGeneratedLabel();
                    strings.put(label, instruction.getStringOperand().get());
                    writer.append("  push " + label + "\n");
                    break;

                case LOAD:
                    Optional<String> loadName = instruction.getStringOperand();
                    int loadShift = VariableRepository.getShiftByName(loadName);

                    writer.append("  mov rax, [rbp - " + loadShift * 8 + "]\n");
                    writer.append("  push rax\n");
                    break;

                case STORE:
                    Optional<String> storeName = instruction.getStringOperand();
                    int storeShift = VariableRepository.getShiftByName(storeName);

                    writer.append("  pop rax\n");
                    writer.append("  mov [rbp - " + storeShift * 8 + "], rax\n");
                    break;

                case STORE_ARRAY:
                    String arrayLabel = instruction.getStringOperand().get();
                    arrayStrings.add(arrayLabel + ":\n  dd " + instruction.valuesToString() + "\n");

                    writer.append("  mov rbx, " + arrayLabel + "\n");
                    break;

                case STORE_CARRAY:
                    String carrayLabel = instruction.getStringOperand().get();
                    arrayStrings.add(carrayLabel + ":\n  db " + instruction.charValuesToString() + "\n");

                    writer.append("  mov rbx, " + carrayLabel + "\n");
                    break;

                case LOAD_INDEX:
                    String loadArrayName = instruction.getStringOperand().get();
                    int index = instruction.getShiftOperand();

                    writer.append("  mov rbx, " + loadArrayName + "\n");
                    writer.append("  mov rax, [rbx + " + index * 4 + "]\n");
                    writer.append("  push rax\n");
                    break;

                case LOAD_CHAR_INDEX:
                    String loadCharArrayName = instruction.getStringOperand().get();
                    int charIndex = instruction.getShiftOperand();

                    writer.append("  mov rbx, " + loadCharArrayName + "\n");
                    writer.append("  mov rax, [rbx + " + charIndex * 1 + "]\n");
                    writer.append("  push rax\n");
                    break;

                case ADD:
                    writer.append("  pop rax\n");
                    writer.append("  pop rbx\n");
                    writer.append("  add rax, rbx\n");
                    writer.append("  push rax\n");
                    break;

                case SUB:
                    writer.append("  pop rbx\n");
                    writer.append("  pop rax\n");
                    writer.append("  sub rax, rbx\n");
                    writer.append("  push rax\n");
                    break;

                case MUL:
                    writer.append("  pop rax\n");
                    writer.append("  pop rbx\n");
                    writer.append("  imul rax, rbx\n");
                    writer.append("  push rax\n");
                    break;

                case DIV:
                    writer.append("  xor rdx, rdx\n");
                    writer.append("  pop rax\n");
                    writer.append("  pop rbx\n");
                    writer.append("  idiv rbx\n");
                    writer.append("  push rax\n");
                    break;

                case CALL:
                    writer.append("  call " + instruction.getStringOperand().get() + "\n");
                    break;

                case RET:
                    writer.append("  ret\n");
                    break;

                case JMP:
                    writer.append("  jmp " + instruction.getStringOperand().get() + "\n");
                    break;

                case JMPGT:
                    writer.append("  pop rbx\n");
                    writer.append("  pop rax\n");
                    writer.append("  cmp rax, rbx\n");
                    writer.append("  jg " + instruction.getStringOperand().get() + "\n");
                    break;

                case JMPGTE:
                    writer.append("  pop rbx\n");
                    writer.append("  pop rax\n");
                    writer.append("  cmp rax, rbx\n");
                    writer.append("  jge " + instruction.getStringOperand().get() + "\n");
                    break;

                case JMPLT:
                    writer.append("  pop rbx\n");
                    writer.append("  pop rax\n");
                    writer.append("  cmp rax, rbx\n");
                    writer.append("  jl " + instruction.getStringOperand().get() + "\n");
                    break;

                case JMPLTE:
                    writer.append("  pop rbx\n");
                    writer.append("  pop rax\n");
                    writer.append("  cmp rax, rbx\n");
                    writer.append("  jle " + instruction.getStringOperand().get() + "\n");
                    break;

                case JMPEQ:
                    writer.append("  pop rbx\n");
                    writer.append("  pop rax\n");
                    writer.append("  cmp rax, rbx\n");
                    writer.append("  je " + instruction.getStringOperand().get() + "\n");
                    break;

                case JMPNE:
                    writer.append("  pop rbx\n");
                    writer.append("  pop rax\n");
                    writer.append("  cmp rax, rbx\n");
                    writer.append("  jne " + instruction.getStringOperand().get() + "\n");
                    break;

                case HLT:
                    writer.append("  mov rdi, 0\n");
                    writer.append("  call exit\n");
                    break;

                case IN:
                    Optional<String> inName = instruction.getStringOperand();
                    int inShift = VariableRepository.getShiftByName(inName);

                    strings.put("num_fmt", "%d");
                    writer.append("  lea rsi, [rbp - " + inShift * 8 + "]\n");
                    writer.append("  push rsi\n");
                    writer.append("  mov rdi, num_fmt\n");
                    writer.append("  mov al, 0\n");
                    writer.append("  call scanf\n");
                    writer.append("  xor rax, rax\n");
                    writer.append("  mov eax, [rbp - " + inShift * 8 + "]\n");
                    writer.append("  push rax\n");
                    break;

                case OUTS:
                    strings.put("str_fmt", "%s");
                    writer.append("  pop rsi\n");
                    writer.append("  mov rdi, str_fmt\n");
                    writer.append("  mov al, 0\n");
                    writer.append("  call printf\n");
                    break;

                case OUTI:
                    strings.put("num_fmt", "%d");
                    writer.append("  pop rsi\n");
                    writer.append("  mov rdi, num_fmt\n");
                    writer.append("  mov al, 0\n");
                    writer.append("  call printf\n");
                    break;

                case OUTC:
                    strings.put("char_fmt", "%c");
                    writer.append("  pop rsi\n");
                    writer.append("  mov rdi, char_fmt\n");
                    writer.append("  mov al, 0\n");
                    writer.append("  call printf\n");
                    break;


            }
        }

        writer.append("  mov rax, 0\n");
        writer.append("  mov rsp, rbp\n");
        writer.append("  pop rbp\n");
        writer.append("  ret\n");

        writer.append("[section .rodata]\n");
        for (Map.Entry<String, String> string : strings.entrySet()) {
            writer.append(string.getKey() + ":\n");
            writer.append("  db \"" + escape(string.getValue()) + "\", 0\n");
        }
        for (String str : arrayStrings) {
            writer.append(str);
        }
    }

    private String escape(String value) {
        value = value.replace("\n", "\", 10, \"");
        return value;
    }

    private int varIndex(Instruction instruction) {
        //return (instruction.getStringOperand().get().charAt(0) - 'A') * 8;
        return (instruction.getShiftOperand() * 8);
    }

    @Override
    public void close() throws IOException {
        writer.close();
    }

}
