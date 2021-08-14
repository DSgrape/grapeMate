package com.example.grape;

import android.media.Image;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class myPost extends Fragment {
    private ImageButton back;
    private RecyclerView recyclerView;
    private LinearLayoutManager layoutManager;
    private boardAdapter adapter;
    private ArrayList<board> item = new ArrayList<>();

    private DatabaseReference databaseRef;
    private FirebaseUser user;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        setHasOptionsMenu(true);
        View v = inflater.inflate(R.layout.my_post, container, false);

        back = v.findViewById(R.id.back_myPost);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((MainActivity)getActivity()).toMyPage();
            }
        });

        recyclerView = v.findViewById(R.id.recycle_main);

        layoutManager = new LinearLayoutManager(getContext());
        layoutManager.setReverseLayout(true);
        layoutManager.setStackFromEnd(true);
        recyclerView.setLayoutManager(layoutManager);

        databaseRef = FirebaseDatabase.getInstance().getReference("grapeMate/post");
        user = FirebaseAuth.getInstance().getCurrentUser();

        databaseRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                loadBoardList(snapshot);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        adapter = new boardAdapter(item, getContext());
        recyclerView.setAdapter(adapter);

        return v;
    }

    private void loadBoardList(DataSnapshot dataSnapshot) {
        item.clear();

        for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
            if(String.valueOf(snapshot.child("id").getValue()).equals(user.getUid())) {
                board b = snapshot.getValue(board.class);
                Log.e("key", b.getPostId());
                item.add(b);
            }

        }
        recyclerView.setAdapter(adapter);

        adapter.notifyDataSetChanged(); // 변경사항 나타내기
    }
}