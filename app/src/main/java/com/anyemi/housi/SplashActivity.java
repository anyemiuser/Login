package com.anyemi.housi;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import androidx.appcompat.app.AppCompatActivity;

import com.anyemi.housi.MainActivity;
import com.anyemi.housi.utils.SharedPreferenceUtil;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_splash);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent i;
                i = new Intent(SplashActivity.this, MainActivity.class);

                /*String reg_id = SharedPreferenceUtil.getRegCode(getApplicationContext());
               // if (reg_id.equals("")) {

               } else {
                    i = new Intent(SplashActivity.this, MediaActivity.class);
                }*/
                startActivity(i);
                finish();
            }
        }, 3000);



}
    }


