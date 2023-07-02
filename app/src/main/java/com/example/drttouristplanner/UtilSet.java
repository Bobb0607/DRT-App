package com.example.drttouristplanner;

import java.util.Calendar;

public class UtilSet {

    String value;

    public UtilSet(String value) {
        this.value = value;
    }

    public UtilSet(){

    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getDateToday()
    {
        String datenow_format = "";

        Calendar cal = Calendar.getInstance();
        int month = cal.get(Calendar.MONTH);
        int year = cal.get(Calendar.YEAR);
        int day = cal.get(Calendar.DAY_OF_YEAR);

        datenow_format = makeDateString(day,month,year);
        return datenow_format;
    }

    public String getTodaysDate()
    {
        Calendar cal = Calendar.getInstance();
        int year = cal.get(Calendar.YEAR);
        int month = cal.get(Calendar.MONTH);

        month = month + 1;
        int day = cal.get(Calendar.DAY_OF_MONTH);
        return makeDateString(day, month, year);

    }

    public String toDate(Calendar cal){
        String newDate = "";



        return newDate;
    }

    public String getAge(int year, int month, int day){
        Calendar dob = Calendar.getInstance();
        Calendar today = Calendar.getInstance();

        dob.set(year, month, day);

        int age = today.get(Calendar.YEAR) - dob.get(Calendar.YEAR);

        if (today.get(Calendar.DAY_OF_YEAR) < dob.get(Calendar.DAY_OF_YEAR)){
            age--;
        }

        Integer ageInt = new Integer(age);
        String ageS = ageInt.toString();

        return ageS;
    }


    public String makeDateString(int day, int month, int year)
    {
        String day_string = ""+day+"";
        String date_format = "";
        String month_string = "";
        String year_string = ""+year+"";

        switch(month)
        {
            case 1:
                month_string = "January";
                break;
            case 2:
                month_string = "February";
                break;
            case 3:
                month_string = "March";
                break;
            case 4:
                month_string = "April";
                break;
            case 5:
                month_string = "May";
                break;
            case 6:
                month_string = "June";
                break;
            case 7:
                month_string = "July";
                break;
            case 8:
                month_string = "August";
                break;
            case 9:
                month_string = "September";
                break;
            case 10:
                month_string = "October";
                break;
            case 11:
                month_string = "November";
                break;
            case 12:
                month_string = "December";
                break;
        }

        date_format = month_string+" "+day+", "+year;

        return date_format;
    }

}
