package com.example.grape;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;


public class Main_Fragment extends Fragment {
    private LinearLayoutManager LinearLayoutManager;
    private main_adapter main_adapter;
    private FloatingActionButton fab;
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

        RecyclerView recyclerView=v.findViewById(R.id.recycle_main);
        LinearLayoutManager=new LinearLayoutManager(getContext());
        LinearLayoutManager.setReverseLayout(true);
        LinearLayoutManager.setStackFromEnd(true);
        recyclerView.setLayoutManager(LinearLayoutManager);
        main_adapter=new main_adapter();
        main_adapter.items.add(new board("운동","운동하자"));
        main_adapter.items.add(new board("공부","공부하자"));
        main_adapter.items.add(new board("귀가","귀가하자"));
        recyclerView.setAdapter(main_adapter);

        return v;
    }
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }
}
