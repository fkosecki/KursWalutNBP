package pl.parser.nbp;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Locale;
import java.util.logging.Level;
import java.util.logging.Logger;

public class FileHandling {
    
    private int numberOfFiles = 0;
    
    /**
     * Returns number of XML files needed to calculate exchange rate for given period of time.
     */
    int getNumberOfFiles(){
        return this.numberOfFiles;
    }
    
    /**
     * Returns String in URL standard needed to download data from NBP site
     */
    private String getDirUrl(int year){
        
        String begin = "http://www.nbp.pl/kursy/xml/dir";
        String end = ".txt";
        Calendar calendar = new GregorianCalendar(Locale.getDefault());
        if(year<2002){
            year = 2002;
        }
        if(year != calendar.get(Calendar.YEAR)){
            return begin+year+end;
        }
        else
        {
            return begin+end;
        }
    }
    
    /**
     * Gets contents of dir.txt files from NBP site, needed to calculate exchange rate.
     */
    String getFilteredDirsContents(String startDate, String endDate){
        
        int startYear = Integer.parseInt(startDate.substring(0, 4));
        int endYear = Integer.parseInt(endDate.substring(0, 4));
        String allDirsContents = "";
        
        do{
            allDirsContents += getFilteredTextFromURL(getDirUrl(startYear),startDate,endDate);
            startYear++;
        }while((endYear - startYear) >= 0);
        return allDirsContents;
    }
    /**
     * Returns list of XML files needed to calculate exchange rate.
     */
    private String getFilteredTextFromURL(String address,String startDate,String endDate){
        
        try {
            int startInt = Integer.parseInt(startDate.substring(2));
            int endInt = Integer.parseInt(endDate.substring(2));
            URL url = new URL(address);
            BufferedReader reader = new BufferedReader(new InputStreamReader(url.openStream()));
            String inputLine;
            String output = "";
            
            while ((inputLine = reader.readLine()) != null){
                if(inputLine.startsWith("c")){
                    String tempOutput = inputLine.substring(5, 11);
                    if(Integer.parseInt(tempOutput)>=startInt 
                    && Integer.parseInt(tempOutput)<=endInt){
                        output += inputLine+"\n";
                        this.numberOfFiles++;
                    }
                }            
            }
            reader.close();
            return output;
            
        } catch (IOException ex) {
            Logger.getLogger(FileHandling.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }
    
}
