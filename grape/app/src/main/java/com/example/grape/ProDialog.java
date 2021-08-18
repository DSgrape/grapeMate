package com.example.grape;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.google.firebase.database.DataSnapshot;
import com.squareup.picasso.Picasso;


public class ProDialog extends Dialog {
    private Context context;
    private de.hdodenhof.circleimageview.CircleImageView iv_profile;
    private TextView name_profile, school_profile, heart_profile;
    private String postUserProfile, postUserName, postUserSchool, heart;

    public ProDialog(@NonNull Context context) {
        super(context);
        this.context = context;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.profile_dialog);

        //각 각 넣기
        iv_profile = findViewById(R.id.iv_profile);
        name_profile = findViewById(R.id.name_profile);
        school_profile = findViewById(R.id.school_profile);
        heart_profile = findViewById(R.id.num_profile);

        if(postUserProfile != null) {
            Log.e("postuser", postUserProfile);

            //Picasso.get().load(postUserProfile).into(iv_profile);
            Picasso.get().load("https://firebasestorage.googleapis.com/v0/b/grape-3cabc.appspot.com/o/user.png?alt=media&token=4e337b01-bbc3-4c3a-9beb-657f2ff0fa2e).into(iv_profile");
        }

        name_profile.setText(postUserName);
        school_profile.setText(postUserSchool);
        heart_profile.setText(heart+"개");

    }

    public void setInfo(DataSnapshot snapshot) {
        // 프로필 사진
        postUserProfile = String.valueOf(snapshot.child("profile").getValue());
        postUserName = String.valueOf(snapshot.child("nickname").getValue());
        postUserSchool = String.valueOf(snapshot.child("school").getValue());
        heart = String.valueOf(snapshot.child("sticker").getValue());

    }
}
