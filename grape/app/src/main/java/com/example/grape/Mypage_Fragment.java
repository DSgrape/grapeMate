package com.example.grape;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

public class Mypage_Fragment extends Fragment {
    TextView post;
    TextView chat;
    TextView sticker;
    TextView profile;
    de.hdodenhof.circleimageview.CircleImageView iv_mypage;
    TextView tv_mypage;

    private FirebaseAuth fbAuth;
    private DatabaseReference databaseRef;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        setHasOptionsMenu(true);
        View v= inflater.inflate(R.layout.mypage_fragment,container,false);

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        databaseRef = FirebaseDatabase.getInstance().getReference("grapeMate/UserAccount");
        DatabaseReference userRef = databaseRef.child(user.getUid());

        // profile, nickname
        iv_mypage = v.findViewById(R.id.iv_mypage);
        tv_mypage = v.findViewById(R.id.tv_mypage);

        userRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.child("profile").exists()) {
                    String prf = String.valueOf(snapshot.child("profile").getValue());
                    Picasso.get().load(prf).into(iv_mypage);
                }
                if(snapshot.child("nickname").exists()) {
                    tv_mypage.setText(String.valueOf(snapshot.child("nickname").getValue()));
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(getContext(), "프로필 사진 호출 실패", Toast.LENGTH_SHORT).show();
            }
        });


        //내가 쓴 글
        post = v.findViewById(R.id.myPost);
        post.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((MainActivity)getActivity()).showMyPost();
            }
        });

        //채팅
        chat = v.findViewById(R.id.myChat);
        chat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((MainActivity)getActivity()).showMyChat();
            }
        });

        //내 스티커 보기
        sticker = v.findViewById(R.id.mySticker);
        sticker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((MainActivity)getActivity()).showMySticker();
            }
        });

        //개인 정보 수정
        profile = v.findViewById(R.id.myProfile);
        profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((MainActivity)getActivity()).showMyProfile();
            }
        });

        return v;
    }
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }
}
