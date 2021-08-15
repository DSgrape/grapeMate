package com.example.grape;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class showPost extends Fragment {
    ImageButton back;
    String postToken, postUid;
    TextView category;
    TextView title;
    TextView content;
    TextView name;
    TextView date;
    ImageButton btnChat, btnMap;// 채팅버튼
    EditText etShowPost; //댓글쓰기
    ImageButton btnShowPost; //댓글쓰기

    int heart = 10;

    private int isHeart = 0;   //false
    private ImageButton btnHeart;

    // 댓글작성자Id / 댓글Id / 글Id / 닉네임 / 댓글내용 / 작성시간
    private String writeId, postId, nickname, comment, createAt;

    private ArrayList<comment> item = new ArrayList<>();

    private LinearLayoutManager layoutManager;
    private commentAdapter adapter;

    private DatabaseReference databaseRef = FirebaseDatabase.getInstance().getReference();
    DatabaseReference userRef, postUserRef;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        setHasOptionsMenu(true);
        View v = inflater.inflate(R.layout.show_post, container, false);

        //boardAdapter->MainActivity->(Bundle)->here
        Bundle bundle = getArguments();
        postUid = bundle.getString("Uid");
        postToken = bundle.getString("postToken");
        heart = bundle.getInt("sticker");

        Log.e("토큰", postToken);
        Log.e("id", postUid);
        Log.e("sticker", String.valueOf(heart));


        // database 연결
        DatabaseReference postRef = databaseRef.child("grapeMate/post").child(postToken);
        DatabaseReference commentRef = FirebaseDatabase.getInstance().getReference("grapeMate/comment");


        // 현재 로그인한 사용자 불러오기
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        userRef = databaseRef.child("grapeMate/UserAccount").child(user.getUid());
        postUserRef = databaseRef.child("grapeMate/UserAccount").child(postUid);


        Log.d("체크", postToken);

        category = v.findViewById(R.id.show_post_category);
        title = v.findViewById(R.id.show_post_title);
        content = v.findViewById(R.id.show_post_content);
        name = v.findViewById(R.id.show_post_name);
        date = v.findViewById(R.id.show_post_date);

        // 글쓴이 하트 수 높이기
        postUserRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                // 유저 하트 수 바꾸기
                if (snapshot.child("sticker").exists()) {
                    Log.e("print", "글쓴이 하트 개수 바꾸기");
                    heart = Integer.parseInt(String.valueOf(snapshot.child("sticker").getValue()));
                    Log.e("print", String.valueOf(heart));
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(getContext(), "하트 개수 불러오기 실패", Toast.LENGTH_SHORT).show();
            }
        });
        commentRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                // postToken과 comment child의 postId 가 같은 댓글만 표시하는거임
                loadBoardList(snapshot);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        postRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                name.setText(String.valueOf(snapshot.child("nickname").getValue()));
                title.setText(String.valueOf(snapshot.child("title").getValue()));
                content.setText(String.valueOf(snapshot.child("postContent").getValue()));
                category.setText(String.valueOf(snapshot.child("postType").getValue()));
                date.setText(String.valueOf(snapshot.child("endDay").getValue()) + "까지");

                // 135~144아님
                // 로그인한 사용자가 좋아요한 이력이 있는지 확인
                if (snapshot.child(user.getUid()).exists()) {
                    isHeart = Integer.parseInt(String.valueOf(snapshot.child(user.getUid()).getValue()));
                    Log.e("isHeart", String.valueOf(isHeart));
                    if (isHeart == 1) {
                        btnHeart.setImageResource(R.drawable.fullheart);
                    }
                } else {
                    postRef.child(user.getUid()).setValue(0);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });

        //댓글 리사이클러뷰
        RecyclerView recyclerView = v.findViewById(R.id.show_post_recycle);
        layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);


        adapter.items.add(new comment("commentId", "postId", "writeId", "이름1", "내용1", "createAt"));
        adapter.items.add(new comment("commentId", "postId", "writeId", "이름2", "내용2", "createAt"));

        adapter = new commentAdapter(item, getContext());
        recyclerView.setAdapter(adapter);


        btnShowPost = v.findViewById(R.id.btn_show_post);
        // 댓글 저장
        btnShowPost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                // 로그인한 사용자 writeId, nickname 가져오기
                writeId = user.getUid();
                // postId
                postId = postToken;
                // 댓글 내용
                etShowPost = v.findViewById(R.id.et_show_post);
                comment = etShowPost.getText().toString();
                // 작성날짜
                Date todayDate = Calendar.getInstance().getTime();
                SimpleDateFormat formatter = new SimpleDateFormat("yyyy/MM/dd");
                createAt = formatter.format(todayDate);

                saveComment(postId, writeId, comment, createAt);
            }
        });

        //heart, map, chat
        btnHeart = v.findViewById(R.id.btn_heart);
        btnMap = v.findViewById(R.id.btn_map);
        btnChat = v.findViewById(R.id.btn_chat);

        btnHeart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {



                if (isHeart == 1) {
                    //좋아요 취소
                    isHeart = 0;
                    btnHeart.setImageResource(R.drawable.heart4);
                    // 먼저 s 값을 불러와야함
                    Log.e("좋아요 취소", String.valueOf(heart));

                    postRef.child(user.getUid()).setValue(0);
                    //postUserRef.child("sticker").setValue(heart);       //여기문제

                    heart -= 1;

                    //saveHeart();

                    Log.e("좋아요 취소 완료", String.valueOf(heart));
                } else {
                    // 좋아요
                    isHeart= 1;
                    btnHeart.setImageResource(R.drawable.fullheart);
                    Log.e("좋아요", String.valueOf(heart));
                    postRef.child(user.getUid()).setValue(1);
                    //postUserRef.child("sticker").setValue(heart);
                    heart +=1;

                    //saveHeart();
                    Log.e("좋아요 완료", String.valueOf(heart));
                }
            }
        });

        //채팅으로
        btnChat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((MainActivity) getActivity()).toChatting(name.getText().toString());
            }
        });

        //왼쪽 화살표 버튼
        back = v.findViewById(R.id.btn_back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((MainActivity) getActivity()).toMain();
            }
        });

        return v;
    }

    private void saveHeart() {
        Map<String, Object> map = new HashMap<>();
        map.put("sticker", heart);
        postUserRef.updateChildren(map);
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    private void loadBoardList(DataSnapshot dataSnapshot) {
        item.clear();

        for (DataSnapshot snapshot : dataSnapshot.getChildren()) {

            if (String.valueOf(snapshot.child("postId").getValue()).equals(postToken)) {
                String commentId = String.valueOf(snapshot.child("commentId").getValue());     // comment Id
                String postId = String.valueOf(snapshot.child("postId").getValue());  // 글 id
                String writeId = String.valueOf(snapshot.child("writeId").getValue());  // 댓글 단 사람 id
                String nickname = String.valueOf(snapshot.child("nickname").getValue());    // name
                String content = String.valueOf(snapshot.child("content").getValue()); // content
                String createAt = String.valueOf(snapshot.child("createAt").getValue());    // 댓글 작성시간
                comment c = new comment(commentId, postId, writeId, nickname, content, createAt);

                item.add(c);
            }
        }

        adapter.notifyDataSetChanged(); // 변경사항 나타내기
    }

    // 데이터베이스에 댓글 저장하는 함수
    public void saveComment(String postId, String writeId, String content, String createAt) {
        // 키값을 임의의 문자열로 지정하고 싶으면 push() 사용
        String key = databaseRef.child("grapeMate/comment").push().getKey();
        userRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                nickname = String.valueOf(snapshot.child("nickname").getValue());
                comment c = new comment(key, postId, writeId, nickname, content, createAt);
                etShowPost.setText("");
                databaseRef.child("grapeMate/comment").child(key).setValue(c);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(getContext(), "댓글 입력에 실패했습니다.", Toast.LENGTH_SHORT).show();
            }
        });

    }

}
