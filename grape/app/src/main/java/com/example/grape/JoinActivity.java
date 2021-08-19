package com.example.grape;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListPopupWindow;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.viewpager.widget.ViewPager;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.File;
import java.lang.reflect.Field;
import java.util.Arrays;

import static java.security.AccessController.getContext;

public class JoinActivity extends AppCompatActivity {

    private FirebaseAuth mfbAuth;     //파이어베이스 인증 처리
    private DatabaseReference databaseRef;  //실시간 데이터베이스
    private EditText etEmail, etPwd, etNickname, etPhoneNumber;
    private Button btnRegister;
    private ImageButton btnPhoto;
    private String imgUrl = "";
    private Spinner spin;
    String mySchool = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.join);

        new hideNavigationBar(getWindow().getDecorView());

        //스피너 하단바 오류개선
        View decorView = getWindow().getDecorView();
        decorView.setOnSystemUiVisibilityChangeListener(
                visibility -> decorView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_HIDE_NAVIGATION | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY)
        );

        FirebaseApp.initializeApp(this);

        etEmail = findViewById(R.id.et_email);
        etPwd = findViewById(R.id.et_pwd);
        etNickname = findViewById(R.id.et_nickname);
        etPhoneNumber = findViewById(R.id.et_phone_number);

        btnRegister = findViewById(R.id.btn_join);
        btnPhoto = findViewById(R.id.btn_photo);

        mfbAuth = FirebaseAuth.getInstance();
        databaseRef = FirebaseDatabase.getInstance().getReference("grapeMate");


        // 스피너
        spin = findViewById(R.id.info_spin);
        String[] school = { "덕성여자대학교", "동덕여자대학교", "서울여자대학교", "성신여자대학교", "숙명여자대학교", "이화여자대학교" };


        ArrayAdapter adapter= new ArrayAdapter(this,R.layout.spinner_item,school);
        spin.setAdapter(adapter);

        spin.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                switch (position){
                    case 0:
                        mySchool = school[0];
                        break;
                    case 1:
                        mySchool = school[1];
                        break;
                    case 2:
                        mySchool = school[2];
                        break;
                    case 3:
                        mySchool = school[3];
                        break;
                    case 4:
                        mySchool = school[4];
                        break;
                    case 5:
                        mySchool = school[5];
                        break;
                    default: break;
                }
            }

            // 기본 - 선택된 것이 없을 때
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                spin.setSelection(0);

            }

        });


        btnPhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // 사진 불러오기
                //https://crazykim2.tistory.com/487
                int writePermission = ContextCompat.checkSelfPermission(JoinActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE);
                int readPermission = ContextCompat.checkSelfPermission(JoinActivity.this, Manifest.permission.READ_EXTERNAL_STORAGE);

                if(writePermission == PackageManager.PERMISSION_GRANTED && readPermission == PackageManager.PERMISSION_GRANTED) {
                    // 권한 허용
                    Intent intent = new Intent(Intent.ACTION_PICK);
                    intent.setType("image/*");
                    startActivityForResult(intent, 102);
                } else {
                    requestPermissions(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE}, 1000);
                }

                // https://m.blog.naver.com/PostView.naver?isHttpsRedirect=true&blogId=zoomen1004&logNo=110183313246
                Log.e("print", "카메라 화면 이동");
            }
        });
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
                                    // account에 토큰, 이메일, 비밀번호, 등급, 닉네임, 학교,  넣음
                                    account.setIdToken(firebaseUser.getUid());  //Uid : 로그인하면 나오는 거
                                    account.setEmailId(firebaseUser.getEmail());
                                    account.setPassword(strPwd);
                                    account.setGrade(1);
                                    account.setNickname(strNickname);
                                    account.setPhoneNumber(strPhoneNumber);
                                    account.setStudentCardPhoto(imgUrl);
                                    account.setSchool(mySchool);
                                    // 기본 프로필 사진
                                    account.setProfile("https://firebasestorage.googleapis.com/v0/b/grape-3cabc.appspot.com/o/user.png?alt=media&token=4e337b01-bbc3-4c3a-9beb-657f2ff0fa2e");

                                    Log.e("print", "정보 저장 성공");

                                    //grapeMate 하위에 넣는다는 뜻
                                    // setValue : database에 insert
                                    databaseRef.child("UserAccount").child(firebaseUser.getUid()).setValue(account);

                                    Toast.makeText(JoinActivity.this, "회원가입에 성공하였습니다.", Toast.LENGTH_SHORT).show();
                                    Intent intent = new Intent(JoinActivity.this, LoginActivity.class);
                                    startActivity(intent);
                                } else {
                                    Toast.makeText(JoinActivity.this, "회원가입에 실패하였습니다.", Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
            }
        });

    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == 101) {

        } else if(requestCode == 102) {
            // 갤러리에서 사진 불러오기
            if(resultCode==Activity.RESULT_OK) {
                if(Build.VERSION.SDK_INT>=19) {
                    Uri imgUri = data.getData();


                    //https://trend21c.tistory.com/1468
                    String[] projection = {MediaStore.Images.Media.DATA};
                    Cursor cursor = managedQuery(imgUri, projection, null, null, null);
                    startManagingCursor(cursor);
                    int columnIndex = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
                    cursor.moveToFirst();

                    String imgPath = cursor.getString(columnIndex);
                    Log.e("print", imgPath);

                    if(imgPath!=null) {
                        // cloud storage에 올리기
                        FirebaseStorage storage = FirebaseStorage.getInstance();
                        StorageReference storageRef = storage.getReference();


                        Uri file = Uri.fromFile(new File(imgPath));
                        StorageReference photoRef = storageRef.child("images/"+file.getLastPathSegment());
                        UploadTask uploadTask = photoRef.putFile(file);


                        // Register observers to listen for when the download is done or if it fails
                        uploadTask.addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception exception) {
                                // Handle unsuccessful uploads
                            }
                        }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                            @Override
                            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                                // 스토리지 저장 성공
                                photoRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                    @Override
                                    public void onSuccess(Uri uri) {

                                        Log.e("print", uri.toString());
                                        imgUrl = uri.toString();
                                    }
                                });

                            }
                        });
                    }
                }
            }
        }
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

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType("image/*");
        startActivityForResult(intent, 102);

    }
}
