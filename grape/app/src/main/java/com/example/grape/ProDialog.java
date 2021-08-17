package com.example.grape;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.widget.TextView;

import androidx.annotation.NonNull;



public class ProDialog extends Dialog {
    private Context context;
    private de.hdodenhof.circleimageview.CircleImageView iv_profile;
    private TextView name_profile, school_profile, heart_profile;

    public ProDialog(@NonNull Context context){
        super(context);
        this.context=context;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.profile_dialog);

        //각 각 넣기
        iv_profile=findViewById(R.id.iv_profile);
        name_profile=findViewById(R.id.name_profile);
        school_profile=findViewById(R.id.school_profile);
        heart_profile=findViewById(R.id.num_profile);

    }
}
