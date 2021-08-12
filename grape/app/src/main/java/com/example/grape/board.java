package com.example.grape;

import java.util.HashMap;

public class board {
    String pid;         // 글 토큰
    String id;          // 유저 Uid
    String writeId;     // 글 작성자 이메일
    String nickname;    // 글 작성자 닉네임
    String postType;    // 글 주제
    String title;   // 글 제목
    String postContent;    // 글 내용
    String endDay;     // 마감기한
    String createAt;       // 작성시간

    public board(){}

    public board(String pid, String id, String writeId, String nickname, String postType, String title, String postContent, String endDay, String createAt) {
        this.pid = pid;
        this.id = id;
        this.writeId = writeId;
        this.nickname = nickname;
        this.postType = postType;
        this.title = title;
        this.postContent = postContent;
        this.endDay = endDay;
        this.createAt = createAt;
    }

    public String getPid() { return pid; }

    public String getId() { return id; }

    public String getWirteId() { return writeId; }

    public String getPostType() { return postType; }

    public String getTitle(){return title;}

    public String getPostContent() { return postContent; }

    public String getEndDay() { return endDay; }

    public String getCreateAt() { return createAt; }

    public String getNickname() { return nickname; }

    public void setPid(String pid) { this.pid = pid; }

    public void setId(String id) { this.id = id; }

    public void setWirteId(String wirteId) { this.writeId = wirteId; }

    public void setTitle(String title){this.title=title;}

    public void setPostType(String postType) { this.postType = postType; }

    public void setPostContent(String postContent) { this.postContent = postContent; }

    public void setEndDay(String endDay) { this.endDay = endDay; }

    public void setCreateAt(String createAt) { this.createAt = createAt; }

    public void setNickname(String nickname) { this.nickname = nickname; }
}

