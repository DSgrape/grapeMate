package com.example.grape;



import java.util.HashMap;

public class board {
    private String postId;         // 글 토큰
    private String id;          // 유저 Uid
    private String writeId;     // 글 작성자 이메일
    private String nickname;    // 글 작성자 닉네임
    private String postType;    // 글 주제
    private String title;   // 글 제목
    private String postContent;    // 글 내용
    private String endDay;     // 마감기한
    private double mapX;
    private double mapY;
    private String createAt;       // 작성시간

    public board(){}

    public board(String pid, String id, String writeId, String nickname, String postType, String title, String postContent, String endDay, String createAt, double x, double y) {
        this.postId = pid;
        this.id = id;
        this.writeId = writeId;
        this.nickname = nickname;
        this.postType = postType;
        this.title = title;
        this.postContent = postContent;
        this.endDay = endDay;
        this.createAt = createAt;
        this.mapX = x;
        this.mapY = y;
    }

    public String getPostId() { return postId; }

    public String getId() { return id; }

    public String getPostType() { return postType; }

    public String getTitle(){return title;}

    public String getPostContent() { return postContent; }

    public double getMapX() { return mapX; }

    public double getMapY() { return mapY; }

    public String getWriteId() { return writeId; }

    public String getEndDay() { return endDay; }

    public String getCreateAt() { return createAt; }

    public String getNickname() { return nickname; }

    public void setPostId(String pid) { this.postId = pid; }

    public void setId(String id) { this.id = id; }

    public void setWriteId(String writeId) { this.writeId = writeId; }

    public void setTitle(String title){this.title=title;}

    public void setPostType(String postType) { this.postType = postType; }

    public void setPostContent(String postContent) { this.postContent = postContent; }

    public void setEndDay(String endDay) { this.endDay = endDay; }

    public void setCreateAt(String createAt) { this.createAt = createAt; }

    public void setNickname(String nickname) { this.nickname = nickname; }

    public void setMapX(double mapX) { this.mapX = mapX; }

    public void setMapY(double mapY) { this.mapY = mapY; }
}

