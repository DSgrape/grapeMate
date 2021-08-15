package com.example.grape;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class Chatting extends Fragment {
    ImageButton back;
    ImageButton finish;
    TextView chatName;
    TextView title;
    TextView type;
    private RecyclerView recyclerView;
    private LinearLayoutManager layoutManager;
    private chatAdapter adapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        setHasOptionsMenu(true);
        View v = inflater.inflate(R.layout.chatting, container, false);

        //번들로 채팅 대상 이름받기
        Bundle bundle = getArguments();
        chatName=v.findViewById(R.id.ChatName);
        chatName.setText(bundle.getString("ChatName"));

        //카테고리, 제목
        type=v.findViewById(R.id.tv_chat_type);
        title=v.findViewById(R.id.tv_chat_title);


        //채팅 내용 리사이클러뷰
        recyclerView = v.findViewById(R.id.chatting_recycle);
        layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);

        adapter = new chatAdapter();
        adapter.items.add(new chat("내요용", 0));
        adapter.items.add(new chat("답벼여영ㄴ", 1));
        recyclerView.setAdapter(adapter);


        //메인으로
        back=v.findViewById(R.id.back_chatting);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((MainActivity)getActivity()).showMyChat();
            }
        });

        //체팅 나가기
        finish=v.findViewById(R.id.finish);
        finish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                builder.setMessage("채팅방을 나가시겠습니까?");
                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //체팅방 삭제 넣어야됨
                        Toast.makeText(getContext(), "삭제되었습니다.", Toast.LENGTH_SHORT).show();
                        ((MainActivity)getActivity()).showMyChat();
                    }
                });
                builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(getContext(), "취소되었습니다.", Toast.LENGTH_SHORT).show();
                    }
                });

                AlertDialog alertDialog=builder.create();
                alertDialog.show();

            }
        });

        return v;
    }
}