package com.example.grape;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class LoginActivity2  extends AppCompatActivity {

    private Button join;
    private FirebaseAuth mfbAuth;     //파이어베이스 인증 처리
    private DatabaseReference databaseRef;  //실시간 데이터베이스
    private EditText etEmail, etPwd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login2);

        join = findViewById(R.id.btn_join);
        join.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // login

                mfbAuth = FirebaseAuth.getInstance();
                databaseRef = FirebaseDatabase.getInstance().getReference("grapeMate");

                etEmail = findViewById(R.id.et_email);
                etPwd = findViewById(R.id.et_pwd);

                String strEmail =etEmail.getText().toString();
                String strPwd = etPwd.getText().toString();

                mfbAuth.signInWithEmailAndPassword(strEmail, strPwd)
                        .addOnCompleteListener(LoginActivity2.this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if(task.isSuccessful()) {
                                    // 로그인 성공
                                    Intent intent=new Intent(getApplicationContext(), MainActivity.class);
                                    startActivity(intent);
                                    finish(); // 다시 안 쓰니까 파괴
                                } else {
                                    Toast.makeText(LoginActivity2.this, "로그인 실패", Toast.LENGTH_SHORT).show();
                                }
                            }
                        });



            }
        });


    }
}
