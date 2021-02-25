package calendar;

import javax.swing.*;
import java.awt.*; 
import java.util.Calendar;
import java.util.GregorianCalendar;

public class MainWindow extends JFrame {

    protected int size;
    protected int year;
    protected int month;

    protected JTabbedPane tabbedPane;
    protected YearView yearView;
    protected MonthView monthView;

    protected JToolBar toolBar;
    protected JButton prev;
    protected JButton next;
    protected JSpinner spinner;
    protected JButton spinnerButton;
    protected JScrollBar scrollBar;

    protected Calendar gregorianCalendar;

    public MainWindow(int size){
        this.size = size;
        gregorianCalendar = new GregorianCalendar();
        year = gregorianCalendar.get(Calendar.YEAR);
        month = gregorianCalendar.get(Calendar.MONTH) + 1; //January == 0 (!)

        this.setTitle("Calendar");
        this.setLayout(new BorderLayout());
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(size, size);

        tabbedPane = new JTabbedPane();
        yearView = new YearView(year);
        tabbedPane.addTab("Year: " + year, yearView);
        monthView = new MonthView(month, year);
        tabbedPane.addTab("Month: " + getMonthName(month), monthView);
        this.add(tabbedPane, BorderLayout.CENTER);

        toolBar = new JToolBar();

        prev = new JButton("Prev");
        prev.addActionListener(actionEvent -> {
            if(tabbedPane.getSelectedComponent() == monthView) {
                if (month == 1) {
                    month = 12;
                    year = year - 1;
                } else
                    month = month - 1;
                scrollBar.setValue(month);
            }
            else
                year = year - 1;

            yearView.update(year);
            monthView.update(month, year);
            tabbedPane.repaint();
            tabbedPane.setTitleAt(0, "Year: " + year);
            tabbedPane.setTitleAt(1, "Month: " + getMonthName(month));
        });

        next = new JButton("Next");
        next.addActionListener(actionEvent -> {
            if(tabbedPane.getSelectedComponent() == monthView) {
                if (month == 12) {
                    month = 1;
                    year = year + 1;
                } else
                    month = month + 1;
                scrollBar.setValue(month);
            }
            else
                year = year + 1;

            yearView.update(year);
            monthView.update(month, year);
            tabbedPane.repaint();
            tabbedPane.setTitleAt(0, "Year: " + year);
            tabbedPane.setTitleAt(1, "Month: " + getMonthName(month));
        });

        spinner = new JSpinner(new SpinnerNumberModel(year, -4000, 4000, 1));
        spinnerButton = new JButton("Jump to year");
        spinnerButton.addActionListener(actionEvent -> {
            year = (Integer) spinner.getValue();
            yearView.update(year);
            tabbedPane.repaint();
            tabbedPane.setTitleAt(0, "Year: " + year);
        });

        scrollBar = new JScrollBar(Adjustable.HORIZONTAL, month, 0, 1, 12);
        scrollBar.addAdjustmentListener(adjustmentEvent -> {
            month = scrollBar.getValue();
            monthView.update(month, year);
            tabbedPane.repaint();
            tabbedPane.setTitleAt(1, "Month: " + getMonthName(month));
        });

        toolBar.add(prev);
        toolBar.add(next);
        toolBar.add(spinner);
        toolBar.add(spinnerButton);
        toolBar.add(scrollBar);
        this.add(toolBar, BorderLayout.PAGE_END);

        this.setVisible(true);
        this.pack();
    }

    protected String getMonthName(int month){

        return switch (month) {
            case 1 -> "January";
            case 2 -> "February";
            case 3 -> "March";
            case 4 -> "April";
            case 5 -> "May";
            case 6 -> "June";
            case 7 -> "July";
            case 8 -> "August";
            case 9 -> "September";
            case 10 -> "October";
            case 11 -> "November";
            case 12 -> "December";
            default -> "Error in month name";
        };
    }
}
