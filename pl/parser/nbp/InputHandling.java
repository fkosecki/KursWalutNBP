package pl.parser.nbp;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.StringReader;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Pattern;


public class InputHandling {
    
    /**
     * Checks if input data is in correct format.
     * @param input Command line input
     */
    public boolean checkInput(String[] input){
        String errorMsg = "Podano bledne wartosci wejsciowe. Prawidlowe dane wejsciowe maja format:\n"
                            + "KOD_WALUTY YYYY-MM-DD YYYY-MM-DD\n"+"Przykładowo: EUR 2013-04-01 2016-02-27\n"
                            +"Dostepne waluty: (EUR,CHF,USD,GBP)";
        String[] kodyDostepnychWalut = {"EUR","CHF","USD","GBP"};
        boolean check = false;
            if(input != null && Pattern.matches("\\d\\d\\d\\d-\\d\\d-\\d\\d", input[1]) 
                             && Pattern.matches("\\d\\d\\d\\d-\\d\\d-\\d\\d", input[2])){
                for(String kod : kodyDostepnychWalut){
                    if(kod.equalsIgnoreCase(input[0])){
                        check = true;
                        break;
                    }
                }
            }
            else{
                check =  false;
            }
        if(!check){
            System.out.println(errorMsg);
        }
        return check;
    }
    /**
     * Parses input to prepare it for further usage.
     */
    private String inputDateParser(String input){
        String output = input.replaceAll("-", "");
        return output;
    }
    /**
     * Calculates standard deviation
     * @param kursy Table with exchange rates
     * @param sredniKurs Avarage exchange rate 
     * @return 
     */
    private double getOdchylenieStandardowe(double[] kursy, double sredniKurs){
        int counter = kursy.length;
        double srednieOdchylenie = 0;
        
        for(double kurs : kursy){
            double temp = Math.pow((kurs - sredniKurs),2);
            srednieOdchylenie += temp;
        }

            srednieOdchylenie = Math.sqrt(srednieOdchylenie/counter);
            return srednieOdchylenie;
    }
    /**
     * Calculates standard deviation and avarage exchange rate
     * @return Table: first argument is avarage buying rate; second argument is standard deviation for selling rate.
     */
    public double[] sredniKursIodchylenie(String[] arg){
        try {
            FileHandling fileHandler = new FileHandling();
            InputHandling inputHandler = new InputHandling();
            XMLhandling xmlHandler = new XMLhandling();
            String tekst = fileHandler.getFilteredDirsContents(inputHandler.inputDateParser(arg[1]), inputHandler.inputDateParser(arg[2]));
            BufferedReader reader = new BufferedReader(new StringReader(tekst));
            
            String inputLine;
            int counter = 0;
            double odchylenieStandardowe;
            double sredniKursKupna = 0;
            double sredniKursSprzedazy = 0;
            double[] kursy = new double[2];
            double[] kursySprzedazy = new double[fileHandler.getNumberOfFiles()];
            
            while((inputLine = reader.readLine()) != null){
                double tempKurs[] = xmlHandler.getKursFromDoc(xmlHandler.getXML(inputLine), arg[0]);
                kursySprzedazy[counter] = tempKurs[1];
                sredniKursSprzedazy += tempKurs[1];
                sredniKursKupna += tempKurs[0];
                counter++;
            }
            reader.close();
            
            sredniKursKupna = sredniKursKupna/counter;
            sredniKursSprzedazy = sredniKursSprzedazy/counter;
            
            odchylenieStandardowe = getOdchylenieStandardowe(kursySprzedazy, sredniKursSprzedazy);
            kursy[0] = sredniKursKupna;
            kursy[1] = odchylenieStandardowe;
            return kursy;
            
        } catch (IOException ex) {
            Logger.getLogger(InputHandling.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }
}
