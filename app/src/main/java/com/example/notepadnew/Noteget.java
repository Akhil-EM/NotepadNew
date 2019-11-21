package com.example.notepadnew;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Noteget {
    public String tittle;
    public String date;
   public String content;
     public Noteget()
     {

     }
    public Noteget( String title, String date, String content) {
        this.tittle = title;
        this.date = date;
        this.content = content;
    }


    public String getTitle() {
        return tittle;
    }

    public String getDate() {
        return date;
    }

    public String getContents() {
        return content;
    }

}
