package com.android.railreserve;

/**
 * Created by Mayur Makhija on 01-05-2017.
 */

public class User {
    public String source,destination;
    public int day,month,year;
    public User(){

    }

    public User(String source, String destination, int day, int month, int year) {
        this.source = source;
        this.destination = destination;
        this.day = day;
        this.month = month;
        this.year = year;
    }
}
