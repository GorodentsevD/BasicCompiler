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
  mov rbx, int_array
line_20:
    mov rax, [rdi - 0]
    push rax
    pop rsi
    mov rdi, num_fmt
    mov al, 0
    call printf
    push generated_0
    pop rsi
    mov rdi, str_fmt
    mov al, 0
    call printf
    mov rax, 0
    mov rsp, rbp
    pop rbp
    ret
[section .rodata]
num_fmt:
  db "%d", 0
generated_0:
  db "", 10, "", 0
str_fmt:
  db "%s", 0
int_array:
  db 1,2,3,4