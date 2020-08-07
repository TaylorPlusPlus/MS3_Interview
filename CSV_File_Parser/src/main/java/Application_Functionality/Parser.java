package Application_Functionality;

import com.opencsv.CSVWriter;

import javax.swing.*;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.StringTokenizer;

public class Parser {

    // Properties
    private String token, fileName, returnString, returnString2, sql;
    private StringTokenizer tok;
    private Scanner scan;
    private int sizeOfLine, failureCount = 0, lineCount = 0, successCount = 0;
    private File log, badCsv, database;
    private CSVWriter writer;
    private ArrayList<String> list = new ArrayList<String>();
    private ArrayList<String> tempList = new ArrayList<String>();


    // Constructor
    public Parser(){

    }

    //methods
    public void parseFile(File file) throws IOException {

        scan = new Scanner(file);

        //Getting filename without extension for future use
        tok = new StringTokenizer(file.getName() , ".", false);
        fileName = tok.nextToken();

        //Checking for db, log, and bad csv files with this file name, if they exist they are deleted
        //This allows for the program to be re-runnable
        log = new File( fileName + ".log");
        if(log.delete()){
            System.out.println("Log Deleted");
        }

        badCsv = new File( fileName + "-bad.csv");
        if(badCsv.delete()){
            System.out.println("bad.csv file deleted");
        }

        database = new File( fileName + ".db");
        if(database.delete()){
            System.out.println("database file deleted");
        }

        //checking to make sure the file isn't empty, if it isn't, the first line will be the line with headers
        // to be used as database column names
        if(scan.hasNextLine()) {
            token = scan.nextLine();
            tok = new StringTokenizer(token, ",", false);
            sizeOfLine = tok.countTokens();

            if(sizeOfLine < 10){
                // file must have 10 columns
                JOptionPane.showMessageDialog(null, "The file must have 10 columns!\n" +
                        "Choose a file with 10 columns too continue");
            }else{  //continue
                try{

                    // Creating the database
                    Class.forName("org.sqlite.JDBC");


                    Connection con = DriverManager.getConnection("jdbc:sqlite:" + fileName +".db");
                    Statement statement = con.createStatement();


                    // Creating the table
                    statement.execute("Create Table fileTable(" +
                            tok.nextToken() + " varchar(2550)," +
                            tok.nextToken() + " varchar(2550)," +
                            tok.nextToken() + " varchar(2550)," +
                            tok.nextToken() + " varchar(2550)," +
                            tok.nextToken() + " varchar(2550)," +
                            tok.nextToken() + " varchar(2550)," +
                            tok.nextToken() + " varchar(2550)," +
                            tok.nextToken() + " varchar(2550)," +
                            tok.nextToken() + " varchar(2550)," +
                            tok.nextToken() + " varchar(2550) );"
                            );

                    // The following code continues to parse the file line by line either inserting it  into the
                    // database or adding it to the bad.csv file

                    while(scan.hasNextLine()){

                        lineCount++;
                        System.out.println(lineCount);

                        token = scan.nextLine();


                        //List is for holding the String at each "index" of the csv file line
                        list.clear();
                        commaRemover(token);

                        //If the list is less then 10, the list should be sent to the bad.csv file
                       if(list.size() < 10) {


                            // failure counter needs incrementation
                            failureCount++;

                            //check if the file has been created, if not, create it
                            if(writer == null){
                                writer = new CSVWriter(new FileWriter(fileName +
                                        "-bad.csv"));
                            }

                            // getting the bad line into csv format
                           String[] badLine = new String[list.size()];

                            for(int i = 0; i < badLine.length; i ++){
                                badLine[i] = list.get(i);
                            }

                            // writing the bad line to the file
                            writer.writeNext(badLine);
                            writer.flush();

                        }else{// the line has all the content and needs to be added to the database

                            //increment the successful entry counter
                            successCount++;


                            try {

                                sql = "INSERT INTO fileTable VALUES ( '" + list.get(0) + "' , '" +
                                        list.get(1) + "', '" +
                                        list.get(2) + "' , '" +
                                        list.get(3) + "' , '" +
                                        list.get(4) + "' , '" +
                                        list.get(5) + "' , '" +
                                        list.get(6) + "' , '" +
                                        list.get(7) + "' , '" +
                                        list.get(8) + "', '" +
                                        list.get(9) + "');";

                               statement.execute(sql);
                            }catch(SQLException e){

                             //making sure the ' symbol isn't causing the sql error
                                for(int i = 0; i < list.size(); i ++){
                                    tempList.add(tokenChecker(list.get(i)));
                                }

                               sql = "INSERT INTO fileTable VALUES ( '" + tempList.get(0) + "' , '" +
                                        tempList.get(1) + "', '" +
                                        tempList.get(2) + "' , '" +
                                        tempList.get(3) + "' , '" +
                                        tempList.get(4) + "' , '" +
                                        tempList.get(5) + "' , '" +
                                        tempList.get(6) + "' , '" +
                                        tempList.get(7) + "' , '" +
                                        tempList.get(8) + "', '" +
                                        tempList.get(9) + "'); ";

                                statement.execute(sql);
                            }


                       }

                    }
                } catch (ClassNotFoundException | SQLException | IOException e) {
                    e.printStackTrace();
                    System.out.println("Error on Line " + lineCount
                    + " \n sql: " + sql);
                }
            }

        }

        //Writing Log File
        log = new File( fileName + ".log");
        System.out.println(log.getName());
        if(log.createNewFile()){
            System.out.println("Log Created");
            BufferedWriter writer = new BufferedWriter( new FileWriter(log));
            writer.write(lineCount + " records received.\n" +
                    successCount + " records successful.\n" +
                    failureCount + " records failed.");
            writer.close();
        }else{
            System.out.println("Log with same name already exists");
        }
    }

   
    
    public void commaRemover(String line){

        returnString = "";

        boolean foundEndOfQuote = false;
        tok = new StringTokenizer(line, "," , false);

        while(tok.hasMoreTokens()) {

            token = tok.nextToken();

            //each string with a comma, starts with a quotation mark, if the string doesn't have one, add it to the list
            if (!token.substring(0, 1).equals("\"")) {
                list.add(token);
            } else {

                //removes leading quote
                returnString += token.substring(1, token.length() -1 );

                //finding the end of the quote
                while (!foundEndOfQuote) {

                    token = tok.nextToken();
                    if (token.substring(token.length() - 1).equals("\"")) {
                        foundEndOfQuote = true;

                    }
                }
                //removed ending quotation mark
                returnString += "," + token.substring(0, token.length() - 2);
                list.add(returnString);
            }
        }

    }

    //This method checks for invalid characters
    public String tokenChecker(String token){
        returnString2 = "";

        for(int i = 0; i < token.length(); i ++){

            if(token.substring(i, i + 1).equals("'")){
                returnString2 += "''";
            }else{
                returnString2 += token.substring(i, i + 1);
            }
        }
        return returnString2;
    }


}
