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

        // ?????????
        spin = v.findViewById(R.id.spin);
        String[] category={ "??????", "??????", "??????", "??????", "??????" };

        ArrayAdapter adapter= new ArrayAdapter(getContext(),R.layout.spinner_item,category);
        spin.setAdapter(adapter);

        spin.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    switch (position){
                        case 0: postType = category[0]; break;  // ??????
                        case 1: postType = category[1]; break;  // ??????
                        case 2: postType = category[2]; break;  // ??????
                        case 3: postType = category[3]; break;  // ??????
                        case 4: postType = category[4]; break;  // ??????
                        default: postType = "??????"; break;
                }
            }

            // ?????? - ????????? ?????? ?????? ???
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
                        //?????? ?????????
                        btnLocation.setText("?????? ?????? ??????");
                        Toast.makeText(getContext(),"????????? ?????????????????????.",Toast.LENGTH_SHORT).show();
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
                        Toast.makeText(getContext(),"?????? ????????? ?????????????????????.",Toast.LENGTH_SHORT).show();
                    }
                });
                mapDialog.setCanceledOnTouchOutside(false);//??????????????? ?????? ????????? ??????
                mapDialog.setCancelable(true);//???????????? ???????????? ??????
                mapDialog.show();
            }
        });

        // ???????????? ?????? - calendarView
        cal = v.findViewById(R.id.cal);
        cal.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView view, int year, int month, int dayOfMonth) {
                date = Integer.toString(year)+"/"+Integer.toString(month+1)+"/"+Integer.toString(dayOfMonth);//???????????? ????????? -??? ????????????
                Log.d("??????",date);
            }
        });

        // ????????????->????????????
        btnCancel = v.findViewById(R.id.btn_cen);
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getContext(),"???????????? ??????????????????.",Toast.LENGTH_SHORT).show();
                ((MainActivity)getActivity()).toMain();
            }
        });

        // ????????????
        btnOk=v.findViewById(R.id.btn_ok);
        btnOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(title.getText().toString().equals("")) {
                    Toast.makeText(getContext(),"????????? ??????????????????.",Toast.LENGTH_SHORT).show();
                } else if(content.getText().toString().equals("")) {
                    Toast.makeText(getContext(),"????????? ??????????????????.",Toast.LENGTH_SHORT).show();
                } else if(date == null) {
                    Toast.makeText(getContext(),"????????? ??????????????????.",Toast.LENGTH_SHORT).show();
                } else {
                    //db??? ??????
                    Log.d("??????", postType);
                    Log.d("??????", title.getText().toString());
                    Log.d("??????", content.getText().toString());
                    Log.d("??????", date);

                    // Database
                    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

                    // Uid ???????????? -> board id
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
                                Toast.makeText(getContext(), "????????? ???????????????", Toast.LENGTH_SHORT).show();
                            } else if (content.getText().toString() == ""){
                                Toast.makeText(getContext(), "????????? ???????????????", Toast.LENGTH_SHORT).show();
                            } else if(Uid != null && emailId != null && content != null) {
                                // ?????? ??????????????? ???????????? ???
                                Date todayDate = Calendar.getInstance().getTime();
                                SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
                                String todayString = formatter.format(todayDate);

                                savePost(Uid, emailId, nickname, postType, title.getText().toString(), content.getText().toString(), date, todayString, mapX, mapY);
                                title.setText("");
                                content.setText("");
                                //???????????? ??????
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

    // ????????????????????? ??? ???????????? ??????
    public void savePost(String Uid, String emailId, String nickname, String postType, String title, String content, String date, String todayString, double mapX, double mapY) {
        // ????????? ????????? ???????????? ???????????? ????????? push() ??????
        String key = databaseRef.child("grapeMate/post").push().getKey();
        Log.e("mapx", String.valueOf(mapX));
        board b = new board(key, Uid, emailId, nickname, postType, title, content, date, todayString, mapX, mapY);
        databaseRef.child("grapeMate/post").child(key).setValue(b);
    }

}
