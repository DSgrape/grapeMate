package com.example.grape;

// 사용자 계정 정보 model
public class UserAccount {

    private String idToken;     // 고유 토큰
    private String emailId;     // 이메일
    private String password;    // 비밀번호
    private String nickname;    // 닉네임
    private int grade = -1;          // 회원 등급 0:owner 1:준회원 2:정회원
    private String location;    // 위치
    private String phoneNumber;

    public UserAccount() { }    // firebase realtime database 사용 시에 필수

    public String getEmailId() {
        return emailId;
    }

    public String getPassword() {
        return password;
    }

    public String getIdToken() {
        return idToken;
    }

    public String getNickname() {
        return nickname;
    }

    public int getGrade() {
        return grade;
    }

    public String getLocation() {
        return location;
    }

    public String getPhoneNumber() { return phoneNumber; }

    public void setEmailId(String emailId) {
        this.emailId = emailId;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setIdToken(String idToken) {
        this.idToken = idToken;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public void setGrade(int grade) {
        this.grade = grade;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public void setPhoneNumber(String phoneNumber) { this.phoneNumber = phoneNumber; }
}
