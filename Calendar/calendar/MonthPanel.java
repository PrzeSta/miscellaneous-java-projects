package calendar;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;

public class MonthPanel extends JPanel {

    protected DataModel month;

    //due to colorblindness I decided to use blue instead of red to mark Sundays
    protected MonthPanel(DataModel input){
        this.month = input;

        String title = this.month.getMonthName();
        Border border = BorderFactory.createTitledBorder(title);
        this.setBorder(border);

        int dayCounter = 1;
        int numberOfRows = (int) Math.ceil((1.0 * this.month.getStartDayValue() - 1.0 + 1.0 * this.month.getSize()) / 7);
        this.setLayout(new GridLayout(numberOfRows,7));

        for(int i = 1; i < month.getStartDayValue(); i++) {
            dayCounter += 1;
            this.add(new JLabel(" "));
        }

        JLabel dayLabel;
        if(month.getMonthName() == "October") {
            int day;
            for (int i = 0; i < month.getSize(); i++) {
                day = Integer.parseInt((((String) month.getElementAt(i)).split(" ")[1]));
                if (i < 10)
                    dayLabel = new JLabel(" " + day);
                else
                    dayLabel = new JLabel(Integer.toString(day));
                if (dayCounter % 7 == 0)
                    dayLabel.setForeground(Color.BLUE);
                this.add(dayLabel);
                dayCounter += 1;
            }
        }
        else{
            for (int i = 1; i <= month.getSize(); i++) {
                if (i < 10)
                    dayLabel = new JLabel(" " + i);
                else
                    dayLabel = new JLabel(Integer.toString(i));
                if (dayCounter % 7 == 0)
                    dayLabel.setForeground(Color.BLUE);
                this.add(dayLabel);
                dayCounter += 1;
            }
        }

        for(int i = this.month.getSize() + this.month.getStartDayValue() - 1; i < numberOfRows * 7; i++) {
            this.add(new JLabel("  "));
        }
    }
}
