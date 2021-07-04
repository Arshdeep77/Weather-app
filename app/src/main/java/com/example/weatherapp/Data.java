package com.example.weatherapp;

public class Data {
    String txt;
    String img;
    String time;

    public Data(String txt, String img, String time) {
        this.txt = txt;
        this.img = img;
        this.time = time;
    }

    public String getTxt() {
        return txt;
    }

    public void setTxt(String txt) {
        this.txt = txt;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
}
