package Application_View;

import javax.swing.*;

public class main {

    public static void main(String[] args){

        JFrame frame = new JFrame("CSV File to SQLite Database Application");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        MainPanel panel = new MainPanel();

        frame.getContentPane().add(panel);
        frame.pack();
        frame.setVisible(true);

    }

}
