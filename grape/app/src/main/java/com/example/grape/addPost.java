package com.example.grape;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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

import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class addPost extends Fragment {
    private Button ok;
    private Button cen;
    private Spinner spin;
    private CalendarView cal;
    private String cate;
    private EditText title;
    private EditText content;
    private String date;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        setHasOptionsMenu(true);
        View v = inflater.inflate(R.layout.add_post, container, false);
        title=v.findViewById(R.id.et_title);
        content=v.findViewById(R.id.et_content);

        //스피너
        spin=v.findViewById(R.id.spin);
        String[] category={"귀가","운동","음식","공부","기타"};
        ArrayAdapter adapter= new ArrayAdapter(getContext(),R.layout.spinner_item,category);
        spin.setAdapter(adapter);
        spin.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    switch (position){
                        case 0: cate=category[0]; break;
                        case 1: cate=category[1]; break;
                        case 2: cate=category[2]; break;
                        case 3: cate=category[3]; break;
                        case 4: cate=category[4]; break;
                        default: cate="오류"; break;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                spin.setSelection(0);
                cate=category[0];
            }

        });

        cal=v.findViewById(R.id.cal);
        cal.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView view, int year, int month, int dayOfMonth) {
                date=Integer.toString(year)+"/"+Integer.toString(month+1)+"/"+Integer.toString(dayOfMonth);
                Log.d("체크",date);
            }
        });

        //취소버튼->메인으로
        cen=v.findViewById(R.id.btn_cen);
        cen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getContext(),"글쓰기를 취소했습니다.",Toast.LENGTH_SHORT).show();
                ((MainActivity)getActivity()).toMain();
            }
        });

        //확인버튼->메인으로
        //정보 등록 필요
        ok=v.findViewById(R.id.btn_ok);
        ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(title.getText().toString().equals("")){
                    Toast.makeText(getContext(),"제목을 입력해주세요.",Toast.LENGTH_SHORT).show();
                }else if(content.getText().toString().equals("")){
                    Toast.makeText(getContext(),"내용을 입력해주세요.",Toast.LENGTH_SHORT).show();
                }else if(date==null){
                    Toast.makeText(getContext(),"날짜를 선택해주세요.",Toast.LENGTH_SHORT).show();
                }else {
                    //db에 저장
                    Log.d("체크",cate);
                    Log.d("체크", title.getText().toString());
                    Log.d("체크", content.getText().toString());
                    Log.d("체크",date);

                    //메인으로 이동
                    ((MainActivity)getActivity()).toMain();
                }
            }
        });

        return v;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }
}
