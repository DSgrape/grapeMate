package com.example.grape;

import org.w3c.dom.Comment;

import java.util.HashMap;
import java.util.Map;

public class chat {
    private String chatId;      // 채팅방 고유키
    private String postId;      // 글 id
    private Comment comments = new Comment();
    Users users = new Users();    //유저 아이디

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

    public chat() {}

    public chat(String chatId, String postId, Comment comments, Users users) {
        this.chatId = chatId;
        this.comments = comments;
        this.users = users;
    }

    public String getChatId() { return chatId; }

    public String getPostId() { return postId; }

    public Users getUsers() { return users; }

    public Comment getComments() { return comments; }


    public void setChatId(String chatId) { this.chatId = chatId; }

    public void setComments(Comment comments) { this.comments = comments; }

    public void setUsers(Users users) { this.users = users; }

    public void setPostId(String postId) { this.postId = postId; }

}
