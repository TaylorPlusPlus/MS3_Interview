package Application_View;

import Application_Functionality.Parser;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Scanner;

public class MainPanel extends JPanel {

    //Properties
    private JButton fileChooser;
    private JLabel fileChooserLabel;
    private File file;
    private Scanner scan;
    private int response;




    //Constructor

    public MainPanel (){

        fileChooserLabel = new JLabel("Select a File to parse");
        fileChooser = new JButton("Choose a File");



        fileChooser.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                JFileChooser fileChooser = new JFileChooser(".");
                fileChooser.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
                response = fileChooser.showOpenDialog(null);


                if(response == JFileChooser.APPROVE_OPTION){

                    file = fileChooser.getSelectedFile();
                    System.out.println("Have the File with name " + file.getName());
                    Parser parser = new Parser();
                    try {
                        parser.parseFile(file);
                    } catch (FileNotFoundException ex) {
                        ex.printStackTrace();
                    } catch (IOException ex) {
                        ex.printStackTrace();
                    }

                }
            }
        });



        add(fileChooserLabel);
        add(fileChooser);


        setBackground(Color.LIGHT_GRAY);
        setPreferredSize(new Dimension(400,400));
    }



}
