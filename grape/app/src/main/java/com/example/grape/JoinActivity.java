package com.example.grape;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
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
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class JoinActivity extends AppCompatActivity {

    private FirebaseAuth mfbAuth;     //파이어베이스 인증 처리
    private DatabaseReference databaseRef;  //실시간 데이터베이스
    private EditText etEmail, etPwd, etNickname, etPhoneNumber;
    private Button btnRegister;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.join);

        new hideNavigationBar(getWindow().getDecorView());

        FirebaseApp.initializeApp(this);

        etEmail = findViewById(R.id.et_email);
        etPwd = findViewById(R.id.et_pwd);
        etNickname = findViewById(R.id.et_nickname);
        etPhoneNumber = findViewById(R.id.et_phone_number);

        btnRegister = findViewById(R.id.btn_join);

        mfbAuth = FirebaseAuth.getInstance();
        databaseRef = FirebaseDatabase.getInstance().getReference("grapeMate");

        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // 회원가입
                String strEmail =etEmail.getText().toString();
                String strPwd = etPwd.getText().toString();
                String strNickname = etNickname.getText().toString();
                String strPhoneNumber = etPhoneNumber.getText().toString();
                //firebaseAuth를 통해 유저 생성
                // 이메일, 비밀번호
                mfbAuth.createUserWithEmailAndPassword(strEmail,strPwd)
                        .addOnCompleteListener(JoinActivity.this , new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                // 결과 값 task

                                if (task.isSuccessful()) {
                                    // 로그인 성공한 객체를 firebaseUser에 넣음

                                    FirebaseUser firebaseUser = mfbAuth.getCurrentUser();
                                    Log.e("print", firebaseUser!=null?"불러옴":"못불러옴");

                                    UserAccount account = new UserAccount();
                                    // account에 토큰, 이메일, 비밀번호, 등급, 닉네임, 위치, 전화번호 넣음
                                    /** 잊지말고 사진을 넣읍시다 **/
                                    account.setIdToken(firebaseUser.getUid());  //Uid : 로그인하면 나오는 거
                                    account.setEmailId(firebaseUser.getEmail());
                                    account.setPassword(strPwd);
                                    account.setGrade(1);
                                    account.setNickname(strNickname);
                                    account.setPhoneNumber(strPhoneNumber);
                                    account.setLocation("기본선호위치");
                                    Log.e("print", "정보 저장 성공");

                                    //grapeMate 하위에 넣는다는 뜻
                                    // setValue : database에 insert
                                    databaseRef.child("UserAccount").child(firebaseUser.getUid()).setValue(account);

                                    Toast.makeText(JoinActivity.this, "회원가입에 성공하였습니다.", Toast.LENGTH_SHORT).show();
                                } else {
                                    Toast.makeText(JoinActivity.this, "회원가입에 실패하였습니다.", Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
            }
        });

    }
}
