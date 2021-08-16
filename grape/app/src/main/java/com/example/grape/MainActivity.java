package com.example.grape;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class MainActivity extends AppCompatActivity {

    private BottomNavigationView mBottomNV;
    FragmentManager fragmentManager;
    FragmentTransaction fragmentTransaction;
    private String MainTag;
    private String MyPageTag;
    private long backKeyPressedTime = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        new hideNavigationBar(getWindow().getDecorView());

        mBottomNV = findViewById(R.id.nav_view);
        mBottomNV.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() { //NavigationItemSelecte
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                BottomNavigate(menuItem.getItemId());

                return true;
            }
        });
        mBottomNV.setSelectedItemId(R.id.navigation_2);


    }

    private void BottomNavigate(int id) {  //BottomNavigation 페이지 변경, 하단바
        String tag = String.valueOf(id);
        fragmentManager = getSupportFragmentManager();
        fragmentTransaction = fragmentManager.beginTransaction();

        Fragment currentFragment = fragmentManager.getPrimaryNavigationFragment();
        if (currentFragment != null) {
            fragmentTransaction.hide(currentFragment);
        }

        remove("ap");
        remove("sp");
        remove("smp");
        remove("smc");
        remove("sms");
        remove("profile");

        Fragment fragment = fragmentManager.findFragmentByTag(tag);

        if (fragment == null) {
            if (id == R.id.navigation_1) {
                fragment = new GPS_Fragment();
            } else if (id == R.id.navigation_2){
                MainTag=tag;
                fragment = new Main_Fragment();
            }else {
                MyPageTag=tag;
                fragment = new Mypage_Fragment();
            }

            fragmentTransaction.add(R.id.content_layout, fragment, tag);
        } else {
            fragmentTransaction.show(fragment);
            Log.d("체크","체크");
        }

        fragmentTransaction.setPrimaryNavigationFragment(fragment);
        fragmentTransaction.setReorderingAllowed(true);
        fragmentTransaction.commitNow();

    }

    // 글쓰기
    public void AddPost(){
        fragmentManager = getSupportFragmentManager();
        fragmentTransaction = fragmentManager.beginTransaction();

        Fragment currentFragment = fragmentManager.getPrimaryNavigationFragment();

        // 현재 fragment 감추기
        if (currentFragment != null) {
            fragmentTransaction.hide(currentFragment);
        }

        Fragment fragment = fragmentManager.findFragmentByTag("ap");

        if (fragment == null) {
            fragment=new addPost();
            Log.d("체크","체크4");
            fragmentTransaction.add(R.id.content_layout, fragment, "ap");
        } else {
            Log.d("체크","체크5");
            fragmentTransaction.remove(fragment);
            fragment = new addPost();
            fragmentTransaction.add(R.id.content_layout, fragment, "ap");
        }

        fragmentTransaction.setPrimaryNavigationFragment(fragment);
        fragmentTransaction.setReorderingAllowed(true);
        fragmentTransaction.addToBackStack(null);//뒤로가기 눌렀을 떄 이전 프래그먼트로 이동가능
        fragmentTransaction.commit();
    }

    public void toMain(){

        fragmentManager = getSupportFragmentManager();
        fragmentTransaction = fragmentManager.beginTransaction();

        Fragment currentFragment = fragmentManager.getPrimaryNavigationFragment();
        if (currentFragment != null) {
            fragmentTransaction.hide(currentFragment);
        }

        remove("ap");
        remove("sp");
        remove("smp");
        remove("smc");
        remove("sms");
        remove("profile");

        Fragment fragment = fragmentManager.findFragmentByTag(MainTag);
        if (fragment == null) {
            fragment = new Main_Fragment();
            fragmentTransaction.add(R.id.content_layout, fragment, MainTag);
            Log.d("체크","체크2");
        } else {
            fragmentTransaction.show(fragment);
            Log.d("체크","체크3");
        }

        fragmentTransaction.setPrimaryNavigationFragment(fragment);
        fragmentTransaction.setReorderingAllowed(true);
        fragmentTransaction.commitNow();
    }

    //글보기
    public void ShowPost(String postToken, String Uid){
        fragmentManager = getSupportFragmentManager();
        fragmentTransaction = fragmentManager.beginTransaction();

        Fragment currentFragment = fragmentManager.getPrimaryNavigationFragment();

        // 현재 fragment 감추기
        if (currentFragment != null) {
            fragmentTransaction.hide(currentFragment);
        }

        Bundle bundle = new Bundle();
        bundle.putString("postToken",postToken);
        bundle.putString("Uid", Uid);

        Fragment fragment = fragmentManager.findFragmentByTag("sp");

        if (fragment == null) {
            fragment=new showPost();
            fragment.setArguments(bundle);
            fragmentTransaction.add(R.id.content_layout, fragment, "sp");
            Log.d("체크","ㅍ");
        } else {
            fragmentTransaction.remove(fragment);
            fragment = new showPost();
            fragment.setArguments(bundle);
            fragmentTransaction.add(R.id.content_layout, fragment, "sp");
        }

        fragmentTransaction.setPrimaryNavigationFragment(fragment);
        fragmentTransaction.setReorderingAllowed(true);
        fragmentTransaction.addToBackStack(null);//뒤로가기 눌렀을 떄 이전 프래그먼트로 이동가능
        fragmentTransaction.commit();
    }

    // 내가 쓴 글로
    public void showMyPost(){
        fragmentManager = getSupportFragmentManager();
        fragmentTransaction = fragmentManager.beginTransaction();

        Fragment currentFragment = fragmentManager.getPrimaryNavigationFragment();


        // 현재 fragment 감추기
        if (currentFragment != null) {
            fragmentTransaction.hide(currentFragment);
        }

        Fragment fragment = fragmentManager.findFragmentByTag("smp");

        if (fragment == null) {
            fragment=new myPost();
            fragmentTransaction.add(R.id.content_layout, fragment, "smp");
        } else {
            fragmentTransaction.remove(fragment);
            fragment = new myPost();
            fragmentTransaction.add(R.id.content_layout, fragment, "smp");
        }

        fragmentTransaction.setPrimaryNavigationFragment(fragment);
        fragmentTransaction.setReorderingAllowed(true);
        fragmentTransaction.addToBackStack(null);//뒤로가기 눌렀을 떄 이전 프래그먼트로 이동가능
        fragmentTransaction.commit();
    }

    // 내 채팅으로
    public void showMyChat(){
        fragmentManager = getSupportFragmentManager();
        fragmentTransaction = fragmentManager.beginTransaction();

        Fragment currentFragment = fragmentManager.getPrimaryNavigationFragment();

        // 현재 fragment 감추기
        if (currentFragment != null) {
            fragmentTransaction.hide(currentFragment);
        }

        remove("Chatting");

        Fragment fragment = fragmentManager.findFragmentByTag("smc");

        if (fragment == null) {
            fragment=new myChat();
            fragmentTransaction.add(R.id.content_layout, fragment, "smc");
        } else {
            fragmentTransaction.remove(fragment);
            fragment = new myChat();
            fragmentTransaction.add(R.id.content_layout, fragment, "smc");
        }

        fragmentTransaction.setPrimaryNavigationFragment(fragment);
        fragmentTransaction.setReorderingAllowed(true);
        fragmentTransaction.addToBackStack(null);//뒤로가기 눌렀을 떄 이전 프래그먼트로 이동가능
        fragmentTransaction.commit();
    }

    // 내 스티커으로
    public void showMySticker(){
        fragmentManager = getSupportFragmentManager();
        fragmentTransaction = fragmentManager.beginTransaction();

        Fragment currentFragment = fragmentManager.getPrimaryNavigationFragment();

        // 현재 fragment 감추기
        if (currentFragment != null) {
            fragmentTransaction.hide(currentFragment);
        }

        Fragment fragment = fragmentManager.findFragmentByTag("sms");

        if (fragment == null) {
            fragment=new showSticker();
            fragmentTransaction.add(R.id.content_layout, fragment, "sms");
        } else {
            fragmentTransaction.remove(fragment);
            fragment = new showSticker();
            fragmentTransaction.add(R.id.content_layout, fragment, "sms");
        }

        fragmentTransaction.setPrimaryNavigationFragment(fragment);
        fragmentTransaction.setReorderingAllowed(true);
        fragmentTransaction.addToBackStack(null);//뒤로가기 눌렀을 떄 이전 프래그먼트로 이동가능
        fragmentTransaction.commit();
    }

    // 개인 정보 설정
    public void showMyProfile(){
        fragmentManager = getSupportFragmentManager();
        fragmentTransaction = fragmentManager.beginTransaction();

        Fragment currentFragment = fragmentManager.getPrimaryNavigationFragment();

        // 현재 fragment 감추기
        if (currentFragment != null) {
            fragmentTransaction.hide(currentFragment);
        }

        Fragment fragment = fragmentManager.findFragmentByTag("profile");

        if (fragment == null) {
            fragment=new editInfo();
            fragmentTransaction.add(R.id.content_layout, fragment, "profile");
        } else {
            fragmentTransaction.remove(fragment);
            fragment = new editInfo();
            fragmentTransaction.add(R.id.content_layout, fragment, "profile");
        }

        fragmentTransaction.setPrimaryNavigationFragment(fragment);
        fragmentTransaction.setReorderingAllowed(true);
        fragmentTransaction.addToBackStack(null);//뒤로가기 눌렀을 떄 이전 프래그먼트로 이동가능
        fragmentTransaction.commit();
    }

    // 채팅으로
    public void toChatting(String ChatName){
        fragmentManager = getSupportFragmentManager();
        fragmentTransaction = fragmentManager.beginTransaction();

        Fragment currentFragment = fragmentManager.getPrimaryNavigationFragment();

        // 현재 fragment 감추기
        if (currentFragment != null) {
            fragmentTransaction.hide(currentFragment);
        }

        Bundle bundle = new Bundle();
        bundle.putString("ChatName",ChatName);

        Fragment fragment = fragmentManager.findFragmentByTag("Chatting");

        if (fragment == null) {
            fragment=new Chatting();
            fragment.setArguments(bundle);
            fragmentTransaction.add(R.id.content_layout, fragment, "Chatting");
        } else {
            fragmentTransaction.remove(fragment);
            fragment = new Chatting();
            fragment.setArguments(bundle);
            fragmentTransaction.add(R.id.content_layout, fragment, "Chatting");
        }

        fragmentTransaction.setPrimaryNavigationFragment(fragment);
        fragmentTransaction.setReorderingAllowed(true);
        fragmentTransaction.addToBackStack(null);//뒤로가기 눌렀을 떄 이전 프래그먼트로 이동가능
        fragmentTransaction.commit();
    }

    //마이페이로
    public void toMyPage(){

        fragmentManager = getSupportFragmentManager();
        fragmentTransaction = fragmentManager.beginTransaction();

        Fragment currentFragment = fragmentManager.getPrimaryNavigationFragment();
        if (currentFragment != null) {
            fragmentTransaction.hide(currentFragment);
        }

        remove("ap");
        remove("sp");
        remove("smp");
        remove("smc");
        remove("sms");
        remove("profile");

        Fragment fragment = fragmentManager.findFragmentByTag(MyPageTag);
        if (fragment == null) {
            fragment = new Main_Fragment();
            fragmentTransaction.add(R.id.content_layout, fragment, MyPageTag);
        } else {
            fragmentTransaction.show(fragment);
        }

        fragmentTransaction.setPrimaryNavigationFragment(fragment);
        fragmentTransaction.setReorderingAllowed(true);
        fragmentTransaction.commitNow();
    }

    //프래그먼트 트랜젝션 삭제
    public void remove(String tag){
        Fragment fragment = fragmentManager.findFragmentByTag(tag);
        if (fragment != null) {
            fragmentTransaction.remove(fragment);
            Log.d("체크","체크1");
        }
    }

    //두번 눌러 종료
    @Override
    public void onBackPressed(){
        Toast t = Toast.makeText(this,"뒤로가기 버튼을 한번 더 누르시면 종료됩니다.",Toast.LENGTH_SHORT);
        if(getSupportFragmentManager().getBackStackEntryCount()==0){
            if(System.currentTimeMillis()>backKeyPressedTime+2000){
                backKeyPressedTime = System.currentTimeMillis();
                t.show();
            }else {
                t.cancel();
                finish();
            }
        }else {
            super.onBackPressed();
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
}