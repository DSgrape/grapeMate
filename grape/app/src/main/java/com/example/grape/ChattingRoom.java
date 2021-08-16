package com.example.grape;

import java.util.HashMap;
import java.util.Map;

public class ChattingRoom {
    private String chatId;
    private String image;     // 프로필 사진
    private String chatName;  // 채팅 상대
    private String postId;
    private String category;
    private String title;
    private String uid;         // 보낸 사람 id
    private String message;     // 메시지
    private String timestamp;   // 시간
    private Map<String, Boolean> users = new HashMap<>();    //유저 아이디

    public ChattingRoom(String chatId, String image, String chatName, String postId, String category, String title, String uid, String message, String timestamp, Map<String, Boolean> users) {
        this.chatId = chatId;
        this.image = image;
        this.chatName = chatName;
        this.postId = postId;
        this.category = category;
        this.title = title;
        this.uid = uid;
        this.message = message;
        this.timestamp = timestamp;
        this.users = users;
    }

    public String getChatName() {
        return chatName;
    }

    public String getImage() {
        return image;
    }

    public String getPostId() { return postId; }

    public String getCategory() { return category; }

    public String getTitle() { return title; }

    public String getChatId() { return chatId; }

    public String getUid() { return uid; }

    public String getMessage() { return message; }

    public String getTimestamp() { return timestamp; }

    public Map<String, Boolean> getUsers() { return users; }

    public void setChatName(String chatName) {
        this.chatName = chatName;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public void setPostId(String postId) { this.postId = postId; }

    public void setCategory(String category) { this.category = category; }

    public void setTitle(String title) { this.title = title; }
}
