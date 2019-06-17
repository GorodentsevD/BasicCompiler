package ru.Gorodentsev.ast;

import java.io.*;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

public final class VariableRepository {
    //private HashMap<String, StringExpression> variables = new HashMap<>();

    private static String tablePath = "src/main/resources/table.csv";

    public VariableRepository() {

        //cleanCSV();
    }



    /**  метод для чтения из .csv файла */
    public static String getValueByName (Optional<String> name) throws IOException {

        String value = "";
        try (FileReader fr = new FileReader(tablePath)) {

            BufferedReader br = new BufferedReader(fr);

            String line;

            String tokenList[];
            while((line = br.readLine()) != null) {
                tokenList = line.split(";");
                System.out.println("name: " + tokenList[0]);
                if(tokenList[0] == name.get()) {
                    value = tokenList[1];
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        if(value != "")
            return value;
        else
            throw new IOException("Wrong variable");
    }

    public static int getShiftByName (Optional<String> name) throws IOException {

        int value = -1;
        try (FileReader fr = new FileReader(tablePath)) {


            BufferedReader br = new BufferedReader(fr);

            String line;

            String tokenList[];
            while((line = br.readLine()) != null) {
                tokenList = line.split(";");

                if(tokenList[0].equals(name.get())) {
                    value = Integer.parseInt(tokenList[2]);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        if(value != -1)
            return value;
        else
            throw new IOException("Wrong variable");
    }

    /** метод для очистки .csv файла */
    public static void cleanCSV () {
        try (PrintWriter writ = new PrintWriter(tablePath)) {
            writ.print("");
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
    }

    public static void addVariable(String name, StringExpression value, int shift) throws IOException {

        Path path = Paths.get(tablePath);
        Boolean flagIsPresent = false;

        String tokenList[] = {};
        StringBuilder newLine = new StringBuilder();

        try {
            List<String> fileLines = new ArrayList<>(Files.readAllLines(path, Charset.defaultCharset()));
            for (int i = 0; i < fileLines.size(); i++) {
                tokenList = fileLines.get(i).split(";");
                if (tokenList[0].equals(name)) {
                    flagIsPresent = true;
                    //newLine.append(tokenList[0]).append(';').append(value).append(';').append(tokenList[2]);
                    //fileLines.set(i, newLine.toString());
                    break;
                }
            }
            Files.write(path, fileLines, Charset.defaultCharset());
        } catch (IOException ex) {
            System.out.println("FILE OPEN ERROR");
        }

        /*
        int flagPresent = 0;
        try (FileReader fr = new FileReader(tablePath)) {

            BufferedReader br = new BufferedReader(fr);

            String line;


            String tokenList[];
            while((line = br.readLine()) != null) {
                tokenList = line.split(";");
                //System.out.println("name: " + tokenList[0]);
                if(tokenList[0].equals(name)) {
                    line.replaceAll(tokenList[1], Integer.parseInt(value));
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

 */
            try (FileWriter fw = new FileWriter(tablePath, true)) {
                if (!flagIsPresent)
                    fw.write(name + ';' + value + ';' + shift + "\n");
                else
                    fw.write(name + ';' + value + ';' + tokenList[2]);
            } catch (IOException ex) {
                System.out.println(ex.getMessage());
            }
        }
}
