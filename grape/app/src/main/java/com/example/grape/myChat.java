package com.example.grape;

import android.os.Bundle;
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

public class myChat extends Fragment {
    ImageButton back;
    private RecyclerView recyclerView;
    private LinearLayoutManager layoutManager;
    private chatListAdapter adapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        setHasOptionsMenu(true);
        View v = inflater.inflate(R.layout.chating_list, container, false);

        back=v.findViewById(R.id.back_myChat);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((MainActivity)getActivity()).toMyPage();
            }
        });

        recyclerView = v.findViewById(R.id.recycle_chattingRoom);
        layoutManager = new LinearLayoutManager(getContext());
        layoutManager.setReverseLayout(true);
        layoutManager.setStackFromEnd(true);
        recyclerView.setLayoutManager(layoutManager);


        adapter = new chatListAdapter();
        adapter.items.add(new ChattingRoom("x","이름"));
        recyclerView.setAdapter(adapter);

        return v;
    }
}