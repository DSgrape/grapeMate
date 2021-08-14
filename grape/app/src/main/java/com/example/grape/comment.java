package com.example.grape;

public class comment {
    private String commentId;     // comment Id
    private String postId;  // 글 id
    private String writeId;  // 댓글 단 사람 id
    private String nickname;    // name
    private String content; // content
    private String createAt;    // 댓글 작성시간

    public comment(String commentId, String postId, String writeId, String nickname, String content, String createAt) {
        this.commentId = commentId;
        this.postId = postId;
        this.writeId = writeId;
        this.nickname = nickname;
        this.content = content;
        this.createAt = createAt;
    }

    public String getCommentId() {
        return commentId;
    }

    public String getPostId() {
        return postId;
    }

    public String getWriteId() {
        return writeId;
    }

    public String getNickname() {
        return nickname;
    }

    public String getContent() {
        return content;
    }

    public String getCreateAt() {
        return createAt;
    }

    public void setCommentId(String commentId) {
        this.commentId = commentId;
    }

    public void setPostId(String postId) {
        this.postId = postId;
    }

    public void setWriteId(String writeId) {
        this.writeId = writeId;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public void setCreateAt(String createAt) {
        this.createAt = createAt;
    }
}
