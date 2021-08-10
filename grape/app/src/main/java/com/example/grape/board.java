package com.example.grape;

import java.util.HashMap;

public class board {
    String id;          //Uid
    String wirteId;
    String postType;    // 글 주제
    String title;   // 글 제목
    String postContent;    // 글 내용
    String endDay;     // 마감기한
    String createAt;       // 작성시간

    public board(){}
    public board(String id, String wirteId, String postType, String title, String postContent, String endDay, String createAt) {
        this.id = id;
        this.wirteId = wirteId;
        this.postType = postType;
        this.title = title;
        this.postContent = postContent;
        this.endDay = endDay;
        this.createAt = createAt;
    }

    public String getId() { return id; }

    public String getWirteId() { return wirteId; }

    public String getPostType() { return postType; }

    public String getTitle(){return title;}

    public String getPostContent() { return postContent; }

    public String getEndDay() { return endDay; }

    public String getCreateAt() { return createAt; }

    public void setId(String id) { this.id = id; }

    public void setWirteId(String wirteId) { this.wirteId = wirteId; }

    public void setTitle(String title){this.title=title;}

    public void setPostType(String postType) { this.postType = postType; }

    public void setPostContent(String postContent) { this.postContent = postContent; }

    public void setEndDay(String endDay) { this.endDay = endDay; }

    public void setCreateAt(String createAt) { this.createAt = createAt; }
}

