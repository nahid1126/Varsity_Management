package com.varsity_management.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;

import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;
import com.varsity_management.R;
import com.varsity_management.utils.LocalStorage;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SplashActivity extends AppCompatActivity {
    @BindView(R.id.img_logo)
    ImageView img_logo;

    private LocalStorage localStorage;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        ButterKnife.bind(this);

        localStorage = new LocalStorage(this);
        getSupportActionBar().hide();
        YoYo.with(Techniques.BounceInUp).duration(2000).onEnd(animator -> {
            Intent intent;
            try {
                boolean isLogin = localStorage.getLoginResponse();
                if (isLogin) {
                    intent = new Intent(SplashActivity.this, MainActivity.class);
                } else {
                    intent = new Intent(SplashActivity.this, SignInActivity.class);
                }
                SplashActivity.this.startActivity(intent);
            } catch (Exception e) {
                SplashActivity.this.startActivity(new Intent(SplashActivity.this, SignInActivity.class));
            } finally {
                SplashActivity.this.finish();
            }
        }).playOn(img_logo);
    }
}