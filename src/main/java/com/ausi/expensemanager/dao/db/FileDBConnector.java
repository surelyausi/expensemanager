package com.ausi.expensemanager.dao.db;

import java.io.*;

/**
 * Created by Schurl on 31.07.2016.
 */
public class FileDBConnector {
    private static final String EXPENSES_FILE_PATH = "expenses";
    private static final String CATEGORIES_FILE_PATH = "categories";

    private static final FileDBConnector instance = new FileDBConnector();

    public void writeExpenses(String jsonString){
        write(jsonString, EXPENSES_FILE_PATH);
    }

    public String readExpenses(){
       return read(EXPENSES_FILE_PATH);
    }

    public void writeCategories(String jsonString){
        write(jsonString, CATEGORIES_FILE_PATH);
    }

    public String readCategories(){
        return read(CATEGORIES_FILE_PATH);
    }

    private void write(String stringToWrite, String path){
        try {
            File targetFile = new File(path);
            if(!targetFile.exists()){
                targetFile.createNewFile();
            }

            PrintWriter printWriter = new PrintWriter(path);
            printWriter.print(stringToWrite);
            printWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private String read(String path){
        try {
            BufferedReader bufferedReader = new BufferedReader(new FileReader(path));
            StringBuffer stringBuffer = new StringBuffer();
            String line;

            while((line = bufferedReader.readLine()) != null){
                stringBuffer.append(line);
            }

            return stringBuffer.toString();

        } catch (FileNotFoundException e) {
            System.err.println("No Category File Found.");
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

    public static FileDBConnector getInstance() {
        return instance;
    }
}
