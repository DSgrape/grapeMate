package com.example.grape;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
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
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.Map;

public class LoginActivity2  extends AppCompatActivity {

    private Button join;
    private FirebaseAuth mfbAuth;     //파이어베이스 인증 처리
    private DatabaseReference databaseRef;  //실시간 데이터베이스
    private EditText etEmail, etPwd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login2);

        new hideNavigationBar(getWindow().getDecorView());

        //스피너 하단바 오류개선
        View decorView = getWindow().getDecorView();
        decorView.setOnSystemUiVisibilityChangeListener(
                visibility -> decorView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_HIDE_NAVIGATION | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY)
        );

        join = findViewById(R.id.btn_join);
        join.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // login

                mfbAuth = FirebaseAuth.getInstance();
                databaseRef = FirebaseDatabase.getInstance().getReference("grapeMate");

                etEmail = findViewById(R.id.et_email);
                etPwd = findViewById(R.id.et_pwd);

                String strEmail = etEmail.getText().toString();
                String strPwd = etPwd.getText().toString();

                // 로그인 시도
                mfbAuth.signInWithEmailAndPassword(strEmail, strPwd)
                        .addOnCompleteListener(LoginActivity2.this, new OnCompleteListener<AuthResult>() {

                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if(task.isSuccessful()) {
                                    // 로그인 성공
                                    // grade가 2(정회원)일때만 메인 액티비티로 넘어가도록
                                    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

                                    DatabaseReference mref = FirebaseDatabase.getInstance().getReference();
                                    DatabaseReference ref = mref.child("grapeMate/UserAccount").child(user.getUid());

                                    ref.addValueEventListener(new ValueEventListener() {
                                        @Override
                                        public void onDataChange(@NonNull DataSnapshot snapshot) { ;
                                            if(snapshot.child("grade").exists()) {
                                                int grade = Integer.parseInt(String.valueOf(snapshot.child("grade").getValue()));


                                                if(grade == 2) {    // 정회원인지 확인
                                                    Intent intent = new Intent(LoginActivity2.this, MainActivity.class);
                                                    startActivity(intent);
                                                    finish(); // 다시 안 쓰니까 파괴
                                                } else {
                                                    Toast.makeText(getApplicationContext(), "정회원이 아닙니다.", Toast.LENGTH_SHORT).show();
                                                }
                                            }
                                        }
                                        @Override
                                        public void onCancelled(@NonNull DatabaseError error) { }
                                    });
                                } else {
                                    Toast.makeText(LoginActivity2.this, "로그인 실패", Toast.LENGTH_SHORT).show();
                                    Intent intent=new Intent(LoginActivity2.this, LoginActivity.class);
                                    startActivity(intent);
                                }
                            }
                        });
            }
        });


    }

    //키보드 내리기
    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        hideKeyboard();
        return super.dispatchTouchEvent(ev);
    }

    private void hideKeyboard() {
        InputMethodManager imm = (InputMethodManager) getSystemService(Activity.INPUT_METHOD_SERVICE);
        //Find the currently focused view, so we can grab the correct window token from it.
        View view = getCurrentFocus();
        //If no view currently has focus, create a new one, just so we can grab a window token from it
        if (view == null) {
            view = new View(this);
        }
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }
}
