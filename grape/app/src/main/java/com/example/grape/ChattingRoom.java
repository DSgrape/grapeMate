package com.example.grape;

import java.util.HashMap;
import java.util.Map;

public class ChattingRoom {
    private String chatId;
    private String uid;         // 보낸 사람 id
    private String destinationId;
    private String chatName;  // 채팅 상대
    private String postId;
    private String category;
    private String title;

    public ChattingRoom(String chatId, String uid, String destinationId, String chatName, String postId, String category, String title) {
        this.chatId = chatId;
        this.chatName = chatName;
        this.postId = postId;
        this.category = category;
        this.title = title;
        this.uid = uid;

        this.destinationId = destinationId;
    }

    public String getChatName() {
        return chatName;
    }

    public String getDestinationId() { return destinationId; }

    public String getPostId() { return postId; }

    public String getCategory() { return category; }

    public String getTitle() { return title; }

    public String getChatId() { return chatId; }

    public String getUid() { return uid; }


    public void setChatName(String chatName) {
        this.chatName = chatName;
    }

    public void setPostId(String postId) { this.postId = postId; }

    public void setCategory(String category) { this.category = category; }

    public void setTitle(String title) { this.title = title; }
}
