package com.example.weatherapp;

import java.util.StringJoiner;
class Icon{
    String id;

    public Icon(String id) {
        this.id = id;
    }
}
class RiseSet{

    String set;
    String rise;

    public RiseSet(String set, String rise) {
        this.set = set;
        this.rise = rise;
    }


}
public class apiData {
    Icon icon;
    String time;
String ts;
    String dis;
    String temp;
    String maxtem,mintem;
    String humidity;
    String location;
    RiseSet tp;



    public apiData(String time, String ts, String dis, String temp, String maxtem, String mintem, String humidity, String location, RiseSet tp,Icon icon) {
        this.time = time;
        this.ts = ts;
        this.dis = dis;
        this.temp = temp;
        this.maxtem = maxtem;
        this.mintem = mintem;
        this.humidity = humidity;
        this.location = location;
        this.tp = tp;
        this.icon=icon;
    }

    public apiData(String time, String ts, String dis, String temp, String maxtem, String mintem, String humidity, String location) {
        this.time = time;
        this.ts = ts;
        this.dis = dis;
        this.temp = temp;
        this.maxtem = maxtem;
        this.mintem = mintem;
        this.humidity = humidity;
        this.location = location;
    }

    public apiData(String time, String ts, String dis, String temp, String maxtem, String mintem, String humidity, Icon icon) {
        this.time = time;
        this.ts = ts;
        this.dis = dis;
        this.temp = temp;
        this.maxtem = maxtem;
        this.mintem = mintem;
        this.humidity = humidity;
     this.icon=icon;
    }
}
