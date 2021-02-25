package calendar;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class YearView extends JPanel {

    protected ArrayList<DataModel> months;
    protected int year;

    protected YearView(int year){
        this.year = year;
        this.months = new ArrayList<>();
        for(int i = 1; i <= 12; i++){
            months.add(new DataModel(i, year));
        }

        this.setLayout(new GridLayout(4,3));
        for(DataModel month : months){
            this.add(new MonthPanel(month));
        }
    }

    protected void update(int year){
        this.removeAll();

        this.year = year;
        months = new ArrayList<>();

        for(int i = 1; i <= 12; i++){
            months.add(new DataModel(i, year));
        }

        for(DataModel month : months){
            this.add(new MonthPanel(month));
        }

        this.repaint();
    }
}
