package com.example.grape;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class Mypage_Fragment extends Fragment {
    TextView post;
    TextView chat;
    TextView sticker;
    TextView profile;
    de.hdodenhof.circleimageview.CircleImageView iv_mypage;
    TextView tv_mypage;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        setHasOptionsMenu(true);
        View v= inflater.inflate(R.layout.mypage_fragment,container,false);

        iv_mypage=v.findViewById(R.id.iv_mypage);//사진 넣어주세요
        tv_mypage=v.findViewById(R.id.tv_mypage);//닉네임 넣어주세요

        //내가 쓴 글
        post=v.findViewById(R.id.myPost);
        post.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((MainActivity)getActivity()).showMyPost();
            }
        });

        //채팅
        chat=v.findViewById(R.id.myChat);
        chat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((MainActivity)getActivity()).showMyChat();
            }
        });

        //내 스티커 보기
        sticker=v.findViewById(R.id.mySticker);
        sticker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((MainActivity)getActivity()).showMySticker();
            }
        });

        //개인 정보 수정
        profile=v.findViewById(R.id.myProfile);
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
