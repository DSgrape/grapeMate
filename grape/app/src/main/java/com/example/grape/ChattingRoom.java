package com.example.grape;

public class ChattingRoom {
    private String image;     // 프로필 사진
    private String chatName;  // 채팅 상대

    public ChattingRoom(String image, String chatName) {
        this.image = image;
        this.chatName = chatName;
    }

    public String getChatName() {
        return chatName;
    }

    public String getImage() {
        return image;
    }

    public void setChatName(String chatName) {
        this.chatName = chatName;
    }

    public void setImage(String image) {
        this.image = image;
    }
}
