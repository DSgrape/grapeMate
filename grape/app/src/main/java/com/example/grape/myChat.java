package com.example.grape;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class myChat extends Fragment {
    ImageButton back;
    private RecyclerView recyclerView;
    private LinearLayoutManager layoutManager;
    private chatListAdapter adapter;

    private DatabaseReference databaseRef = FirebaseDatabase.getInstance().getReference("grapeMate/chat");
    private ArrayList<ChattingRoom> chattingRooms = new ArrayList<>();
    String chatRoomUid, destinationUid, postId;
    String title, category, nickname;

    String uid = FirebaseAuth.getInstance().getCurrentUser().getUid();

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        setHasOptionsMenu(true);
        View v = inflater.inflate(R.layout.chating_list, container, false);

        back = v.findViewById(R.id.back_myChat);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((MainActivity) getActivity()).toMyPage();
            }
        });

        recyclerView = v.findViewById(R.id.recycle_chattingRoom);
        layoutManager = new LinearLayoutManager(getContext());
        layoutManager.setReverseLayout(true);
        layoutManager.setStackFromEnd(true);
        recyclerView.setLayoutManager(layoutManager);

        // 채팅 상대
        Map<String, Boolean> user1 = new HashMap<>();
        Map<String, Boolean> user2 = new HashMap<>();
        user1.put("유저1", true);
        user2.put("유저1", true);
        adapter.items.add(new ChattingRoom("chatKey", "postId", new ChattingRoom.Users("uid", "destinationUid"), "nickname", "category", "title"));

        // 리사이클러뷰
        uid = FirebaseAuth.getInstance().getCurrentUser().getUid();
        // 1. 내가 시작한 채팅 -> 글쓴이가 내가 아님
        // 2. 남이 나한테 건 채팅 -> 글쓴이가 나야, 그사람이 건 거야

        checkChatRoom();


        return v;
    }

    void checkChatRoom() {


        databaseRef.child("chatroom").addValueEventListener(new ValueEventListener() {

            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                chattingRooms.clear();


                for (DataSnapshot item : snapshot.getChildren()) {
                    postId = item.child("postId").getValue().toString();
                    chatRoomUid = item.getKey();

                    @NonNull DataSnapshot dataSnapshot = snapshot.child(chatRoomUid).child("users");

                    if (dataSnapshot.child("uid").getValue().toString().equals(uid) || dataSnapshot.child("destinationUid").getValue().toString().equals(uid)) {

                        if (dataSnapshot.child("destinationUid").getValue().toString().equals(uid)) {
                            // 내가 만든 채팅방이 아닌 경우 -> 주객전도해야함
                            destinationUid = dataSnapshot.child("uid").getValue().toString();
                        } else {
                            // 내가 만든 채팅방인 경우
                            destinationUid = dataSnapshot.child("destinationUid").getValue().toString();
                        }

                    }

                    if (destinationUid != null) {

                        ChattingRoom chattingRoom = new ChattingRoom(chatRoomUid, postId, new ChattingRoom.Users(uid, destinationUid));

                        chattingRooms.add(chattingRoom);
                        Log.e("1", chatRoomUid+ postId);
                        destinationUid = null;
                    }

                    adapter = new chatListAdapter(chattingRooms, getContext());
                    recyclerView.setAdapter(adapter);
                    adapter.notifyDataSetChanged(); // 변경사항 나타내기

                }


            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }
}
