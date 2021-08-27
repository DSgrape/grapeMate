# grapeMate

> 포도메이트에 오신 여러분 환영합니다.
> 
혼자 귀가하는 20대 여성을 표적으로 한 귀갓길 범죄가 증가하여 목적지가 비슷한 여럿이 만나 함께 귀가하면, 범죄의 표적에서 벗어나는 동시에 안전을 보장받을 수 있을 것이라 생각하여 <b>같이 귀가할 사람을 구할 수 있는 커뮤니케이션 앱</b>을 기획하게 되었다. 더불어 운동, 음식, 공부 등 다양한 분야에서 함께할 동료를 구할 수 있는 앱을 기획하고자 하였다. 자체 로그인과 학생증 인증을 받아 여성들만 가입하게 하며, GPS를 통해 약속장소의 정확한 위치를 확인하고 즐겨 찾는 장소를 설정해 놓는다. 글 작성 시, 사용자가 마감 날짜를 설정할 수 있다. 작성한 글은 메인 화면 [글]을 통해 모아 볼 수 있으며, 글을 올리거나 댓글을 작성함으로써 자신에게 필요한 메이트를 찾을 수 있다. 이후 채팅을 통해 상대와 대화를 더 이어가거나 일정을 조율해 나갈 수 있다. 카테고리가 나눠져 있어 사용자가 원하는 주제의 글을 골라볼 수 있다. 상대방에게 신뢰와 호감을 얻으면 하트를 받게 되는데, 하트를 10개씩 받을 때마다 포도알 스티커가 하나씩 채워진다. 모은 포도알 스티커는 내 스티커 보기 화면에서 확인 가능하다. ‘포도메이트’는 포도송이의 한 꼭지에 포도알이 모여 달린 모습에서 연결, 동료의 의미를 느껴 착안했다. <b>‘포도메이트’ 안에서 20대 여성들은 각각 포도알이 되어 안전을 보장받고 좋은 동료를 만날 기회를 가지며 자신만의 포도송이 즉, 공동체를 형성해 나갈 수 있다.</b>

## 포도메이트 초기 UI
![포도메이트_완성](https://user-images.githubusercontent.com/57867252/128599198-aca9020a-08ed-4392-b6bc-0f40a8a1cc87.jpg)

## Flow Chart
![flowChart1](https://user-images.githubusercontent.com/57867252/131131823-2b8b0d05-c93a-4ee0-a93e-dd45dee1fe6d.png)
![flowChart2](https://user-images.githubusercontent.com/57867252/131136226-3da55318-766a-4081-b55d-87574a821895.png)
![flowChart3](https://user-images.githubusercontent.com/57867252/131136343-ea9b3076-729a-48f4-b8f5-9cd14a96822c.png)
![flowChart4](https://user-images.githubusercontent.com/57867252/131136430-50199a29-2987-4369-9aba-0c1f3da133b2.png)


## 개발 환경

```sh
Android Studio Version 4.1.1
```

## 사용 예제
### 로그인 화면  
- LoginActivity.java
- 로그인 및 회원가입 가능(Firebase Authentication)
<img src="https://user-images.githubusercontent.com/57867252/131130469-b12f72c1-d622-42b6-bd26-b0df5b76cd45.png" width="200"/>

### 회원가입 화면 
- JoinActivity.java
- 회원가입(Firebase Authentication) / 학생증 인증(Firebase Storage) / 회원정보 저장(Firebase Realtime Database)
<img src="https://user-images.githubusercontent.com/57867252/131136973-37dac5a7-b79b-4bd8-8b43-213c0b19c765.png" width="200"/>

### 메인 화면
- Main_Fragment.java
- 사용자들이 쓴 글을 게시판 형식으로 확인하기
<img src="https://user-images.githubusercontent.com/57867252/131136587-9e87acdb-f810-4d99-ae33-c8d09b6ce4bd.png" width="200"/>

### 메인 화면2
- 사용자들이 쓴 글을 위치 별로 확인하기
<img src="https://user-images.githubusercontent.com/57867252/131138994-f4ebf5a9-5ca3-4f77-89ec-c21312161d07.png" width="200"/>

### 마이페이지
<img src="https://user-images.githubusercontent.com/57867252/131139094-59398fcb-3d9f-4f9b-b31f-37582a64501d.png" width="200"/>

### 세부 글 내용 보기 
- showPost.java  
- 좋아요 및 댓글, 선호 거래 위치 확인, 채팅하기 기능  

<img src="https://user-images.githubusercontent.com/57867252/131140540-00075252-43f7-47a5-ac3a-92b7cf5b12a4.png" width="200"/> <img src="https://user-images.githubusercontent.com/57867252/131140251-bbe30fa2-907a-41b3-86f3-eeb3f5d34630.png" width="200"/> <img src="https://user-images.githubusercontent.com/57867252/131140695-104fdb70-5584-492f-9eeb-cdc6017871b2.png" width="200"/>


### 채팅하기
- ChattingRoom.java(클래스 파일) / myChat.java
- 본인이 속한 채팅방을 확인
<img src="https://user-images.githubusercontent.com/57867252/131141062-c778b023-194f-42cf-9d07-083014e5d242.png" width="200"/>



### 글쓰기 화면
- addPost.java
<img src="https://user-images.githubusercontent.com/57867252/131138818-29d441b6-9bd6-45e7-9a58-0e9ead2bc53c.png" width="200"/>




## 업데이트 내역

* 0.1.0
    * 작품 제출
    * 서울여대 해커톤 인기상 수상
* 0.0.1
    * 작업 진행 중

## 정보

인스타그램 – [@ds_podong](https://www.instagram.com/ds_podong/)
이메일 - shn7446@duksung.ac.kr  - kytbible@gmail.com

