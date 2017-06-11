package graphicInterface;

import pl.parser.nbp.InputHandling;

import javax.swing.*;
import java.awt.event.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * This GUI was meant to just simply work. Sorry it's messy and not fancy.
 */
public class Gui implements ActionListener{
    private JComboBox comboBox1;
    private JComboBox comboBox2;
    private JComboBox comboBox3;
    private JComboBox comboBox4;
    private JComboBox comboBox5;
    private JComboBox comboBox6;
    private JComboBox comboBox7;
    private JButton wyznaczSredniKursButton;
    public JPanel mainWindow;
    private JPanel Results;
    private JLabel sredniKurs;
    private JLabel srednieOdchylenie;

    public Gui() {
        wyznaczSredniKursButton.addActionListener(this);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        InputHandling inputHandler = new InputHandling();
        String[] input = new String[3];
        input[0] = (String)this.comboBox1.getSelectedItem();
        input[1] = dateString((int)this.comboBox2.getSelectedItem(),(int)this.comboBox3.getSelectedItem(),(int)this.comboBox4.getSelectedItem());
        input[2] = dateString((int)this.comboBox5.getSelectedItem(),(int)this.comboBox6.getSelectedItem(),(int)this.comboBox7.getSelectedItem());
        if(inputHandler.checkInput(input)){
            double[] kursy = inputHandler.sredniKursIodchylenie(input);
            String sredniKurs = ""+(double)Math.round(kursy[0]*10000)/10000;
            String srednieOdch = ""+(double)Math.round(kursy[1]*10000)/10000;
            this.sredniKurs.setText(sredniKurs);
            this.srednieOdchylenie.setText(srednieOdch);
        }
    }

    public String dateString(int year, int month, int day){
        String newDate;
        if(month < 10){
            newDate = "0"+month;
        }else{
            newDate = ""+day;
        }
        newDate = newDate+"-";
        if(day < 10){
            newDate += "0"+day;
        }else{
            newDate += ""+day;
        }

        return year+"-"+newDate;
    }

    private void createUIComponents() {
        // TODO: place custom component creation code here

        String[] waluty = {"USD","CHF","GBP","EUR"};
        this.comboBox1 = new JComboBox<String>();
        for(String item : waluty){
            this.comboBox1.addItem(item);
        }

        DateTimeFormatter rokDTF = DateTimeFormatter.ofPattern("yyyy");
        DateTimeFormatter miesiacDTF = DateTimeFormatter.ofPattern("MM");
        DateTimeFormatter dzienDTF = DateTimeFormatter.ofPattern("dd");
        LocalDateTime now = LocalDateTime.now();
        int dzien = Integer.parseInt(dzienDTF.format(now));
        int miesiac = Integer.parseInt(miesiacDTF.format(now));
        int rok = Integer.parseInt(rokDTF.format(now));
        this.comboBox2 = new JComboBox<Integer>();
        this.comboBox5 = new JComboBox<Integer>();
        for(int i = 2002; i <= rok; i++){
            this.comboBox2.addItem(i);
            this.comboBox5.addItem(i);
        }
        this.comboBox3 = new JComboBox<Integer>();
        this.comboBox6 = new JComboBox<Integer>();
        for(int i = 1; i <= 12; i++){
            this.comboBox3.addItem(i);
            this.comboBox6.addItem(i);
        }
        this.comboBox4 = new JComboBox<Integer>();
        this.comboBox7 = new JComboBox<Integer>();
        for(int i = 1; i <= 31; i++){
            this.comboBox4.addItem(i);
            this.comboBox7.addItem(i);
        }
    }
}
