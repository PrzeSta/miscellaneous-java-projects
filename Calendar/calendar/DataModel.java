package calendar;

import javax.swing.*;
import java.util.Calendar;
import java.util.GregorianCalendar;

import static java.util.Calendar.*;

public class DataModel extends AbstractListModel {

    protected int year;
    protected int month;
    private Calendar gregorianCalendar;

    protected DataModel(int month, int year){
        this.month = month;
        this.year = year;
    }

    protected int getStartDayValue() {
        this.gregorianCalendar = new GregorianCalendar(year, month - 1, 1);
        switch (gregorianCalendar.get(DAY_OF_WEEK)) {
            case MONDAY:
                return 1;
            case TUESDAY:
                return 2;
            case WEDNESDAY:
                return 3;
            case THURSDAY:
                return 4;
            case FRIDAY:
                return 5;
            case SATURDAY:
                return 6;
            case SUNDAY:
                return 7;
        }
        return 0;
    }

    protected String getNameDay(int day){
        this.gregorianCalendar = new GregorianCalendar(year, month - 1, day);

        switch(gregorianCalendar.get(DAY_OF_WEEK)) {
            case MONDAY:
                return "Mon";
            case TUESDAY:
                return "Tue";
            case WEDNESDAY:
                return "Wed";
            case THURSDAY:
                return "Thu";
            case FRIDAY:
                return "Fri";
            case SATURDAY:
                return "Sat";
            case SUNDAY:
                return "Sun";
        }
        return "Error";

    }

    protected String getMonthName(){
        switch(month) {
            case 1:
                return "January";
            case 2:
                return "February";
            case 3:
                return "March";
            case 4:
                return "April";
            case 5:
                return "May";
            case 6:
                return "June";
            case 7:
                return "July";
            case 8:
                return "August";
            case 9:
                return "September";
            case 10:
                return "October";
            case 11:
                return "November";
            case 12:
                return "December";
        }
        return "Error in month name";
    }

    @Override
    public int getSize() {
        if(month == 10 && year == 1582)
            return 21;

        switch(month){
            case 1:
            case 3:
            case 5:
            case 7:
            case 8:
            case 10:
            case 12:
                return 31;
            case 4:
            case 6:
            case 9:
            case 11:
                return 30;
            case 2: {
                if ((year > 1582 && ((year % 4 == 0) && !(year % 100 == 0)) ) ||
                        (year < 1582 && year % 4 == 0) ||
                        (year % 400 == 0))
                    return 29;
                else
                    return 28;
            }
            default:
                System.out.println("Invalid month.");
                return -1;
         }
    }

    @Override
    public Object getElementAt(int day) {
        day = day + 1;
        if(month == 10 && year == 1582 && day > 4)
            day = day + 10;

        return this.getNameDay(day) + " " + day + " " + this.getMonthName();
    }
}
