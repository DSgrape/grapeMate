package com.example.grape;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class showSticker extends Fragment {
    // 스티커 보여줌

    private ImageView stickerPan;
    private TextView stickerNumber;
    private int heartCount = 0;
    private ImageButton back;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        setHasOptionsMenu(true);
        View v = inflater.inflate(R.layout.show_sticker, container, false);

        //뒤로가기
        back=v.findViewById(R.id.back_sticker);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((MainActivity)getActivity()).toMyPage();
            }
        });


        stickerPan = v.findViewById(R.id.sticker_pan);
        stickerNumber = v.findViewById(R.id.sticker_num);

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        DatabaseReference mref = FirebaseDatabase.getInstance().getReference();
        DatabaseReference ref = mref.child("grapeMate/UserAccount").child(user.getUid());

        // 스티커 사진, 개수 조절
        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                if (snapshot.child("sticker").exists()) {
                    heartCount = Integer.parseInt(String.valueOf(snapshot.child("sticker").getValue()));
                }
                if (heartCount >= 0 && heartCount < 10) {
                    stickerPan.setImageResource(R.drawable.gae0);
                    stickerNumber.setText(heartCount+"개");
                } else if (heartCount >= 10 && heartCount < 20) {
                    stickerPan.setImageResource(R.drawable.gae1);
                    stickerNumber.setText(heartCount+"개");
                } else if (heartCount >= 20 && heartCount < 30) {
                    stickerPan.setImageResource(R.drawable.gae2);
                    stickerNumber.setText(heartCount+"개");
                } else if (heartCount >= 30 & heartCount < 40) {
                    stickerPan.setImageResource(R.drawable.gae3);
                    stickerNumber.setText(heartCount+"개");
                } else if (heartCount >= 40 && heartCount < 50) {
                    stickerPan.setImageResource(R.drawable.gae4);
                    stickerNumber.setText(heartCount+"개");
                } else if (heartCount >= 50 && heartCount < 60) {
                    stickerPan.setImageResource(R.drawable.gae5);
                    stickerNumber.setText(heartCount+"개");
                } else if (heartCount >= 60) {
                    stickerPan.setImageResource(R.drawable.gae6);
                    stickerNumber.setText(heartCount+"개");
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });



        return v;
    }
}
