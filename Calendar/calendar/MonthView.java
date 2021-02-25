package calendar;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class MonthView extends JPanel {

    class CustomRenderer implements ListCellRenderer {
        protected Border noFocusBorder = new EmptyBorder(1, 10, 1, 1);

        protected DefaultListCellRenderer defaultRenderer = new DefaultListCellRenderer();

        public Component getListCellRendererComponent(JList list, Object value, int index, boolean isSelected, boolean cellHasFocus){
            JLabel renderer = (JLabel) defaultRenderer.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
            renderer.setBorder(noFocusBorder);

            String day = (String) value;
            if (day.contains("Sun"))
                renderer.setForeground(Color.BLUE);

            return renderer;
        }
    }

    protected int month;
    protected int prevMonth;
    protected int nextMonth;
    protected int year;
    protected int prevYear;
    protected int nextYear;
    ListCellRenderer renderer;

    protected JList<String> prevMonthList;
    protected JList<String> currentMonthList;
    protected JList<String> nextMonthList;

    protected MonthView(int month, int year) {
        renderer = new CustomRenderer();
        this.month = month;
        this.year = year;
        if (month == 1){
            prevMonth = 12;
            nextMonth = 2;
            prevYear = year - 1;
            nextYear = year;
        }
        else if(month == 12){
            prevMonth = 11;
            nextMonth = 1;
            prevYear = year;
            nextYear = year + 1;
        }
        else{
            prevMonth = month - 1;
            nextMonth = month + 1;
            prevYear = year;
            nextYear = year;
        }


        prevMonthList = new JList<>(new DataModel(prevMonth, prevYear));
        prevMonthList.setCellRenderer(renderer);
        currentMonthList = new JList<>(new DataModel(month, year));
        currentMonthList.setCellRenderer(renderer);
        nextMonthList = new JList<>(new DataModel(nextMonth, nextYear));
        nextMonthList.setCellRenderer(renderer);

        this.setLayout(new GridLayout(1,3));
        this.add(prevMonthList);
        this.add(currentMonthList);
        this.add(nextMonthList);
    }

    protected void update(int month, int year){
        this.removeAll();

        this.month = month;
        this.year = year;
        if (month == 1){
            prevMonth = 12;
            nextMonth = 2;
            prevYear = year - 1;
            nextYear = year;
        }
        else if(month == 12){
            prevMonth = 11;
            nextMonth = 1;
            prevYear = year;
            nextYear = year + 1;
        }
        else{
            prevMonth = month - 1;
            nextMonth = month + 1;
            prevYear = year;
            nextYear = year;
        }

        prevMonthList = new JList<>(new DataModel(prevMonth, prevYear));
        prevMonthList.setCellRenderer(renderer);
        currentMonthList = new JList<>(new DataModel(month, year));
        currentMonthList.setCellRenderer(renderer);
        nextMonthList = new JList<>(new DataModel(nextMonth, nextYear));
        nextMonthList.setCellRenderer(renderer);

        this.add(prevMonthList);
        this.add(currentMonthList);
        this.add(nextMonthList);

        this.repaint();
    }
}
