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
    private int hearts;     // 하트 수
    private String endDay;     // 마감기한
    private String createAt;       // 작성시간

    public board(){}

    public board(String pid, String id, String writeId, String nickname, String postType, String title, String postContent, int hearts, String endDay, String createAt) {
        this.postId = pid;
        this.id = id;
        this.writeId = writeId;
        this.nickname = nickname;
        this.postType = postType;
        this.title = title;
        this.postContent = postContent;
        this.hearts = hearts;
        this.endDay = endDay;
        this.createAt = createAt;
    }

    public String getPostId() { return postId; }

    public String getId() { return id; }

    public String getPostType() { return postType; }

    public String getTitle(){return title;}

    public String getPostContent() { return postContent; }

    public String getWriteId() { return writeId; }

    public int getHearts() { return hearts; }

    public String getEndDay() { return endDay; }

    public String getCreateAt() { return createAt; }

    public String getNickname() { return nickname; }

    public void setPostId(String pid) { this.postId = pid; }

    public void setId(String id) { this.id = id; }

    public void setWriteId(String wirteId) { this.writeId = wirteId; }

    public void setTitle(String title){this.title=title;}

    public void setPostType(String postType) { this.postType = postType; }

    public void setPostContent(String postContent) { this.postContent = postContent; }

    public void setHearts(int hearts) { this.hearts = hearts; }

    public void setEndDay(String endDay) { this.endDay = endDay; }

    public void setCreateAt(String createAt) { this.createAt = createAt; }

    public void setNickname(String nickname) { this.nickname = nickname; }
}

