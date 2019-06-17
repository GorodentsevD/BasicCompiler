package ru.Gorodentsev;

import ru.Gorodentsev.ast.ArraysRepository;
import ru.Gorodentsev.ast.VariableRepository;
import ru.Gorodentsev.codegen.CodeGenerator;
import ru.Gorodentsev.parser.Parser;
import ru.Gorodentsev.tokens.Tokenizer;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public final class Main {

    public static void main(String[] args) throws IOException {

        Path inputPath = Paths.get(args[0]);
        Path outputPath = Paths.get(args[1]);

        //Path inputPath = src/main/java/Resources/Test1.db;

        VariableRepository.cleanCSV();
        ArraysRepository.cleanCSV();
        try (Tokenizer tokenizer = new Tokenizer(Files.newBufferedReader(inputPath, StandardCharsets.UTF_8))) {
            try (Parser parser = new Parser(tokenizer)) {
                try (CodeGenerator generator = new CodeGenerator(Files.newBufferedWriter(outputPath, StandardCharsets.UTF_8))) {
                    generator.generate(parser.parse().compile());
                }
            }
        }
    }

}
