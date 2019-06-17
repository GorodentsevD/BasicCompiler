package ru.Gorodentsev.ast;

import java.io.*;
import java.util.ArrayList;
import java.util.Optional;

public class ArraysRepository {
    private static String tablePath = "src/main/resources/arrays.csv";

    /**  метод для чтения из .csv файла */
    /*
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

     */

    public static int getValueByIndex (String name, int index) throws IOException {

        Integer value = null;
        try (FileReader fr = new FileReader(tablePath)) {

            BufferedReader br = new BufferedReader(fr);

            String line;

            String tokenList[];
            while((line = br.readLine()) != null) {
                tokenList = line.split(";");

                if(tokenList[0].equals(name)) {
                    value = Integer.parseInt(tokenList[1 + index]);
                    System.out.println("value: " + value);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        if(value != null)
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

    public static void addVariable(String name, ArrayList<Integer> values) {
/*
        try (FileReader fr = new FileReader(tablePath)) {

            BufferedReader br = new BufferedReader(fr);

            String line;

            String tokenList[];
            while((line = br.readLine()) != null) {
                tokenList = line.split(";");
                //System.out.println("name: " + tokenList[0]);
                if(tokenList[0] == name) {
                    line.replaceAll(tokenList[1], Integer.parseInt(value));
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }


    */

        int length = values.size();
        StringBuilder stringValues = new StringBuilder();

        for (int value : values) {
            stringValues.append(value);
            stringValues.append(';');
        }


        try (FileWriter fw = new FileWriter(tablePath, true)) {
            fw.write( name + ';' + stringValues + "\n");

            fw.write('\n');
        }
        catch(IOException ex){
            System.out.println(ex.getMessage());
        }
    }

    public static void addCharVariable(String name, ArrayList<Character> values) {
/*
        try (FileReader fr = new FileReader(tablePath)) {

            BufferedReader br = new BufferedReader(fr);

            String line;

            String tokenList[];
            while((line = br.readLine()) != null) {
                tokenList = line.split(";");
                //System.out.println("name: " + tokenList[0]);
                if(tokenList[0] == name) {
                    line.replaceAll(tokenList[1], Integer.parseInt(value));
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }


    */

        int length = values.size();
        StringBuilder stringValues = new StringBuilder();

        for (int value : values) {
            stringValues.append(value);
            stringValues.append(';');
        }


        try (FileWriter fw = new FileWriter(tablePath, true)) {
            fw.write( name + ';' + stringValues + "\n");

            fw.write('\n');
        }
        catch(IOException ex){
            System.out.println(ex.getMessage());
        }
    }

}
