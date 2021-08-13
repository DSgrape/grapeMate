package com.example.grape;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
    private int stickerCount = 0;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        setHasOptionsMenu(true);
        View v = inflater.inflate(R.layout.show_post, container, false);

        stickerPan = v.findViewById(R.id.sticker_pan);
        stickerNumber = v.findViewById(R.id.sticker_num);

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        DatabaseReference mref = FirebaseDatabase.getInstance().getReference();
        DatabaseReference ref = mref.child("grapeMate/UserAccount").child(user.getUid());

        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                if(snapshot.child("sticker").exists()) {
                    stickerCount = Integer.parseInt(String.valueOf(snapshot.child("sticker").getValue()));
                }
                switch(stickerCount) {
                    case 0:
                        stickerPan.setImageResource(R.drawable.gae0);
                        stickerNumber.setText("0개");
                        break;
                    case 1:
                        stickerPan.setImageResource(R.drawable.gae1);
                        stickerNumber.setText("1개");
                        break;
                    case 2:
                        stickerPan.setImageResource(R.drawable.gae2);
                        stickerNumber.setText("2개");
                        break;
                    case 3:
                        stickerPan.setImageResource(R.drawable.gae3);
                        stickerNumber.setText("3개");
                        break;
                    case 4:
                        stickerPan.setImageResource(R.drawable.gae4);
                        stickerNumber.setText("4개");
                        break;
                    case 5:
                        stickerPan.setImageResource(R.drawable.gae5);
                        stickerNumber.setText("5개");
                        break;
                    //case 6: stickerPan.setImageResource(R.drawable.);
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });



        return v;
    }
}
