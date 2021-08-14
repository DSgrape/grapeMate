package com.example.grape;

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
    private boardAdapter adapter_homecoming, adapter_exercise, adapter_food, adapter_study, adapter_etc;    // 카테고리별 어댑터 생성
    private FloatingActionButton fab;

    RecyclerView recyclerView;

    private ArrayList<board> item = new ArrayList<>();
    private ArrayList<board> item_homecoming = new ArrayList<>();
    private ArrayList<board> item_exercise = new ArrayList<>();
    private ArrayList<board> item_food = new ArrayList<>();
    private ArrayList<board> item_study = new ArrayList<>();
    private ArrayList<board> item_etc = new ArrayList<>();

    private Button btnHomeComing, btnExercise, btnFood, btnStudy, btnEtc;
    private int state = 0;  //카테고리별로 1,2,3,4,5 기본 0

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        setHasOptionsMenu(true);
        View v = inflater.inflate(R.layout.main_fragment, container, false);

        fab = v.findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((MainActivity) getActivity()).AddPost();
            }
        });

        recyclerView = v.findViewById(R.id.recycle_main);

        layoutManager = new LinearLayoutManager(getContext());
        layoutManager.setReverseLayout(true);
        layoutManager.setStackFromEnd(true);
        recyclerView.setLayoutManager(layoutManager);

        btnHomeComing = v.findViewById(R.id.btn_homecoming);
        btnExercise = v.findViewById(R.id.btn_exercise);
        btnFood = v.findViewById(R.id.btn_food);
        btnStudy = v.findViewById(R.id.btn_study);
        btnEtc = v.findViewById(R.id.btn_etc);

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
                if (state == 0) {
                    loadBoardList(snapshot);
                } else {
                    loadBoardList(snapshot);
                    loadSpecificBoardList();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                // 취소
                Log.e("print", "메인화면오류");
            }
        });

        // 데이터 변경
        // 데이터 삭제

        adapter = new boardAdapter(item, getContext());
        recyclerView.setAdapter(adapter);

        // 귀가
        btnHomeComing.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                state = 1;
                loadSpecificBoardList();
            }
        });
        // 운동
        btnExercise.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                state = 2;
                loadSpecificBoardList();
            }
        });
        // 음식
        btnFood.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                state = 3;
                loadSpecificBoardList();
            }
        });
        // 공부
        btnStudy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                state = 4;
                loadSpecificBoardList();
            }
        });
        // 기타
        btnEtc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                state = 5;
                loadSpecificBoardList();
            }
        });
        return v;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    // 특정 카테고리 선택
    private void loadSpecificBoardList() {

        switch (state) {
            case 1:
                item_homecoming.clear();
                for (board b : item) {
                    if (b.getPostType().equals("귀가")) {
                        item_homecoming.add(b);
                        if (adapter_homecoming == null) {
                            adapter_homecoming = new boardAdapter(item_homecoming, getContext());
                        } else {
                            adapter_homecoming.setItems(item_homecoming);
                        }
                    }
                }
                recyclerView.setAdapter(adapter_homecoming);
                if(adapter_study != null)
                    adapter_homecoming.notifyDataSetChanged(); // 변경사항 나타내기
                break;
            case 2:
                item_exercise.clear();
                for (board b : item) {
                    if (b.getPostType().equals("운동")) {
                        item_exercise.add(b);
                        if (adapter_exercise == null) {
                            adapter_exercise = new boardAdapter(item_exercise, getContext());
                        } else {
                            adapter_exercise.setItems(item_exercise);
                        }
                    }
                }
                recyclerView.setAdapter(adapter_exercise);
                if(adapter_study != null)
                    adapter_exercise.notifyDataSetChanged(); // 변경사항 나타내기
                break;
            case 3:
                item_food.clear();
                for (board b : item) {
                    if (b.getPostType().equals("음식")) {
                        item_food.add(b);
                        if (adapter_food == null) {
                            adapter_food = new boardAdapter(item_food, getContext());
                        } else {
                            adapter_food.setItems(item_food);
                        }

                    }
                }
                recyclerView.setAdapter(adapter_food);
                if(adapter_study != null)
                    adapter_food.notifyDataSetChanged(); // 변경사항 나타내기
                break;
            case 4:

                item_study.clear();
                for (board b : item) {
                    if (b.getPostType().equals("공부")) {
                        item_study.add(b);
                        if (adapter_study == null) {
                            adapter_study = new boardAdapter(item_study, getContext());
                        } else {
                            adapter_study.setItems(item_study);
                        }
                    }
                }
                recyclerView.setAdapter(adapter_study);
                if(adapter_study != null)
                    adapter_study.notifyDataSetChanged(); // 변경사항 나타내기

                break;
            case 5:
                item_etc.clear();
                for (board b : item) {

                    if (b.getPostType().equals("기타")) {
                        Log.e("테스트", b.getPostId());
                        item_etc.add(b);
                        if (adapter_etc == null) {
                            adapter_etc = new boardAdapter(item_etc, getContext());
                        } else {
                            adapter_etc.setItems(item_etc);
                        }
                    }
                }
                recyclerView.setAdapter(adapter_etc);
                if(adapter_study != null)
                    adapter_etc.notifyDataSetChanged(); // 변경사항 나타내기
                break;
        }

    }

    private void loadBoardList(DataSnapshot dataSnapshot) {
        item.clear();

        for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
            board b = snapshot.getValue(board.class);
            Log.e("key", b.getPostId());
            item.add(b);
        }
        recyclerView.setAdapter(adapter);

        adapter.notifyDataSetChanged(); // 변경사항 나타내기
    }
}
