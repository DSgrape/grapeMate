package com.example.grape;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash);

        new hideNavigationBar(getWindow().getDecorView());

        //스피너 하단바 오류개선
        View decorView = getWindow().getDecorView();
        decorView.setOnSystemUiVisibilityChangeListener(
                visibility -> decorView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_HIDE_NAVIGATION | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY)
        );

        Handler hd = new Handler();
        hd.postDelayed(new splashhandler(), 2000);//길다 2초만해
    }
    private class splashhandler implements Runnable{
        public void run(){
            startActivity(new Intent(getApplication(),LoginActivity.class));
            SplashActivity.this.finish();
        }
    }

    //스플래시에서 뒤로가기 방지
    @Override
    public void onBackPressed(){}
}

