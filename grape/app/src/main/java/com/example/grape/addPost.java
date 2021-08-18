package com.example.grape;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ServerValue;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class addPost extends Fragment {
    private Button btnOk;
    private Button btnCancel;
    private Spinner spin;
    private CalendarView cal;
    private String postType;
    private EditText title;
    private EditText content;
    private String date;
    private String emailId;
    private String Uid;
    private String nickname;
    private Button btnLocation;
    private double mapX = 37.6512;
    private double mapY = 127.0161;

    private DatabaseReference databaseRef = FirebaseDatabase.getInstance().getReference();




    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        setHasOptionsMenu(true);

        View v = inflater.inflate(R.layout.add_post, container, false);

        title = v.findViewById(R.id.et_title);
        content = v.findViewById(R.id.et_content);

        // 스피너
        spin = v.findViewById(R.id.spin);
        String[] category={ "귀가", "거래", "월경", "운동", "생활" };

        ArrayAdapter adapter= new ArrayAdapter(getContext(),R.layout.spinner_item,category);
        spin.setAdapter(adapter);

        spin.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    switch (position){
                        case 0: postType = category[0]; break;  // 귀가
                        case 1: postType = category[1]; break;  // 운동
                        case 2: postType = category[2]; break;  // 음식
                        case 3: postType = category[3]; break;  // 공부
                        case 4: postType = category[4]; break;  // 생활
                        default: postType = "오류"; break;
                }
            }

            // 기본 - 선택된 것이 없을 때
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                spin.setSelection(0);
                postType = category[0];
            }

        });

        btnLocation = v.findViewById(R.id.btn_setLocation);
        btnLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MapDialog mapDialog = new MapDialog(getContext(), (MainActivity)getActivity(), new MapDialogClickListener() {
                    @Override
                    public void onPositiveClick() {
                        //위치 넘기기
                        btnLocation.setText("위치 설정 완료");
                        Toast.makeText(getContext(),"위치가 설정되었습니다.",Toast.LENGTH_SHORT).show();
                    }
                    @Override
                    public void sendLocation(double x, double y) {
                        mapX = x;
                        mapY = y;
                        Log.e("mapX mapY", String.valueOf(mapX));
                    }

                    @Override
                    public double[] provideLocation() {
                        return new double[0];
                    }


                    @Override
                    public void onNegativeClick() {
                        Toast.makeText(getContext(),"위치 설정이 취소되었습니다.",Toast.LENGTH_SHORT).show();
                    }
                });
                mapDialog.setCanceledOnTouchOutside(false);//다이얼로그 외부 터치시 꺼짐
                mapDialog.setCancelable(true);//뒤로가기 버튼으로 취소
                mapDialog.show();
            }
        });

        // 마감기한 선택 - calendarView
        cal = v.findViewById(R.id.cal);
        cal.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView view, int year, int month, int dayOfMonth) {
                date = Integer.toString(year)+"/"+Integer.toString(month+1)+"/"+Integer.toString(dayOfMonth);//마감날짜 슬래쉬 -로 변경가능
                Log.d("체크",date);
            }
        });

        // 취소버튼->메인으로
        btnCancel = v.findViewById(R.id.btn_cen);
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getContext(),"글쓰기를 취소했습니다.",Toast.LENGTH_SHORT).show();
                ((MainActivity)getActivity()).toMain();
            }
        });

        // 확인버튼
        btnOk=v.findViewById(R.id.btn_ok);
        btnOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(title.getText().toString().equals("")) {
                    Toast.makeText(getContext(),"제목을 입력해주세요.",Toast.LENGTH_SHORT).show();
                } else if(content.getText().toString().equals("")) {
                    Toast.makeText(getContext(),"내용을 입력해주세요.",Toast.LENGTH_SHORT).show();
                } else if(date == null) {
                    Toast.makeText(getContext(),"날짜를 선택해주세요.",Toast.LENGTH_SHORT).show();
                } else {
                    //db에 저장
                    Log.d("체크", postType);
                    Log.d("체크", title.getText().toString());
                    Log.d("체크", content.getText().toString());
                    Log.d("체크", date);

                    // Database
                    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

                    // Uid 가져오기 -> board id
                    DatabaseReference ref = databaseRef.child("grapeMate/UserAccount").child(user.getUid());
                    ref.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            // emailId -> writeId
                            if(snapshot.child("emailId").exists()) {
                                emailId = String.valueOf(snapshot.child("emailId").getValue());
                            }
                            //nickname
                            if(snapshot.child("nickname").exists()) {
                                nickname = String.valueOf(snapshot.child("nickname").getValue());
                            }
                            Uid = user.getUid();

                            if (title.getText().toString() == "") {
                                Toast.makeText(getContext(), "제목을 입력하세요", Toast.LENGTH_SHORT).show();
                            } else if (content.getText().toString() == ""){
                                Toast.makeText(getContext(), "내용을 입력하세요", Toast.LENGTH_SHORT).show();
                            } else if(Uid != null && emailId != null) {
                                // 글을 정상적으로 작성했을 때
                                Date todayDate = Calendar.getInstance().getTime();
                                SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
                                String todayString = formatter.format(todayDate);
                                Log.e("print", todayString);

                                savePost(Uid, emailId, nickname, postType, title.getText().toString(), content.getText().toString(), date, todayString, mapX, mapY);
                                title.setText("");
                                content.setText("");
                                //메인으로 이동
                                ((MainActivity)getActivity()).toMain();
                            }
                        }
                        @Override
                        public void onCancelled(@NonNull DatabaseError error) { }
                    });
                }
            }
        });

        return v;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    // 데이터베이스에 글 저장하는 함수
    public void savePost(String Uid, String emailId, String nickname, String postType, String title, String content, String date, String todayString, double mapX, double mapY) {
        // 키값을 임의의 문자열로 지정하고 싶으면 push() 사용
        String key = databaseRef.child("grapeMate/post").push().getKey();
        Log.e("mapx", String.valueOf(mapX));
        board b = new board(key, Uid, emailId, nickname, postType, title, content, date, todayString, mapX, mapY);
        databaseRef.child("grapeMate/post").child(key).setValue(b);
    }

}
