#!/bin/bash

#создание .asm файла
gradle run --args='TestPrograms/Test1.tb TestPrograms/Test1.asm'

#создание объектного файла
nasm -felf64 -o TestPrograms/Test1.o TestPrograms/Test1.asm

#создание исполняемого файла
gcc -o TestPrograms/Test1 TestPrograms/Test1.o -m64 -no-pie
