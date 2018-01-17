package pl.parser.nbp;

import javax.swing.*;
import graphicInterface.*;

import java.util.Scanner;

public class MainClass {
    public static void main(String[] args) {
        String inputString = "";
        boolean useGui = false;
        System.out.println("Wersja z GUI wybierz 1\nWersja konsolowa wybierz 2\nPodaj nr wersji i wciśnij ENTER:\n");

        while (!inputString.trim().equalsIgnoreCase("1") && !inputString.trim().equalsIgnoreCase("2")) {
            Scanner sc = new Scanner(System.in);
            inputString = sc.next();
        }
        if (inputString.equals("1")) {
            useGui = true;
        }
        if (!useGui) {
            System.out.println("Podaje wartosci wejsciowe. Prawidlowe dane wejsciowe maja format:\n" +
                    "KOD_WALUTY YYYY-MM-DD YYYY-MM-DD\n" +
                    "Przykładowo: EUR 2013-04-01 2016-02-27\n" +
                    "Dostepne waluty: (EUR,CHF,USD,GBP)\n");
            InputHandling inputHandler = new InputHandling();
            String[] args2;
            Scanner sc = new Scanner(System.in);
            String tempIn = sc.nextLine();
            args2 = tempIn.split("\\s+");
            if(args2.length < 3){
                args2 = new String[]{"a","b","c"};
            }
            if (inputHandler.checkInput(args2)) {
                double[] kursy = inputHandler.sredniKursIodchylenie(args2);
                System.out.println("Sredni kurs kupna: \n" + (double) Math.round(kursy[0] * 10000) / 10000);
                System.out.println("Srednie odchylenie standardowe kursu sprzedazy: \n" + (double) Math.round(kursy[1] * 10000) / 10000);
            }
        } else{
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
