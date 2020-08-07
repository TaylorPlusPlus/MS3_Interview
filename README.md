# MS3_Interview Coding Challenge

Author: Taylor Stoltzfus

**Summary**

This repository is for fellow developers to get an inside look at the application I have developed to parse a CSV file. Each line of the CSV file that is being parsed will either be submitted to an SQLite database or new CSV file depending on whether the line was formatted correctly.

**How Developers Can Starting the Application**

One approach to getting this application up and running is by opening the IntelliJ IDE (assuming developers have this or know how to install it as well as git). 

In intellij, go to file > new > project from version control

Select a directory you would like to have the application go to and copy the url from this github repository and paste it under the section labeled "URL:", then select the clone button.

Select yes through the option menus until the box titled Import project, select import project from external model with the option Maven, then continue.

Go to File > Open > location where you saved the application > Click CSV_File_Parser > Click the Ok button

Make sure the following are set

File -> Project structure -> Project -> Project SDK -> 14.

File -> Project structure -> Project -> Project language level -> 13.

File -> Project structure -> Project -> Modules -> -> Sources --> 13

In project -> ctrl + alt + s -> Build, Execution, Deployment -> Compiler -> Java Compiler -> Project bytecode version -> 13

In project -> ctrl + alt + s -> Build, Execution, Deployment -> Compiler -> Java Compiler -> Module -> 13.

You are now ready to run the Application!

Parse through the file tree at the top left of the intelliJ application in the following order
	CSV_File_Parser > src > main > java > Application_View > main

right click main and select the option Run main.main().

Once the application is running, the Choose a File button can be selected to bring up a file chooser.
	The test case file for this application can be found by selecting the "target" folder and selecting ms3Interview.csv (a .csv file from anywhere can be selected).
Once the file is selected, the program will begin parsing.

The .log, .db, and .csv files that this application creates can be found within the CSV_File_Parser folder once the program is finished running.

**Approach and Design Choices**

Within the requirements for this project, it was stated that this application needed to be completed "ASAP". Because of this, I did not spend much time on creating a "pretty" user interface. It was also stated that this application was going to support a new project. Because of this, I have decoupled most of the logic from the user interface so it could easily be integrated into a different project. 

Most of the design choices made not including the choices previously stated where made to allow the application to meet the design requirements. 

The only requirement that I believe I have not entirely satisfied is the processing optimization requirement. I believe the main bottle neck slowing the application down happens within the Parser class, within the parseFile() method between lines 145 and 175. 

