package com.example.grape;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;


public class Main_Fragment extends Fragment {

    private FirebaseAuth mfbAuth;     //파이어베이스 인증 처리
    private DatabaseReference databaseRef;  //실시간 데이터베이스

    private LinearLayoutManager layoutManager;
    private boardAdapter adapter;
    private FloatingActionButton fab;
    private ArrayList<board> item = new ArrayList<>();
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        setHasOptionsMenu(true);
        View v= inflater.inflate(R.layout.main_fragment,container,false);

        fab=v.findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((MainActivity)getActivity()).AddPost();
            }
        });

        RecyclerView recyclerView = v.findViewById(R.id.recycle_main);

        layoutManager = new LinearLayoutManager(getContext());
        layoutManager.setReverseLayout(true);
        layoutManager.setStackFromEnd(true);
        recyclerView.setLayoutManager(layoutManager);


        // 임시 아이템 추가
        adapter.items.add(new board("key", "id", "writeId", "닉네임", "운동", "운동같이할사람", "운동 같이 하실래요?", 0,
                "endDay", "createAt"));

        databaseRef = FirebaseDatabase.getInstance().getReference("grapeMate/post");

        // 데이터 불러오기
        // https://firebase.google.com/docs/database/admin/retrieve-data?hl=ko
        databaseRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                // 데이터 변경
                Log.e("print", "동작중");
                loadBoardList(snapshot);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                // 취소
                Log.e("print", "글 쓰다 취소함");
            }
        });

        // 데이터 변경
        // 데이터 삭제

        adapter = new boardAdapter(item, getContext());
        recyclerView.setAdapter(adapter);

        return v;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    private void loadBoardList(DataSnapshot dataSnapshot) {
        item.clear();

        for(DataSnapshot snapshot : dataSnapshot.getChildren()){
            board b = snapshot.getValue(board.class);
            Log.e("key", b.getPid());
            item.add(b);
        }

        adapter.notifyDataSetChanged(); // 변경사항 나타내기
    }
}
