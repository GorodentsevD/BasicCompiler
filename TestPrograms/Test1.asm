[extern exit]
[extern printf]
[extern scanf]
[section .code]
[global main]
main:
  push rbp
  mov rbp, rsp
  sub rsp, 216
line_10:
  push 0x1
  pop rax
  mov [rbp - 0], rax
line_20:
  push 0x2
  pop rax
  mov [rbp - 8], rax
line_30:
  push 0x3
  pop rax
  mov [rbp - 16], rax
line_40:
  push generated_8
  pop rsi
  mov rdi, str_fmt
  mov al, 0
  call printf
  mov rax, [rbp - 16]
  push rax
  pop rsi
  mov rdi, num_fmt
  mov al, 0
  call printf
  push generated_9
  pop rsi
  mov rdi, str_fmt
  mov al, 0
  call printf
line_50:
  mov rax, [rbp - 16]
  push rax
  push 0x3
  pop rbx
  pop rax
  cmp rax, rbx
  je generated_0
  jmp generated_1
generated_0:
  push generated_10
  pop rsi
  mov rdi, str_fmt
  mov al, 0
  call printf
  push generated_11
  pop rsi
  mov rdi, str_fmt
  mov al, 0
  call printf
generated_1:
line_60:
  push generated_12
  pop rsi
  mov rdi, str_fmt
  mov al, 0
  call printf
  mov rax, [rbp - 16]
  push rax
  pop rsi
  mov rdi, num_fmt
  mov al, 0
  call printf
  push generated_13
  pop rsi
  mov rdi, str_fmt
  mov al, 0
  call printf
line_70:
  push generated_14
  pop rsi
  mov rdi, str_fmt
  mov al, 0
  call printf
  mov rax, [rbp - 0]
  push rax
  pop rsi
  mov rdi, num_fmt
  mov al, 0
  call printf
  push generated_15
  pop rsi
  mov rdi, str_fmt
  mov al, 0
  call printf
line_80:
  mov rax, [rbp - 8]
  push rax
  pop rax
  mov [rbp - 0], rax
line_90:
  push generated_16
  pop rsi
  mov rdi, str_fmt
  mov al, 0
  call printf
  mov rax, [rbp - 0]
  push rax
  pop rsi
  mov rdi, num_fmt
  mov al, 0
  call printf
  push generated_17
  pop rsi
  mov rdi, str_fmt
  mov al, 0
  call printf
line_100:
  mov rax, [rbp - 16]
  push rax
  push 0x0
  pop rbx
  pop rax
  cmp rax, rbx
  jg generated_2
  jmp generated_3
generated_2:
  mov rax, [rbp - 16]
  push rax
  push 0x1
  pop rbx
  pop rax
  sub rax, rbx
  push rax
  pop rax
  mov [rbp - 16], rax
  push generated_18
  pop rsi
  mov rdi, str_fmt
  mov al, 0
  call printf
  mov rax, [rbp - 16]
  push rax
  pop rsi
  mov rdi, num_fmt
  mov al, 0
  call printf
  push generated_19
  pop rsi
  mov rdi, str_fmt
  mov al, 0
  call printf
  mov rax, [rbp - 16]
  push rax
  push 0x0
  pop rbx
  pop rax
  cmp rax, rbx
  je generated_4
  jmp generated_5
generated_4:
  push generated_20
  pop rsi
  mov rdi, str_fmt
  mov al, 0
  call printf
  push generated_21
  pop rsi
  mov rdi, str_fmt
  mov al, 0
  call printf
generated_5:
  jmp line_100
generated_3:
line_110:
  push generated_22
  pop rsi
  mov rdi, str_fmt
  mov al, 0
  call printf
  push generated_23
  pop rsi
  mov rdi, str_fmt
  mov al, 0
  call printf
line_120:
  mov rbx, _ARR
line_130:
  push generated_24
  pop rsi
  mov rdi, str_fmt
  mov al, 0
  call printf
  mov rbx, _ARR
  mov rax, [rbx + 0]
  push rax
  pop rsi
  mov rdi, char_fmt
  mov al, 0
  call printf
  push generated_25
  pop rsi
  mov rdi, str_fmt
  mov al, 0
  call printf
line_140:
  push generated_26
  pop rsi
  mov rdi, str_fmt
  mov al, 0
  call printf
  mov rbx, _ARR
  mov rax, [rbx + 4]
  push rax
  pop rsi
  mov rdi, char_fmt
  mov al, 0
  call printf
  push generated_27
  pop rsi
  mov rdi, str_fmt
  mov al, 0
  call printf
line_150:
  push generated_28
  pop rsi
  mov rdi, str_fmt
  mov al, 0
  call printf
  mov rbx, _ARR
  mov rax, [rbx + 8]
  push rax
  pop rsi
  mov rdi, char_fmt
  mov al, 0
  call printf
  push generated_29
  pop rsi
  mov rdi, str_fmt
  mov al, 0
  call printf
line_160:
  push generated_30
  pop rsi
  mov rdi, str_fmt
  mov al, 0
  call printf
  mov rbx, _ARR
  mov rax, [rbx + 12]
  push rax
  pop rsi
  mov rdi, char_fmt
  mov al, 0
  call printf
  push generated_31
  pop rsi
  mov rdi, str_fmt
  mov al, 0
  call printf
line_170:
  push 0x0
  pop rax
  mov [rbp - 40], rax
line_180:
  mov rax, [rbp - 40]
  push rax
  push 0xa
  pop rbx
  pop rax
  cmp rax, rbx
  jl generated_6
  jmp generated_7
generated_6:
  mov rax, [rbp - 40]
  push rax
  mov rax, [rbp - 8]
  push rax
  pop rax
  pop rbx
  add rax, rbx
  push rax
  pop rax
  mov [rbp - 40], rax
  push generated_32
  pop rsi
  mov rdi, str_fmt
  mov al, 0
  call printf
  mov rax, [rbp - 40]
  push rax
  pop rsi
  mov rdi, num_fmt
  mov al, 0
  call printf
  push generated_33
  pop rsi
  mov rdi, str_fmt
  mov al, 0
  call printf
  jmp line_180
generated_7:
line_190:
  mov rbx, $CHAR
line_200:
  push generated_34
  pop rsi
  mov rdi, str_fmt
  mov al, 0
  call printf
  mov rbx, $CHAR
  mov rax, [rbx + 0]
  push rax
  pop rsi
  mov rdi, char_fmt
  mov al, 0
  call printf
  push generated_35
  pop rsi
  mov rdi, str_fmt
  mov al, 0
  call printf
line_210:
  push generated_36
  pop rsi
  mov rdi, str_fmt
  mov al, 0
  call printf
  mov rbx, $CHAR
  mov rax, [rbx + 3]
  push rax
  pop rsi
  mov rdi, char_fmt
  mov al, 0
  call printf
  push generated_37
  pop rsi
  mov rdi, str_fmt
  mov al, 0
  call printf
  mov rax, 0
  mov rsp, rbp
  pop rbp
  ret
[section .rodata]
generated_27:
  db "", 10, "", 0
generated_26:
  db "_ARR[1] = ", 0
generated_29:
  db "", 10, "", 0
generated_28:
  db "_ARR[2] = ", 0
generated_23:
  db "", 10, "", 0
generated_22:
  db "arrays operations", 0
generated_25:
  db "", 10, "", 0
generated_24:
  db "_ARR[0] = ", 0
generated_8:
  db "C = ", 0
generated_9:
  db "", 10, "", 0
num_fmt:
  db "%d", 0
generated_21:
  db "", 10, "", 0
generated_20:
  db "DONE!!!", 0
generated_16:
  db "A = ", 0
generated_15:
  db "", 10, "", 0
generated_37:
  db "", 10, "", 0
generated_18:
  db "C = ", 0
generated_17:
  db "", 10, "", 0
generated_12:
  db "C = ", 0
generated_34:
  db "$CHAR[0] = ", 0
generated_11:
  db "", 10, "", 0
generated_33:
  db "", 10, "", 0
generated_14:
  db "A = ", 0
generated_36:
  db "$CHAR[3] = ", 0
generated_13:
  db "", 10, "", 0
generated_35:
  db "", 10, "", 0
generated_19:
  db "", 10, "", 0
str_fmt:
  db "%s", 0
char_fmt:
  db "%c", 0
generated_30:
  db "_ARR[3] = ", 0
generated_10:
  db "INSIDE IF", 0
generated_32:
  db "L = ", 0
generated_31:
  db "", 10, "", 0
_ARR:
  dd 0,1,2,3,

$CHAR:
  db 'a','b','c','d',

