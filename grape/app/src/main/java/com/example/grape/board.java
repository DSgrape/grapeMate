package com.example.grape;

public class board {
    String type;
    String title;

    public String getType(){return type;}
    public void setType(String type){this.type=type;}
    public String getTitle(){return title;}
    public void setTitle(String title){this.title=title;}

    public board(String type, String title){
        this.title=title;
        this.type=type;
    }
}
