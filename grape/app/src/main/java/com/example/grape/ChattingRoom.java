package com.example.grape;

import java.util.HashMap;
import java.util.Map;

public class ChattingRoom {
    private String chatId;      // 채팅방 고유키
    private String postId;      // 글 id
    private String timestamp;   // 시간
    private Comment comments = new Comment();
    Users users = new Users();    //유저 아이디
    private String nickname;
    private String category;
    private String title;

    public static class Comment {
        public String uid;
        public String message;

        public Comment() {}
        public Comment(String uid, String message) {
            this.uid = uid;
            this.message = message;
        }
    }
    public static class Users {
        public String uid;
        public String destinationUid;

        public Users() {}
        public Users(String uid, String destinaionUid) {
            this.uid = uid;
            this.destinationUid = destinaionUid;
        }
    }

    public ChattingRoom() {}

    // myChat에서 필요한 정보

    public ChattingRoom(String chatId, String postId, Users users) {
        this.chatId = chatId;
        this.postId = postId;
        this.users = users;
    }

    public ChattingRoom(String chatId, String postId, Users users, String nickname, String category, String title) {
        this.chatId = chatId;
        this.postId = postId;
        this.users = users;
        this.nickname = nickname;
        this.category = category;
        this.title = title;
    }

    public ChattingRoom(String chatId, String postId, String timestamp, Comment comments, Users users) {
        this.chatId = chatId;
        this.postId = postId;
        this.timestamp = timestamp;
        this.comments = comments;
        this.users = users;
    }

    public String getNickname() { return nickname; }

    public String getCategory() { return category; }

    public String getTitle() { return title; }

    public String getChatId() { return chatId; }

    public String getPostId() { return postId; }

    public String getTimestamp() { return timestamp; }

    public Comment getComments() { return comments; }

    public Users getUsers() { return users; }

    public void setChatId(String chatId) { this.chatId = chatId; }

    public void setPostId(String postId) { this.postId = postId; }

    public void setTimestamp(String timestamp) { this.timestamp = timestamp; }

    public void setComments(Comment comments) { this.comments = comments; }

    public void setUsers(Users users) { this.users = users; }

    public void setNickname(String nickname) { this.nickname = nickname; }

    public void setCategory(String category) { this.category = category; }

    public void setTitle(String title) { this.title = title; }
}
