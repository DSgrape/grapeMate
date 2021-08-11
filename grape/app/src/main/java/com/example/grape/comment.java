package com.example.grape;

public class comment {
    String name;
    String content;

    public comment(String name, String content){
        this.name=name;
        this.content=content;
    }

    public void setName(String name) { this.name = name; }
    public String getName() { return name; }

    public void setContent(String content){this.content=content;}
    public String getContent(){return content;}
}
