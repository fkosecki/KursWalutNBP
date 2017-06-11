
package pl.parser.nbp;

import java.io.IOException;
import java.net.URL;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import org.w3c.dom.*;
import org.xml.sax.SAXException;

public class XMLhandling {
    
    /**
     * Gets line from dir.txt and returns string in URL standard needed to download XML file from NBP site.
     */
    static Document getXML(String dirLine){
        try {
            String address = "http://www.nbp.pl/kursy/xml/"+(dirLine)+".xml";
            URL url;
            url = new URL(address);
            DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
            dbf.setFeature("http://apache.org/xml/features/nonvalidating/load-external-dtd", false);
            DocumentBuilder db = dbf.newDocumentBuilder();
            Document doc = db.parse(url.openStream());
            return doc;
            
        } catch (ParserConfigurationException | SAXException | IOException ex) {
            Logger.getLogger(XMLhandling.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }
    /**
     * Gets buying and selling rates for given currency from XML file.
     * @param doc XML file from which exchange rates will be extracted.
     * @param kod_waluty Currency code
     * @return Table: first argument is buying rate; second argument is selling rate.
     */
    static double[] getKursFromDoc(Document doc, String kod_waluty){
        double[] kurs_value = new double[2];
        try{
            NodeList nList = doc.getElementsByTagName("pozycja");
            for(int i = 0;i<nList.getLength();i++){
                
                Element element = (Element) nList.item(i);
                String temp = element.getElementsByTagName("kod_waluty").item(0).getTextContent().trim();
                
                if(temp.equalsIgnoreCase(kod_waluty)){
                    
                   String kurs = element.getElementsByTagName("kurs_kupna").item(0).getTextContent().trim();
                   kurs_value[0] = Double.parseDouble(kurs.replace(",", "."));
                   
                   kurs = element.getElementsByTagName("kurs_sprzedazy").item(0).getTextContent().trim();
                   kurs_value[1] = Double.parseDouble(kurs.replace(",", "."));
                   break;
                }
            }
            return kurs_value;
            
        } catch(DOMException | NumberFormatException e) {
            return null;
      }
    }

}
