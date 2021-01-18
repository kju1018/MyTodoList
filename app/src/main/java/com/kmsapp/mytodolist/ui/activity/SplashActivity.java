package com.kmsapp.mytodolist.ui.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.kmsapp.mytodolist.R;
import com.kmsapp.mytodolist.utils.FirebaseAuthUtil;
import com.kmsapp.mytodolist.utils.ToastUtil;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        LinearLayout logo = findViewById(R.id.splash_logo);
        Animation ani = AnimationUtils.loadAnimation(this, R.anim.side_slide);

        logo.startAnimation(ani);
        Handler hd = new Handler();
        hd.postDelayed(new Runnable() {
            @Override
            public void run() {
                FirebaseUser mUser = FirebaseAuthUtil.getUser();

                Intent intent;
                if(mUser != null){
                    intent = new Intent(SplashActivity.this, MainActivity.class);
                    ToastUtil.toastPrint(SplashActivity.this, mUser.getEmail()+" 로그인완료");
                }else{
                    intent = new Intent(SplashActivity.this, LoginActivity.class);
                    ToastUtil.toastPrint(SplashActivity.this, "로그인 화면으로 이동합니다.");
                }
                startActivity(intent);
                finish();
            }
        }, 3000);

    }
}