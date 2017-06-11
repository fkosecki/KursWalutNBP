package pl.parser.nbp;

import javax.swing.*;
import graphicInterface.*;

import java.util.Scanner;

public class MainClass {
    public static void main(String[] args) {

        boolean useGui = true;
        if (!useGui) {
            InputHandling inputHandler = new InputHandling();
            String[] input;
            String[] args2 = new String[3];
            Scanner sc = new Scanner(System.in);
            String tempIn = sc.nextLine();
            args = tempIn.split("\\s+");
            args2[0] = "chf";
            args2[1] = "2017-04-30";
            args2[2] = "2017-05-05";
            if (args.length > 0) {
                input = args;
            } else {
                input = args2;
            }
            if (inputHandler.checkInput(input)) {
                double[] kursy = inputHandler.sredniKursIodchylenie(args2);
                System.out.println("Sredni kurs kupna: \n" + (double) Math.round(kursy[0] * 10000) / 10000);
                System.out.println("Srednie odchylenie standardowe kursu sprzedazy: \n" + (double) Math.round(kursy[1] * 10000) / 10000);
            }
        } else {

            JFrame frame = new JFrame();
            Gui okno = new Gui();
            frame.setResizable(false);
            frame.setTitle("Sredni kurs waluty na podstawie danych z NBP");
            frame.setContentPane(okno.mainWindow);
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.pack();
            frame.setVisible(true);

        }
    }
}
