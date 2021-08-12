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

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class showPost extends Fragment {
    ImageButton back;
    String postToken;
    TextView category;
    TextView title;
    TextView content;
    TextView name;
    TextView date;
    Button chatting;// 채팅버튼
    EditText et_showPost; //댓글쓰기
    Button btn_showPost; //댓글쓰기
    private LinearLayoutManager layoutManager;
    private commentAdapter adapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        setHasOptionsMenu(true);
        View v = inflater.inflate(R.layout.show_post, container, false);

        //boardAdapter->MainActivity->(Bundle)->here
        Bundle bundle = getArguments();
        postToken = bundle.getString("postToken");
        Log.e("토큰", postToken);

        // 현재 로그인한 사용자 불러오기
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        DatabaseReference mref = FirebaseDatabase.getInstance().getReference();

        // database 연결
        DatabaseReference userRef = mref.child("grapeMate/UserAccount").child(user.getUid());

        DatabaseReference postRef = mref.child("grapeMate/post").child(postToken);


        Log.d("체크",postToken);

        category = v.findViewById(R.id.show_post_category);
        title = v.findViewById(R.id.show_post_title);
        content = v.findViewById(R.id.show_post_content);
        name = v.findViewById(R.id.show_post_name);
        date = v.findViewById(R.id.show_post_date);

        postRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                name.setText(String.valueOf(snapshot.child("nickname").getValue()));
                title.setText(String.valueOf(snapshot.child("title").getValue()));
                content.setText(String.valueOf(snapshot.child("postContent").getValue()));
                category.setText(String.valueOf(snapshot.child("postType").getValue()));
                date.setText(String.valueOf(snapshot.child("endDay").getValue())+"까지");

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        //댓글 리사이클러뷰
        RecyclerView recyclerView = v.findViewById(R.id.show_post_recycle);
        layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);
        adapter = new commentAdapter();
        adapter.items.add(new comment("이름1","내용1"));
        adapter.items.add(new comment("이름2","내용2"));
        recyclerView.setAdapter(adapter);

        //왼쪽 화살표 버튼
        back = v.findViewById(R.id.btn_back);
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
