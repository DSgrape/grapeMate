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

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class showPost extends Fragment {
    ImageButton back;
    String id;
    TextView category;
    TextView title;
    TextView content;
    TextView name;
    TextView date;
    Button chating;//체팅버튼
    EditText et_showPost;//댓글쓰기
    Button btn_showPost;//댓글쓰기
    private LinearLayoutManager layoutManager;
    private commentAdapter adapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        setHasOptionsMenu(true);
        View v = inflater.inflate(R.layout.show_post, container, false);

        //bardAdapter->MainActivity->(Bundle)->here
        Bundle bundle=getArguments();
        id=bundle.getString("id");
        Log.d("체크",id);
        //아이디로 글 정보 불러올 수 있나요?
        category=v.findViewById(R.id.show_post_category);
        title=v.findViewById(R.id.show_post_title);
        content=v.findViewById(R.id.show_post_content);
        name=v.findViewById(R.id.show_post_name);
        date=v.findViewById(R.id.show_post_date);

        //댓글 리사이클러뷰
        RecyclerView recyclerView=v.findViewById(R.id.show_post_recycle);
        layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);
        adapter=new commentAdapter();
        adapter.items.add(new comment("이름1","내용1"));
        adapter.items.add(new comment("이름2","내용2"));
        recyclerView.setAdapter(adapter);

        //왼쪽 화살표 버튼
        back=v.findViewById(R.id.btn_back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((MainActivity)getActivity()).toMain();
            }
        });

        return v;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

}
