package com.example.grape;

public class chat {
    private String chatContent;     // 채팅내용
    private  int type; //0=내체팅, 1=상대
    public chat(String chatContent, int type) {
        this.chatContent = chatContent;
        this.type=type;
    }

    public String getChatContent() {
        return chatContent;
    }

    public int getType() { return type; }

    public void setChatName(String chatContent) {
        this.chatContent = chatContent;
    }

    public void setImage(int type) {
        this.type = type;
    }
}
